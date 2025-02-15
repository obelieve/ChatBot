package com.github.obelieve.chat.ui.conversation

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.obelieve.chat.db.Message
import com.github.obelieve.chat.db.Session
import com.github.obelieve.chat.enumtype.MessageRoleEnum
import com.github.obelieve.chat.model.ChatMessage
import com.github.obelieve.chat.net.NetApiManager
import com.github.obelieve.chat.repository.MessageRepository
import com.github.obelieve.chat.repository.SessionRepository
import com.obelieve.frame.utils.ToastUtil
import com.obelieve.frame.utils.info.NetworkUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 *   @desc content
 *   Created by obelieve
 **/
class ConversationViewModel : ViewModel() {

    var currentSessionId: Int = -1
    var currentSession: Session? = null
    val historySessionsLV: SnapshotStateList<Session> = mutableStateListOf()

    val titleLV: MutableLiveData<String> = MutableLiveData("")
    val sendMessagingLV: MutableLiveData<Boolean> = MutableLiveData(false)
    val inputtingMessageContentLV: MutableLiveData<Pair<Int, String>> = MutableLiveData()
    val currentMessageList: SnapshotStateList<Message> = mutableStateListOf()
    val loadingMessaging = Message(0, MessageRoleEnum.SYSTEM.role, "")
    var inputtingMessage:Message? = null
    var inputtingJob: Job? = null

    val mSessionRepository: SessionRepository by lazy { SessionRepository() }
    val mMessageRepository: MessageRepository by lazy { MessageRepository() }

    fun init() {
        queryHistorySessions()
        switchConversation(currentSessionId)
    }

    private fun queryHistorySessions() {
        viewModelScope.launch {
            val list = mSessionRepository.getAllSession().toMutableList()
            historySessionsLV.clear()
            historySessionsLV.addAll(list)
        }
    }

    /**
     * 切换会话
     * @param id
     */
    fun switchConversation(id: Int) {
        titleLV.value = ""
        currentSessionId = id
        currentMessageList.clear()
        sendMessagingLV.value = false
        cancelSend()
        if (id != -1){
            viewModelScope.launch{
                currentSession =
                    mSessionRepository.getSession(id)
                titleLV.value = currentSession?.title?:""
                val list =
                    mMessageRepository.queryAllMessageFromSessionId(id)
                list.let {
                    currentMessageList.addAll(it)
                }
            }
        }
    }

    fun deleteSession(id: Int) {
        viewModelScope.launch{
            currentSession?.run {
                mSessionRepository.deleteSession(id)
                queryHistorySessions()
            }
        }
    }

    fun sendMessage(text: String): Boolean {
        if (!NetworkUtil.isNetAvailable(com.github.obelieve.chat.App.getContext())) {
            showToast("Connection required")
            return false
        }
        if (sendMessagingLV.value == true) {
            return false
        }
        viewModelScope.launch {
            val message = Message(0, MessageRoleEnum.USER.role, text)
            if (currentSessionId == -1) {
                var session: Session
                val sessionId = withContext(Dispatchers.IO) {
                    session = Session(0, message.content, System.currentTimeMillis())
                    val id = mSessionRepository.insertSession(session)
                    mSessionRepository.updateSessionTitle(message.content, id)
                    queryHistorySessions()
                    id
                }
                currentSessionId = sessionId
                currentSession = session
                titleLV.value = message.content?:""
            }
            message.sessionId = currentSessionId
            withContext(Dispatchers.IO) {
                message.id = mMessageRepository.insertMessage(message)
            }
            currentMessageList.add(message)
            sendMessagingLV.value = true
            val listMessage = currentMessageList.filter { it.role != MessageRoleEnum.SYSTEM.role }
                .map { it.toChatMessage() }
            internalSendMessage(listMessage)
        }
        return true
    }

    fun reSendMessage(message: Message): Boolean {
        if (!NetworkUtil.isNetAvailable(com.github.obelieve.chat.App.getContext())) {
            showToast("Connection required")
            return false
        }
        if (sendMessagingLV.value == true) {
            return false
        }
        viewModelScope.launch {
            mMessageRepository.updateMessage(message)
            mSessionRepository.updateSessionTitle(
                message.content,
                currentSessionId
            )
            sendMessagingLV.value = true
            val listMessage = currentMessageList.filter { it.role != MessageRoleEnum.SYSTEM.role }
                .map { it.toChatMessage() }
            internalSendMessage(listMessage)
        }
        return true
    }

    private suspend fun internalSendMessage(list: List<ChatMessage>) {
        inputtingJob?.cancel()
        inputtingJob = viewModelScope.launch(Dispatchers.IO) {
            var indexDelta = -1
            val sb = StringBuffer()
            NetApiManager.requestChatGptFlow(list)
                .buffer(capacity = 5_000, BufferOverflow.SUSPEND)
                .onStart {
                    viewModelScope.launch {
                        showLoadingMessage()
                    }
                }
                .onCompletion {
                    viewModelScope.launch {
                        dismissLoadingMessage()
                        completedInputtingMessage()
                        sendMessagingLV.value = false
                    }
                }
                .collect {
                    delay(80)
                    viewModelScope.launch {
                        if (it.isNotEmpty()) {
                            val delta = it
                            sb.append(delta)
                            indexDelta++
                            if (indexDelta == 0) {
                                addInputtingMessage(sb.toString())
                                dismissLoadingMessage()
                            } else {
                                updateInputtingMessage(indexDelta, sb.toString())
                            }
                        }
                    }
                }
        }
    }

    private fun updateInputtingMessage(indexDelta: Int,content: String) {
        inputtingMessage?.let {
            it.content = content
            if (it.inputting) {
                inputtingMessageContentLV.value =
                    Pair(indexDelta, content)
            }
        }
    }

    private suspend fun addInputtingMessage(content: String) {
        inputtingMessage = Message(
            0,
            MessageRoleEnum.ASSISTANT.role,
            content,
            sessionId = currentSessionId,
        )
        inputtingMessage?.inputting = true
        inputtingMessage?.id = withContext(Dispatchers.IO) {
            mMessageRepository.insertMessage(inputtingMessage!!)
        }
        currentMessageList.add(inputtingMessage!!)
    }

    private suspend fun completedInputtingMessage() {
        inputtingMessage?.let {
            if (it.inputting) {
                it.inputting = false
                mMessageRepository.updateMessage(it)
            }
        }
    }

    private fun showLoadingMessage() {
        currentMessageList.add(loadingMessaging)
    }

    private fun dismissLoadingMessage() {
        currentMessageList.remove(loadingMessaging)
    }

    override fun onCleared() {
        super.onCleared()
        cancelSend()
    }

    fun cancelSend() {
        try {
            NetApiManager.cancel()
            sendMessagingLV.value = false
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * TODO 显示Toast
     */
    fun showToast(message:String){
        ToastUtil.show(message)
    }

}
package com.github.obelieve.chat.ui.conversation

import android.text.TextUtils
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.github.obelieve.chat.R
import com.github.obelieve.chat.db.Message
import com.github.obelieve.chat.enumtype.MessageRoleEnum
import com.github.obelieve.chat.ui.AppColor


/**
 *   @desc content
 *   Created by obelieve
 **/

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConversationScreen(viewModel: ConversationViewModel){
    val messageList =  viewModel.currentMessageList
    val inputtingMessageContent by viewModel.inputtingMessageContentLV.observeAsState(null)
    val sendMessaging by viewModel.sendMessagingLV.observeAsState(false)
    LaunchedEffect(Unit){
        viewModel.init()
    }
    Box(modifier = Modifier.fillMaxSize()){
        MainContent(
            messageList,
            sendMessaging,inputtingMessageContent, mConversationViewModel = viewModel)
    }
}


@Composable
fun MainContent(
    messageList: List<Message>,
    sendMessaging: Boolean,
    inputtingMessageContent:Pair<Int,String>?,
    mConversationViewModel:ConversationViewModel
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColor.Background)
    ) {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (contentRef, bottomBarRef) = createRefs()
            Box(modifier = Modifier
                .constrainAs(contentRef) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(bottomBarRef.top)
                    height = Dimension.fillToConstraints
                    width = Dimension.fillToConstraints
                }) {
                val scrollState = rememberLazyListState()
                val inputStateContent = remember {
                    mutableStateOf("")
                }
                LazyColumn(state = scrollState, content = {
                    items(messageList) {
                        Spacer(modifier = Modifier.height(10.dp))
                        if (MessageRoleEnum.USER.role == it.role) {
                            RightView(message = it){resendMsg->
                                mConversationViewModel.reSendMessage(resendMsg)
                            }
                        } else if (MessageRoleEnum.ASSISTANT.role == it.role) {
                            if(it.inputting){
                                LeftInputtingView(initialIndex = inputtingMessageContent?.first?:inputtingMessageContent?.second?.length?:-1,message = if(!TextUtils.isEmpty(inputStateContent.value))inputStateContent.value else it.content)
                                LaunchedEffect(key1 = inputtingMessageContent?.second) {
                                    inputStateContent.value = inputtingMessageContent?.second?:""
                                }
                            }else{
                                LeftView(message = it.content)
                            }
                        } else if (MessageRoleEnum.SYSTEM.role == it.role) {
                            SystemView(message = it)
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                })
                if (messageList.isNotEmpty()) {
                    LaunchedEffect(key1 = messageList.size,inputtingMessageContent?.second) {
                        scrollState.animateScrollToItem(messageList.size)
                    }
                }else{
                    Image(modifier = Modifier.align(Alignment.Center).size(100.dp),
                        painter = painterResource(id = R.drawable.ic_conversation_bg),
                        contentDescription = null)
                }
            }
            BottomBar(
                modifier = Modifier
                    .constrainAs(bottomBarRef) {
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        width = Dimension.fillToConstraints
                        end.linkTo(parent.end)
                    }
                    .padding(start = 10.dp, end = 10.dp, bottom = 20.dp, top = 10.dp),
                sendMessaging,sendMessageClick = {
                    mConversationViewModel.sendMessage(it)
                }
            , sendCancelCall = {
                    mConversationViewModel.cancelSend()
                })
        }
    }
}
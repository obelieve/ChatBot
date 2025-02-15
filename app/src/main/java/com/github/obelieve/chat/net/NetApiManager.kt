package com.github.obelieve.chat.net

import com.github.obelieve.chat.BuildConfig
import com.github.obelieve.chat.model.ChatCompletionStreamResponseBean
import com.github.obelieve.chat.model.ChatMessage
import com.github.obelieve.chat.model.ChatgptCompletionRequestBean
import com.github.obelieve.chat.net.NetURLConstant.BASE_URL
import com.google.gson.Gson
import com.obelieve.frame.utils.log.LogUtil
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import okhttp3.sse.EventSource
import okhttp3.sse.EventSourceListener
import okhttp3.sse.EventSources
import okio.ByteString
import okio.ByteString.Companion.encodeUtf8
import java.util.concurrent.TimeUnit

/**
 *   @desc content
 *   Created by obelieve
 **/
object NetApiManager {

    val gson = Gson()
    val okHttpClient = OkHttpClient.Builder().apply {
        this.connectTimeout(10, TimeUnit.SECONDS)
        this.readTimeout(3, TimeUnit.MINUTES)
    }.build()
    val JsonMediaType = "application/json".toMediaTypeOrNull()

    var mEventSource:EventSource? = null

    fun requestChatGptFlow(listMessage: List<ChatMessage>): Flow<String> {
        cancel()
        val request: Request = getChatGptRequest(listMessage)
        return callbackFlow {
            mEventSource = EventSources.createFactory(okHttpClient)
                .newEventSource(request,object : EventSourceListener() {
                    override fun onOpen(eventSource: EventSource, response: Response) {
                        super.onOpen(eventSource, response)
                        LogUtil.i("EventSources","onOpen")
                    }

                    override fun onEvent(
                        eventSource: EventSource,
                        id: String?,
                        type: String?,
                        data: String
                    ) {
                        super.onEvent(eventSource, id, type, data)
                        LogUtil.i("EventSources","onEvent id=$id data=${data}")
                        try {
                            val delta = parseEventSourceData(data)
                            if (delta.isNotEmpty()) {
                                trySend(delta)
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }

                    override fun onFailure(
                        eventSource: EventSource,
                        t: Throwable?,
                        response: Response?
                    ) {
                        super.onFailure(eventSource, t, response)
                        LogUtil.i("EventSources","onFailure")
                        cancel(CancellationException("API Error", t))
                    }

                    override fun onClosed(eventSource: EventSource) {
                        super.onClosed(eventSource)
                        LogUtil.i("EventSources","onClosed")
                        channel.close()
                    }
                })
            awaitClose {
                mEventSource?.cancel()
            }
        }
    }

    fun cancel(){
        println("inputMessage cancel")
        mEventSource?.cancel()
    }

    private fun getChatGptRequest(listMessage: List<ChatMessage>): Request {
        val url = BASE_URL + NetURLConstant.CHAT_COMPLETIONS
        val reqBody = ChatgptCompletionRequestBean(listMessage, stream = true)
        val json = Gson().toJson(reqBody)
        val bString: ByteString = json.encodeUtf8() //charset=UTF-8
        val requestBody = RequestBody.create(JsonMediaType, bString)
        val request = Request.Builder().apply {
            addHeader("Authorization", "Bearer ${BuildConfig.API_TOKEN}")
            url(url)
            post(requestBody)
        }.build()
        return request
    }

    private fun parseEventSourceData(data:String):String{
        try {
            val bean: ChatCompletionStreamResponseBean? =
                gson.fromJson(data, ChatCompletionStreamResponseBean::class.java)
            bean?.let {
                if (it.error == null && (it.choices?.size ?: 0) > 0) {
                    val delta = it.choices?.get(0)?.delta?.content ?: ""
                    if (delta.isNotEmpty()) {
                        return delta
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }
}
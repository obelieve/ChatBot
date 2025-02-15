package com.github.obelieve.chat.model
import com.google.gson.annotations.SerializedName


/**
 *   @desc content
 *   Created by obelieve
 **/
data class ChatgptCompletionRequestBean(
    @SerializedName("messages")
    val messages: List<ChatMessage>,
    @SerializedName("model")
    var model: String = "gpt-4o",
    @SerializedName("stream")
    var stream: Boolean
//    ,
//    @SerializedName("temperature")
//    var temperature: Float = 0.7f
)


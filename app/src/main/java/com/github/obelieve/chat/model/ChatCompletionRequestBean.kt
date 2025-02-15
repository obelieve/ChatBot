package com.github.obelieve.chat.model
import com.google.gson.annotations.SerializedName


/**
 *   @desc content
 *   Created by obelieve
 **/
data class ChatCompletionRequestBean(
    @SerializedName("messages")
    val messages: List<ChatMessage>
)


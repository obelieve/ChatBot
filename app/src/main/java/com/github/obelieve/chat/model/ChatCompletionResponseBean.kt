package com.github.obelieve.chat.model

import com.google.gson.annotations.SerializedName

/**
 *   @desc content
 *   Created by obelieve
 **/
class ChatCompletionResponseBean {
    @SerializedName("message")
    var message: String = ""
    @SerializedName("data")
    var data: Data? = null
    @SerializedName("code")
    var code: Int = 0

    class Data {
        @SerializedName("choices")
        val choices: List<Choice>? = null
        @SerializedName("created")
        val created: Int = 0
        @SerializedName("id")
        val id: String? = null
        @SerializedName("object")
        val objectX: String? = null
        @SerializedName("usage")
        val usage: Usage? = null
        data class Choice(
            @SerializedName("finish_reason")
            val finishReason: String,
            @SerializedName("index")
            val index: Int,
            @SerializedName("message")
            val message: ChatMessage
        )
        data class Usage(
            @SerializedName("completion_tokens")
            val completionTokens: Int,
            @SerializedName("prompt_tokens")
            val promptTokens: Int,
            @SerializedName("total_tokens")
            val totalTokens: Int
        )
    }
}


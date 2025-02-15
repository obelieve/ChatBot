package com.github.obelieve.chat.model


import com.google.gson.annotations.SerializedName

data class ChatCompletionStreamResponseBean(
    @SerializedName("choices")
    val choices: List<Choice>? = null,
    @SerializedName("created")
    val created: Int? = null,
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("object")
    val objectX: String? = null,
    @SerializedName("model")
    val model:String? = null,
    @SerializedName("error")
    val error:String? = null
) {

    data class Choice (
        @SerializedName("finish_reason")
        val finishReason: String,
        @SerializedName("index")
        val index: Int,
        @SerializedName("delta")
        val delta: ChatMessage
    )
}
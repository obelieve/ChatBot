package com.github.obelieve.chat.model

import com.google.gson.annotations.SerializedName

/**
 *   @desc content
 *   Created by obelieve
 **/
data class ChatMessage(
    @SerializedName("role")
    val role: String,
    @SerializedName("content")
    val content: String
)
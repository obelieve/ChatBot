package com.github.obelieve.chat.db

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.github.obelieve.chat.model.ChatMessage
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "message")
data class Message(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @Expose @SerializedName("role") val role: String,
    @Expose @SerializedName("content") var content: String,
    @SerializedName("sessionId")
    var sessionId: Int = 0,
    //插入时间
    @SerializedName("insertTime")
    var insertTime: Long = System.currentTimeMillis(),
    //类型
    @SerializedName("type")
    var type:String = "",
    @SerializedName("error")
    var error:String = ""
) {
    //输入性消息
    @Ignore @SerializedName("inputting")
    var inputting: Boolean = false
    fun toChatMessage(): ChatMessage {
        return ChatMessage(role = role, content = content)
    }
}

package com.github.obelieve.chat.db

import android.text.TextUtils
import android.text.format.DateUtils
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "session")
data class Session(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String = "",
    val lastSessionTime: Long,
    val desc:String="",
    var tag:String = ""
) {
    fun getContent(): String {
        return if(TextUtils.isEmpty(desc))
            DateUtils.formatDateTime(com.github.obelieve.chat.App.getContext(),lastSessionTime,0) else
            desc
    }

}
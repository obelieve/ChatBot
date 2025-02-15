package com.github.obelieve.chat.db

import androidx.room.*

/**
 *   @desc content
 *   Created by obelieve
 **/
@Dao
open interface MessageDao {

    @Insert
    fun insertMessage(Message: Message):Long

    @Update
    fun updateMessage(Message: Message): Int

    @Update
    fun updateMessage(vararg Message: Message):Int //可以使用int值返回值

    @Delete
    fun deleteMessage(vararg Message: Message) //根据Message主键删除 Message

    @Query("Delete from message where id =:id")
    fun deleteMessage(id: Int): Int

    @Query("Select * from message where role =:role")
    fun queryAllMessageFromRole(role: String): List<Message>

    @Query("Select * from message where sessionId =:sessionId")
    fun queryAllMessageFromSessionId(sessionId: Int): List<Message>

    @Query("Select * from message where id =:id")
    //@Query是编译时处理，语句出错编译时会提示
    fun queryMessage(id: Int): Message?


    @Query("Select * from message")
    //多表查询 @Query
    fun queryAllMessage(): List<Message>
}
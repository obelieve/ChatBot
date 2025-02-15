package com.github.obelieve.chat.db

import androidx.room.*

/**
 *   @desc content
 *   Created by obelieve
 **/
@Dao
open interface SessionDao {

    @Insert
    fun insertSession(session: Session):Long

    @Update
    fun updateSession(session: Session): Int

    @Update
    fun updateSession(vararg session: Session):Int //可以使用int值返回值

    @Query("update Session set title = :title where id = :sessionId")
    fun updateSessionTitle(title: String, sessionId: Int)

    @Delete
    fun deleteSession(vararg session: Session) //根据Session主键删除 Session

    @Query("Delete from Session where id =:id")
    fun deleteSession(id: Int): Int

    @Query("Select * from Session where id =:id")
    //@Query是编译时处理，语句出错编译时会提示
    fun querySession(id: Int): Session?

    @Query("SELECT * FROM Session ORDER BY id DESC LIMIT :limit ")
    fun querySessions(limit: Int): List<Session>


    @Query("Select * from Session ORDER BY id DESC")
    //多表查询 @Query
    fun queryAllSession(): List<Session>

    @Query("Select * from Session where tag=:tag ORDER BY id DESC")
    //多表查询 @Query
    fun queryAllSessionFromTag(tag:String): List<Session>
}
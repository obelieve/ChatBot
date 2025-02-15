package com.github.obelieve.chat.repository

import com.github.obelieve.chat.db.AppDatabase
import com.github.obelieve.chat.db.Session
import com.github.obelieve.chat.db.SessionDao
import com.obelieve.frame.utils.log.LogUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 *   @desc content
 *   Created by obelieve
 **/
class SessionRepository() {
    
    companion object{
        const val TAG = "SessionRepository"
    }

    lateinit var mAppDatabase: AppDatabase

    init {
        mAppDatabase = AppDatabase.getInstance()
    }

    suspend fun getSession(id:Int): Session?{
        return withContext(Dispatchers.IO){
            val db = mAppDatabase
            val sessionDao: SessionDao = db.sessionDao()
            val session = sessionDao.querySession(id)
            LogUtil.i(TAG,"getSession->${session}")
            session
        }
    }

    suspend fun querySessionsLimit():List<Session>{
        return withContext(Dispatchers.IO){
            val db = mAppDatabase
            val sessionDao: SessionDao = db.sessionDao()
            val list = sessionDao.querySessions(10)
            LogUtil.i(TAG,"querySessions->${list}")
            list
        }
    }

    suspend fun getAllSession():List<Session>{
        return withContext(Dispatchers.IO){
            val db = mAppDatabase
            val sessionDao: SessionDao = db.sessionDao()
            val list = sessionDao.queryAllSession()
            LogUtil.i(TAG,"getAllSession->${list}")
            list
        }
    }

    suspend fun queryAllSessionFromTag(tag:String):List<Session>{
        return withContext(Dispatchers.IO){
            val db = mAppDatabase
            val sessionDao: SessionDao = db.sessionDao()
            val list = sessionDao.queryAllSessionFromTag(tag)
            LogUtil.i(TAG,"queryAllSessionFromTag->${list}")
            list
        }
    }

    suspend fun insertSession(Session: Session):Int{
        return withContext(Dispatchers.IO){
            val db = mAppDatabase
            val sessionDao: SessionDao = db.sessionDao()
            LogUtil.i(TAG,"insertSession->${Session}")
            sessionDao.insertSession(Session).toInt()
        }
    }


    suspend fun updateSession(Session: Session){
        return withContext(Dispatchers.IO){
            val db = mAppDatabase
            val sessionDao: SessionDao = db.sessionDao()
            sessionDao.updateSession(Session)
            LogUtil.i(TAG,"updateSession->${Session}")
        }
    }

    suspend fun deleteSession(Session: Session){
        return withContext(Dispatchers.IO){
            val db = mAppDatabase
            val sessionDao: SessionDao = db.sessionDao()
            sessionDao.deleteSession(Session)
            LogUtil.i(TAG,"deleteSession->${Session}")
        }
    }

    suspend fun deleteSession(id: Int):Int{
        return withContext(Dispatchers.IO){
            val db = mAppDatabase
            val sessionDao: SessionDao = db.sessionDao()
            val id = sessionDao.deleteSession(id)
            LogUtil.i(TAG,"deleteSession->id = ${id}")
            id
        }
    }

    suspend fun updateSessionTitle(title:String,id:Int){
        return withContext(Dispatchers.IO){
            val db = mAppDatabase
            val sessionDao: SessionDao = db.sessionDao()
            sessionDao.updateSessionTitle(title,id)
            LogUtil.i(TAG,"updateSessionTitle->title=${title},id=${id}")
        }
    }

}
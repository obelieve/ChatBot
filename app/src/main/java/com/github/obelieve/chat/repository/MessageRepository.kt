package com.github.obelieve.chat.repository


import com.github.obelieve.chat.db.AppDatabase
import com.github.obelieve.chat.db.Message
import com.github.obelieve.chat.db.MessageDao
import com.obelieve.frame.utils.log.LogUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 *   @desc content
 *   Created by obelieve
 **/
class MessageRepository() {
    
    companion object{
        const val TAG = "MessageRepository"
    }

    lateinit var mAppDatabase: AppDatabase

    init {
        mAppDatabase = AppDatabase.getInstance()
    }


    suspend fun getMessage(id:Int): Message?{
        return withContext(Dispatchers.IO){
            val db = mAppDatabase
            val noteDao: MessageDao = db.messageDao()
            val message = noteDao.queryMessage(id)
            LogUtil.i(TAG,"getMessage->${message}")
            message
        }
    }

    suspend fun getAllMessage():List<Message>{
        return withContext(Dispatchers.IO){
            val db = mAppDatabase
            val messageDao: MessageDao = db.messageDao()
            val list = messageDao.queryAllMessage()
            LogUtil.i(TAG,"getAllMessage->${list}")
            list
        }
    }

    suspend fun queryAllMessageFromSessionId(sessionId: Int):List<Message>{
        return withContext(Dispatchers.IO){
            val db = mAppDatabase
            val messageDao: MessageDao = db.messageDao()
            val list = messageDao.queryAllMessageFromSessionId(sessionId)
            LogUtil.i(TAG,"queryAllMessageFromSessionId->${list}")
            list
        }
    }

    suspend fun insertMessage(message: Message):Int{
        return withContext(Dispatchers.IO){
            val db = mAppDatabase
            val messageDao: MessageDao = db.messageDao()
            message.insertTime = System.currentTimeMillis()
            LogUtil.i(TAG,"insertMessage->${message}")
            messageDao.insertMessage(message).toInt()
        }
    }


    suspend fun updateMessage(message: Message){
        return withContext(Dispatchers.IO){
            val db = mAppDatabase
            val messageDao: MessageDao = db.messageDao()
            messageDao.updateMessage(message)
            LogUtil.i(TAG,"updateMessage->${message}")
        }
    }

    suspend fun deleteMessage(message: Message){
        return withContext(Dispatchers.IO){
            val db = mAppDatabase
            val messageDao: MessageDao = db.messageDao()
            messageDao.deleteMessage(message)
            LogUtil.i(TAG,"deleteMessage->${message}")
        }
    }
}
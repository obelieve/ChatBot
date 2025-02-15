package com.github.obelieve.chat.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * @desc content
 * Created by obelieve
 */
/**
 * @desc content
 * Created by obelieve
 */
@Database(
    entities = [Message::class, Session::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun messageDao(): MessageDao
    abstract fun sessionDao(): SessionDao

    companion object {
        private var sAppDatabase: AppDatabase? = null
        fun getInstance(): AppDatabase {
            if (sAppDatabase == null) {
                sAppDatabase =
                    Room.databaseBuilder(com.github.obelieve.chat.App.getContext(), AppDatabase::class.java, "default.db")
                        .allowMainThreadQueries()
                        .build()
            }
            return sAppDatabase!!
        }
    }
}
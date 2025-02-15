package com.github.obelieve.chat

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.obelieve.frame.utils.ToastUtil
import com.obelieve.frame.utils.storage.SPUtil


/**
 *   @desc content
 *   Created by obelieve
 **/
class App: Application() {

    companion object{

        @SuppressLint("StaticFieldLeak")
        private var _context:Context? = null
        fun getContext():Context{
            return com.github.obelieve.chat.App._context!!
        }
    }

    override fun onCreate() {
        super.onCreate()
        com.github.obelieve.chat.App._context = this
        SPUtil.init(this)
        ToastUtil.init(this)
    }
}
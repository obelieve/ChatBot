package com.github.obelieve.chat.utils

import java.text.SimpleDateFormat
import java.util.*


/**
 *   @desc content
 *   Created by obelieve
 **/
object FormatUtils {

    private val format = SimpleDateFormat("yyyy.MM.dd")

    fun getyyyyMMdd(millsTime:Long):String{
        return format.format(Date(millsTime))
    }
}
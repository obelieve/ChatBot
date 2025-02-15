package com.github.obelieve.chat.model.local

import com.github.obelieve.chat.enumtype.SettingsTypeEnum

/**
 *   @desc content
 *   Created by obelieve
 **/
data class SettingsItemBean(var icon:Int, var title:String, var type: SettingsTypeEnum, var switch:Boolean = true)

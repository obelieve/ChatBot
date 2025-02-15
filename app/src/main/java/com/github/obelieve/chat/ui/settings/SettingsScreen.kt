package com.github.obelieve.chat.ui.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.github.obelieve.chat.R
import com.github.obelieve.chat.enumtype.SettingsTypeEnum
import com.github.obelieve.chat.model.local.SettingsItemBean
import com.github.obelieve.chat.ui.AppColor

/**
 *   @desc content
 *   Created by obelieve
 **/
@Composable
fun SettingsScreen(navController:NavController) {
    Box(modifier = Modifier
        .background(color = AppColor.Background)
        .fillMaxSize()
       ) {
        Column {
            SettingsNavBar {
                navController.popBackStack()
            }
            val list = mutableListOf<SettingsItemBean>()
            list.add(
                SettingsItemBean(
                    R.drawable.ic_settings_account, stringResource(R.string.my_account),
                    SettingsTypeEnum.MY_ACCOUNT)
            )
            list.add(
                SettingsItemBean(
                    R.drawable.ic_settings_about, stringResource(R.string.about),
                    SettingsTypeEnum.ABOUT)
            )
            SettingsItems(myAccountClick = {
                navController.navigate("myAccount")
            }, aboutClick = {
                navController.navigate("about")
            }, list)
        }
    }
}
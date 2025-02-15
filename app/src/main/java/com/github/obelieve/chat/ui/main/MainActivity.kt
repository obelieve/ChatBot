package com.github.obelieve.chat.ui.main

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.github.obelieve.chat.ui.about.AboutScreen
import com.github.obelieve.chat.ui.account.MyAccountScreen
import com.github.obelieve.chat.ui.conversation.ConversationViewModel
import com.github.obelieve.chat.ui.settings.SettingsScreen
import com.obelieve.frame.utils.StatusBarUtil


/**
 *   @desc content
 *   Created by obelieve
 **/
class MainActivity: ComponentActivity() {


    private val mConversationViewModel by viewModels<ConversationViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StatusBarUtil.setStatusBarColor(this, Color.parseColor("#FF0B0B0B"))
        StatusBarUtil.setWindowLightStatusBar(this,false)
        setContent {
            MaterialTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "main",modifier = Modifier){
                    composable(route = "main"){
                        HomeScreen(navController,mConversationViewModel)
                    }
                    composable(route = "settings"){
                        SettingsScreen(navController = navController)
                    }
                    composable(route = "myAccount"){
                        MyAccountScreen(navController = navController)
                    }
                    composable(route = "about"){
                        AboutScreen()
                    }
                }

            }
        }
    }

    @Preview
    @Composable
    fun PreviewHome(){
        MaterialTheme {
            val navController = rememberNavController()
            HomeScreen(navController, ConversationViewModel())
        }
    }
}
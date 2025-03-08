package com.github.obelieve.chat.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.DrawerValue
import androidx.compose.material.IconButton
import androidx.compose.material.ModalDrawer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.rememberDrawerState
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.github.obelieve.chat.R
import com.github.obelieve.chat.db.Session

import com.github.obelieve.chat.ui.AppColor
import com.github.obelieve.chat.ui.AppTypography
import com.github.obelieve.chat.ui.conversation.ConversationScreen
import com.github.obelieve.chat.ui.conversation.ConversationViewModel
import kotlinx.coroutines.launch

/**
 *   @desc content
 *   Created by obelieve
 **/

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    conversationViewModel: ConversationViewModel
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val historySessions = conversationViewModel.historySessionsLV
    val title by conversationViewModel.titleLV.observeAsState("")
    ModalDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(navController, historySessions, startChatClick = {msg,id->
                conversationViewModel.switchConversation(id)
            })
        },
        content = {
            Scaffold(modifier = Modifier
                .background(AppColor.Background)
                .fillMaxSize(),
                topBar = {
                    MainNavBar(Modifier, title.ifEmpty { stringResource(id = R.string.new_conversation) }, openClick = {
                        scope.launch {
                            drawerState.open()
                        }
                    }, newSessionClick = {
                        conversationViewModel.switchConversation(-1)
                    })
                },
                content = { padding ->
                    Box(modifier = Modifier.padding(padding)) {
                        ConversationScreen(
                            viewModel = conversationViewModel
                        )
                    }
                }
            )
        }
    )
}

@Composable
fun DrawerContent(
    navController: NavController,
    curSessions: List<Session>,
    startChatClick: (String?, Int) -> Unit
) {
    HistoryScreen({ msg, id ->
        startChatClick.invoke(msg, id)
    }, curSessions, {
        navController.navigate("settings")
    })
}

@Composable
fun MainNavBar(
    modifier: Modifier,
    title: String,
    openClick: () -> Unit,
    newSessionClick: () -> Unit
) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = AppColor.Background),
        title = {
            Text(
                text = title,
                maxLines = 1,
                style = AppTypography.titleMedium,
                color = AppColor.Text
            )
        },
        navigationIcon = {
            IconButton(onClick = {
                openClick.invoke()
            }) {
                Icon(Icons.Filled.Menu, tint = AppColor.IconColor, contentDescription = "Menu")
            }
        },
        actions = {
            Row {
                IconButton(onClick = {
                    newSessionClick.invoke()
                }) {
                    Icon(Icons.Rounded.Add, tint = AppColor.IconColor, contentDescription = "more")
                }
            }
        })
}
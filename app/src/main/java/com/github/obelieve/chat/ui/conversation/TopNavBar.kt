package com.github.obelieve.chat.ui.conversation

import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.obelieve.chat.ui.AppColor
import kotlinx.coroutines.delay


@Preview
@Composable
fun testTopNavBar(){
    TopNavBar(modifier = Modifier,"标题",{})
}

/**
 *   @desc content
 *   Created by obelieve
 **/
@Composable
fun TopNavBar(modifier: Modifier, title:String,  moreClick: () -> Unit) {
    SmallTopAppBar(modifier = modifier,colors =  TopAppBarDefaults.smallTopAppBarColors(
        containerColor = AppColor.Background
    ), title = {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            val scrollState = rememberScrollState(0)
            Box(modifier = Modifier.horizontalScroll(scrollState)) {
                Text(text = title, maxLines = 1, color = AppColor.Text)
            }
            LaunchedEffect(Unit) {
                while (true) {
                    delay(16)
                    if (scrollState.value == scrollState.maxValue) {
                        delay(1000)
                        scrollState.scrollTo(0)
                        delay(1000)
                    } else {
                        scrollState.scrollBy(1f)
                    }
                }
            }
        }
    }, navigationIcon = {
        Spacer(modifier = Modifier.size(48.dp))
    }, actions = {
       Row{
           IconButton(onClick = {
               moreClick.invoke()
           }) {
               Icon(Icons.Default.MoreVert, tint = AppColor.White10, contentDescription = "more")
           }
       }
    })
}
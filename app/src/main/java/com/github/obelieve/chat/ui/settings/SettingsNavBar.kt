package com.github.obelieve.chat.ui.settings


import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import com.github.obelieve.chat.R
import com.github.obelieve.chat.ui.AppColor


/**
 *   @desc content
 *   Created by obelieve
 **/
@Preview(showSystemUi = true)
@Composable
fun testSettingsNavBar() {
    SettingsNavBar({})
}

@OptIn(ExperimentalUnitApi::class)
@Composable
fun SettingsNavBar(backClick:()->Unit) {
    Box(
        modifier = Modifier
            .height(40.dp)
            .fillMaxWidth()) {
        Row(modifier = Modifier.fillMaxHeight()) {
            Image(
                painter = painterResource(id = R.drawable.ic_arrow_back),
                modifier = Modifier
                    .size(30.dp)
                    .offset(10.dp).align(Alignment.CenterVertically)
                    .clickable {
                        backClick.invoke()
                    },
                contentDescription = ""
            )
        }
        Text(
            text = "设置",
            color = AppColor.Text,
            modifier = Modifier.align(Alignment.Center),
            fontSize = 20.sp
        )
    }

}
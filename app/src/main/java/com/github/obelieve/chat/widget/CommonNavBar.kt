package com.github.obelieve.chat.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.obelieve.chat.ui.AppColor
import com.github.obelieve.chat.R

/**
 *   @desc content
 *   Created by obelieve
 **/
@Preview(showSystemUi = true)
@Composable
fun testCommonNavBar() {
    CommonNavBar("",{})
}

@Composable
fun CommonNavBar(title:String,closeClick:()->Unit) {
    Box(
        modifier = Modifier
            .height(40.dp)
            .fillMaxWidth()) {
        Text(
            text = title,
            color = AppColor.Text,
            modifier = Modifier.align(Alignment.Center),
            fontSize = 20.sp
        )
        Row(modifier = Modifier.fillMaxHeight().padding(start = 20.dp).align(Alignment.CenterStart)) {
            Image(
                painter = painterResource(id = R.drawable.ic_close),
                modifier = Modifier
                    .size(15.dp).align(Alignment.CenterVertically).clickable {
                        closeClick.invoke()
                    },
                contentDescription = ""
            )
        }
    }

}
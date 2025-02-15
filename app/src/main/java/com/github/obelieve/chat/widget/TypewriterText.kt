package com.github.obelieve.chat.widget

import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import kotlinx.coroutines.delay

/**
 *   打字机效果文本
 *   @desc content
 *   Created by obelieve
 **/
@Preview(showSystemUi = true)
@Composable
fun testTypewriterText() {
    TypewriterText(0,"文本")
}

@Composable
fun TypewriterText(initialIndex:Int, content:String,modifier: Modifier = Modifier, speed:Long = 80,
                   color: Color = Color.Unspecified,fontSize:TextUnit = TextUnit.Unspecified,style: TextStyle = LocalTextStyle.current) {
    if(initialIndex<0||initialIndex>=content.length){
        Text(text = content,modifier = modifier, color = color,style = style)
    }else{
        var index by remember { mutableStateOf(initialIndex) }
        val length = content.length
        val text = if(index<length) content.substring(0,index) else content
        Text(text = text,modifier = modifier, color = color,fontSize = fontSize,style = style)
        LaunchedEffect(key1 = index) {
            delay(speed)
            if (index != length) {
                index++
                if (index > length) {
                    index = length
                }
            }
        }
    }
}

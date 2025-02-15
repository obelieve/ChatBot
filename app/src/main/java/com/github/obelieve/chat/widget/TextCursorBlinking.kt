package com.github.obelieve.chat.widget

import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.delay

/**
 *   @desc content
 *   Created by obelieve
 **/
@Preview(showSystemUi = true)
@Composable
fun testaa() {
    TextCursorBlinking("",Modifier)
}

@Composable
fun TextCursorBlinking(text:String, modifier: Modifier = Modifier,
                       color: Color = Color.Unspecified,cursorColor:Color = Color.Unspecified) {
    var showCursor by remember { mutableStateOf(true) }
    LaunchedEffect(Unit) {
        while (true) {
            delay(500)
            showCursor = !showCursor
        }
    }
    Text(modifier = modifier, text = buildAnnotatedString {
        withStyle(style = SpanStyle(color = color)){
            append(text)
        }
        if(showCursor){
            withStyle(style = SpanStyle(color = cursorColor)){
                append("|")
            }
        }
    })
}
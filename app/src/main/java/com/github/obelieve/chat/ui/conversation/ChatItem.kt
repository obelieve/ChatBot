package com.github.obelieve.chat.ui.conversation

import android.text.TextUtils
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.github.obelieve.chat.R
import com.github.obelieve.chat.db.Message
import com.github.obelieve.chat.ui.AppColor
import com.github.obelieve.chat.ui.AppTypography
import com.github.obelieve.chat.utils.SuperUtils
import com.github.obelieve.chat.widget.TypewriterText
import com.obelieve.frame.utils.ToastUtil


/**
*   @desc content
*   Created by obelieve
**/

@Preview(showSystemUi = true)
@Composable
fun testChatItem(){
    LoadingChat()
}

@Composable
fun SystemView(modifier: Modifier = Modifier.padding(start = 10.dp, end = 20.dp), message: Message) {
    Row(modifier = modifier) {
        Image(painterResource(id = R.drawable.ic_logo), modifier = Modifier
            .clip(CircleShape)
            .size(45.dp), contentDescription = "system")
        Row( modifier = Modifier
            .padding(start = 7.dp)) {
            if(TextUtils.isEmpty(message.content)){
                LoadingChat()
            }
            SelectionContainer{
                ChatText(text = message.content,modifier = Modifier.padding(15.dp),color = Color.Red)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LeftView(modifier: Modifier = Modifier.padding(start = 10.dp, end = 20.dp), message: String) {
    Column(modifier = modifier) {
        Row() {
            Image(painterResource(id = R.drawable.ic_logo), modifier = Modifier
                .clip(CircleShape)
                .size(45.dp), contentDescription = "assistant")
            Column {
                Card(modifier = Modifier.padding(start = 8.dp),border = BorderStroke(1.dp, AppColor.StrokeSelectedCategory)
                    , shape = RoundedCornerShape(15.dp), colors = CardDefaults.cardColors(
                        containerColor = AppColor.SelectedCategory)
                ) {
                    SelectionContainer {
                        ChatText(text = message,modifier = Modifier.padding(15.dp))
                    }
                }
                Row(modifier = Modifier.padding(start = 12.dp, top = 8.dp)) {
                   Image(modifier = Modifier
                       .size(20.dp)
                       .clickable {
                           SuperUtils.share(com.github.obelieve.chat.App.getContext(), message)
                       },painter = painterResource(id = R.drawable.ic_share), contentDescription = "分享")
                    val clipboardManager: ClipboardManager = LocalClipboardManager.current
                   Image(modifier = Modifier
                       .padding(start = 8.dp)
                       .size(20.dp)
                       .clickable {
                           clipboardManager.setText(AnnotatedString(message))
                           ToastUtil.show("复制成功")
                       }, painter = painterResource(id = R.drawable.ic_copy), contentDescription = "复制")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LeftInputtingView(modifier: Modifier = Modifier.padding(start = 10.dp, end = 20.dp),initialIndex:Int, message: String) {
    Column(modifier = modifier) {
        Row() {
            Image(painterResource(id = R.drawable.ic_logo), modifier = Modifier
                .clip(CircleShape)
                .size(45.dp), contentDescription = "assistant")
            Column {
                Card(modifier = Modifier.padding(start = 8.dp),border = BorderStroke(1.dp, AppColor.StrokeSelectedCategory)
                    , shape = RoundedCornerShape(15.dp), colors = CardDefaults.cardColors(
                        containerColor = AppColor.SelectedCategory)
                ) {
                    SelectionContainer {
                        TypewriterText(initialIndex = initialIndex,
                            content = message,
                            modifier = Modifier.padding(15.dp),
                            color = AppColor.Text, style = AppTypography.bodyMedium)
                    }
                }
                Row(modifier = Modifier.padding(start = 12.dp, top = 8.dp)) {
                    Image(modifier = Modifier
                        .size(20.dp)
                        .clickable {
                            SuperUtils.share(com.github.obelieve.chat.App.getContext(), message)
                        },painter = painterResource(id = R.drawable.ic_share), contentDescription = "分享")
                    val clipboardManager: ClipboardManager = LocalClipboardManager.current
                    Image(modifier = Modifier
                        .padding(start = 8.dp)
                        .size(20.dp)
                        .clickable {
                            clipboardManager.setText(AnnotatedString(message))
                            ToastUtil.show("复制成功")
                        }, painter = painterResource(id = R.drawable.ic_copy), contentDescription = "复制")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RightView(modifier: Modifier = Modifier.padding(start = 10.dp, end = 20.dp), message: Message, resendMsgClick:(Message)->Unit) {
    Column {
        Row(modifier = modifier.fillMaxWidth()) {
            Spacer(modifier = Modifier.weight(1f))
            AnimatedVisibility(modifier= Modifier.align(Alignment.CenterVertically),visible = false,enter = fadeIn(),exit = fadeOut()
            ) {
                Image(painter = painterResource(id = R.drawable.ic_send_error),
                    modifier = Modifier
                        .size(20.dp)
                        .clickable {
                            resendMsgClick.invoke(message)
                        }, contentDescription = "error")
            }
            Card(modifier = Modifier.padding(start = 8.dp)
                , shape = RoundedCornerShape(15.dp), colors = CardDefaults.cardColors(
                    containerColor = AppColor.Background2)
            ) {
                SelectionContainer{
                    //字太长会被遮挡
                    ChatText(modifier = Modifier
                        .padding(15.dp)
                        .widthIn(0.dp, 250.dp), text = message.content)
                }
            }
            Spacer(modifier = Modifier.width(10.dp))
            Image(
                painter = painterResource(id = R.drawable.me), modifier = Modifier
                    .clip(CircleShape)
                    .size(45.dp), contentScale = ContentScale.Crop, contentDescription = "user"
            )
        }
        AnimatedVisibility(modifier= Modifier
            .fillMaxWidth()
            .padding(top = 10.dp),visible = false,
            enter = fadeIn(),exit = fadeOut()
        ) {
            Box(modifier= Modifier.fillMaxWidth()) {
                Card(modifier = Modifier.align(Alignment.Center)
                    , shape = RoundedCornerShape(15.dp), colors = CardDefaults.cardColors(
                        containerColor = AppColor.SendErrorBg)
                ){
                    Text(text = message.error, modifier = Modifier
                        .padding(
                            start = 8.dp,
                            end = 8.dp, top = 4.dp, bottom = 4.dp
                        )
                        .widthIn(max = 200.dp),color = AppColor.SendError,maxLines = 1,overflow = TextOverflow.Ellipsis
                        , style = AppTypography.bodyMedium)
                }
            }
        }
    }
}
@Composable
fun LoadingChat(){
    val lottieComposition by rememberLottieComposition(
        //spec = RawRes(R.raw.img_practice),
        spec = LottieCompositionSpec.Asset("chat_loading.json")
    )

    var isPlaying by remember {
        mutableStateOf(true)
    }
    var speed by remember {
        mutableStateOf(3.0f)
    }

    val lottieAnimationState by animateLottieCompositionAsState (
        composition = lottieComposition,
        iterations = LottieConstants.IterateForever,
        isPlaying = isPlaying,
        speed = speed,
        restartOnPlay = true
    )
    LottieAnimation(
        lottieComposition,
        lottieAnimationState,
        modifier = Modifier
            .size(50.dp)
            .padding(end = 0.dp)
    )
}

@Composable
fun ChatText(modifier: Modifier,text:String,color:Color = AppColor.Text){
    Text(text = text, modifier = modifier, color = AppColor.Text
        , style = AppTypography.bodyMedium)
}
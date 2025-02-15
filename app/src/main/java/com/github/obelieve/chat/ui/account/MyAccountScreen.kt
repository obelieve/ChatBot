package com.github.obelieve.chat.ui.account

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.github.obelieve.chat.R
import com.github.obelieve.chat.ui.AppColor
import com.github.obelieve.chat.ui.AppTypography
import com.github.obelieve.chat.widget.CommonNavBar

/**
 *   @desc content
 *   Created by obelieve
 **/
@Composable
fun MyAccountScreen(navController: NavController) {

    Box(
        modifier = Modifier
            .background(color = AppColor.Background)
            .fillMaxSize()
    ) {
        Column {
            CommonNavBar(title = stringResource(id = R.string.my_account)) {
                navController.popBackStack()
            }
            Row(
                Modifier
                    .padding(start = 20.dp, end = 20.dp, top = 20.dp)
                    .height(45.dp)) {
                Text(text = stringResource(R.string.avatar),modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically), color = AppColor.Text2,
                    style = AppTypography.titleMedium)
                Image(
                    painterResource(id =  R.drawable.me),"",modifier = Modifier
                        .clip(CircleShape)
                        .size(35.dp)
                        .align(Alignment.CenterVertically))
            }
            Spacer(modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp, top = 10.dp, bottom = 10.dp)
                .height(1.dp)
                .background(
                    AppColor.BlackB220
                ))
            Row(
                Modifier
                    .padding(start = 20.dp, end = 20.dp)
                    .height(45.dp)) {
                Text(text = stringResource(R.string.account), color = AppColor.Text2, modifier = Modifier.align(Alignment.CenterVertically),
                    style = AppTypography.titleMedium)
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    stringResource(R.string.me),modifier = Modifier
                    .align(Alignment.CenterVertically),
                    style = AppTypography.titleMedium,
                    color = AppColor.Text)
            }

        }
    }
}
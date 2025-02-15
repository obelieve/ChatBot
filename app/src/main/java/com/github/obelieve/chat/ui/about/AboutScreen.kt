package com.github.obelieve.chat.ui.about

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.github.obelieve.chat.R
import com.github.obelieve.chat.ui.AppColor
import com.github.obelieve.chat.ui.AppTypography
import com.obelieve.frame.utils.info.SystemInfoUtil

/**
 *   @desc content
 *   Created by obelieve
 **/

@Composable
fun AboutScreen(){
    Box(
        modifier = Modifier
            .background(color = AppColor.Background)
            .fillMaxSize()
    ) {
        val ctx = LocalContext.current
        Column(modifier = Modifier.align(Alignment.Center)) {
            Image(painter = painterResource(id = R.drawable.ic_logo), modifier = Modifier.size(65.dp).align(
                Alignment.CenterHorizontally), contentDescription = "")
            Spacer(modifier = Modifier.padding(top = 10.dp))
            Text(text = "${stringResource(R.string.main_app_name)} v${SystemInfoUtil.getVersionName(ctx)}",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                color = AppColor.Text, style = AppTypography.titleLarge)
        }
    }
}
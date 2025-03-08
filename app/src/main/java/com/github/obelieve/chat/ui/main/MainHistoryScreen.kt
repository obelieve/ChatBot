package com.github.obelieve.chat.ui.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.github.obelieve.chat.R
import com.github.obelieve.chat.db.Session
import com.github.obelieve.chat.ui.AppColor

/**
 *   @desc content
 *   Created by obelieve
 **/
@Preview(showSystemUi = true)
@Composable
fun testTestHistoryScreen() {
    HistoryScreen({ a, b->}, mutableListOf(),{})
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalUnitApi::class)
@Composable
fun HistoryScreen(startChatClick:(String?,Int)->Unit,
                  curSessions:List<Session>,settingsClick:()->Unit) {
    Column(modifier = Modifier
        .fillMaxSize()
        .background(color = AppColor.Background)) {
        Row {
            IconButton(onClick = {
                settingsClick.invoke()
            }) {
                Icon(Icons.Rounded.Settings, tint = AppColor.IconColor, contentDescription = "more")
            }
        }
        Spacer(modifier = Modifier.padding(5.dp))
        LazyColumn {
            items(curSessions.size) {
                val curSession = curSessions[it]
                if(it==0){
                    Spacer(modifier = Modifier.padding(5.dp))
                }
                Column(modifier = Modifier.padding(start = 10.dp, end = 10.dp)) {
                    Card(
                        modifier = Modifier
                            .wrapContentHeight()
                            .fillMaxWidth()
                            .clickable {
                                startChatClick.invoke(null, curSession.id)
                            },
                        shape = RoundedCornerShape(8.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = AppColor.Background2
                        )
                    ) {
                        ConstraintLayout(
                            modifier = Modifier
                                .padding(16.dp)
                        ) {
                            val (tvSessionTitle, tvSessionDesc, ivArrow) = createRefs()
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .constrainAs(tvSessionTitle) {
                                        top.linkTo(parent.top)
                                        start.linkTo(parent.start, margin = 10.dp)
                                        end.linkTo(ivArrow.start, margin = 10.dp)
                                        horizontalChainWeight = 1f
                                    }, maxLines = 2,
                                text = curSession.title,
                                color = AppColor.Text,
                                fontSize = TextUnit(20F, TextUnitType.Sp)
                            )
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .constrainAs(
                                        tvSessionDesc
                                    ) {
                                        top.linkTo(tvSessionTitle.bottom, margin = 10.dp)
                                        start.linkTo(parent.start, margin = 10.dp)
                                        end.linkTo(ivArrow.start, margin = 10.dp)
                                        horizontalChainWeight = 1f
                                    }, maxLines = 2,
                                text = curSession.getContent(),
                                color = AppColor.Gray10
                            )
                            Image(
                                modifier = Modifier.constrainAs(ivArrow) {
                                    top.linkTo(tvSessionTitle.top, margin = 0.dp)
                                    bottom.linkTo(tvSessionDesc.bottom, margin = 0.dp)
                                    end.linkTo(parent.end, margin = 0.dp)
                                    width = Dimension.fillToConstraints
                                },
                                painter = painterResource(id = R.drawable.ic_arrow_forward),
                                contentDescription = ""
                            )
                        }
                    }
                    Spacer(modifier = Modifier.padding(5.dp))
                }
            }
        }
    }
}
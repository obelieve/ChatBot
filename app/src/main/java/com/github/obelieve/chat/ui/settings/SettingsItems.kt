package com.github.obelieve.chat.ui.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.obelieve.chat.R

import com.github.obelieve.chat.enumtype.SettingsTypeEnum
import com.github.obelieve.chat.model.local.SettingsItemBean
import com.github.obelieve.chat.ui.AppColor

/**
 *   @desc content
 *   Created by obelieve
 **/
@Preview(showSystemUi = true)
@Composable
fun testSettingsItems() {
    val list = mutableListOf<SettingsItemBean>()
    list.add(SettingsItemBean(R.drawable.ic_settings_account,"我的账号", SettingsTypeEnum.MY_ACCOUNT))
    list.add(SettingsItemBean(R.drawable.ic_settings_about,"关于", SettingsTypeEnum.ABOUT))
    SettingsItems({},{},list)
}

@Composable
fun SettingsItems(myAccountClick:()->Unit,
                  aboutClick:()->Unit,
                  list:MutableList<SettingsItemBean>) {
    LazyColumn{
        items(list.size){
            val bean = list[it]
            when(bean.type){
                SettingsTypeEnum.MY_ACCOUNT->{
                    SettingsItemTextType(bean) { myAccountClick.invoke() }
                }
                SettingsTypeEnum.ABOUT->{
                    SettingsItemTextType(bean) {
                        aboutClick.invoke()
                    }
                }
            }
            Spacer(modifier = Modifier.padding(top = 15.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsItemTextType(bean: SettingsItemBean, click:()->Unit){
    Card(modifier = Modifier
        .fillMaxWidth()
        .height(60.dp)
        .padding(start = 10.dp, end = 10.dp)
        .clickable {
            click.invoke()
        },shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = AppColor.Background2)) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)) {
            Image(painter = painterResource(id = bean.icon), contentDescription = "", modifier = Modifier
                .align(Alignment.CenterVertically)
                .size(25.dp))
            Spacer(modifier = Modifier.padding(start = 10.dp))
            Text(text = bean.title, color = AppColor.Text, modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically))
            Image(painter = painterResource(id = R.drawable.ic_settings_arrow_forward), contentDescription = "")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsItemSwitchType(bean: SettingsItemBean, checkChanged:(Boolean)->Unit) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .height(60.dp)
        .padding(start = 10.dp, end = 10.dp)
        , shape = RoundedCornerShape(15.dp), colors = CardDefaults.cardColors(containerColor = AppColor.Background2)) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)) {
            Image(painter = painterResource(id = bean.icon), contentDescription = "", modifier = Modifier.align(Alignment.CenterVertically))
            Spacer(modifier = Modifier.padding(start = 10.dp))
            Text(text = bean.title, color = AppColor.Text, modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically))
            Switch(checked = bean.switch, onCheckedChange = {
                checkChanged(it)
            })
        }
    }
}


package com.github.obelieve.chat.ui.conversation

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.github.obelieve.chat.R
import com.github.obelieve.chat.ui.AppColor


@Composable
fun DeleteConversationDialog(id:Int,title:String,onCancel: () -> Unit, onConfirm: (Int) -> Unit) {
    AlertDialog(
        textContentColor = AppColor.Text,
        titleContentColor = AppColor.Text,
        containerColor = AppColor.Background2,
        onDismissRequest = { onCancel.invoke() },
        title = { Text(text = "删除会话") },
        text = {
            Text(text = title)
        },
        confirmButton = {
            Button(colors = ButtonDefaults.buttonColors(
                containerColor = AppColor.Main
            ),
            onClick = {
                onConfirm.invoke(id)
            }) {
                Text(text = stringResource(id = R.string.dialog_confirm))
            }
        },
        dismissButton = {
            Button(colors = ButtonDefaults.buttonColors(
                containerColor = AppColor.Background2
            ),onClick = {
                onCancel.invoke()
            }) {
                Text(text = stringResource(id = R.string.dialog_cancel))
            }
        },
    )
}

@Preview
@Composable
fun DeleteConversationDialogPreview() {
    DeleteConversationDialog(0,"",{}, {})
}
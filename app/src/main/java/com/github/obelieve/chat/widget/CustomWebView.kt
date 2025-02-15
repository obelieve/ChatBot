package com.github.obelieve.chat.widget

import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import com.obelieve.frame.view.AppWebView

/**
 *   @desc content
 *   Created by obelieve
 **/
@Preview(showSystemUi = true)
@Composable
fun testCustomWebView() {
    CustomWebView("https://www.baidu.com",{})
}

@Composable
fun CustomWebView(url:String,getTitle:(String)->Unit) {
    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            AppWebView(context).apply {
                setWebChromeClient(object : WebChromeClient() {
                    override fun onReceivedTitle(view: WebView?, title: String?) {
                        super.onReceivedTitle(view, title)
                        title?.let {
                            getTitle.invoke(it)
                        }
                    }
                });
               loadUrl(url)

            }
        },
        update = { view ->
        }
    )
}
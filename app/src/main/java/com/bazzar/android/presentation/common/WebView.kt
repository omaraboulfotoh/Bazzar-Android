package com.bazzar.android.presentation.common

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.bazzar.android.presentation.composables.BazzarAppBar
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.ResultBackNavigator

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Destination
@Composable
fun WebViewScreen(
    navigator: DestinationsNavigator,
    title: String? = null,
    resultNavigator: ResultBackNavigator<Boolean>,
    url: String
) {
    val backDispatcher =
        LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher

    Scaffold(
        topBar = {
            title?.let {
                BazzarAppBar(
                    title = it
                ) { backDispatcher?.onBackPressed() }
            }

        }
    ) {
        if (url.isNotEmpty()) {
            WebView(data = url, webViewClient = object : WebViewClient() {
                override fun doUpdateVisitedHistory(
                    view: WebView?,
                    url: String?,
                    isReload: Boolean,
                ) {
                    super.doUpdateVisitedHistory(view, url, isReload)
                    url?.let {
                        if (it.contains("PaymentSuccess")) {
                            resultNavigator.navigateBack(true)
                        } else if (it.contains("PaymentCanceled")) {
                            resultNavigator.navigateBack(false)
                        }
                    }
                }
            })
        } else {
            Toast.makeText(LocalContext.current, "Empty Url", Toast.LENGTH_LONG).show()
        }
    }

}


@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebView(
    modifier: Modifier = Modifier,
    data: String,
    webViewClient: WebViewClient = WebViewClient(),
) {
    AndroidView(
        modifier = modifier,
        factory = { context ->
            WebView(context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                settings.javaScriptEnabled = true
                settings.useWideViewPort = true
                setInitialScale(1)
                this.webViewClient = webViewClient

            }.also {
                it.loadUrl(data)
            }
        }
    )
}

package com.example.interneeapp

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.view.KeyEvent
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavHostController

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(navController: NavHostController, authViewModel: AuthViewModel){
    val authState = authViewModel.authStatus.observeAsState()

    LaunchedEffect(authState.value) {
        when(authState.value){
            is AuthStates.Unauthenticated -> navController.navigate("SignIn")
            else -> Unit
        }
    }
    Scaffold (

        topBar = {
            TopBar(modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, end = 8.dp)
                .statusBarsPadding()
                .fillMaxWidth()
                .height(56.dp)
                .background(color = Color.White),
                onClick = {authViewModel.SignOutMethod()}
                )
                 },

    ){
        Column(
            modifier= Modifier.fillMaxSize()
        ) {
            WebViewScreen(url = "https://internee.pk/")

        }
    }
  //  WebViewScreen(url = "https://task.internee.pk/index.php?page=home")

}

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebViewScreen(url:String){
    val loaderDialogScreen = remember { mutableStateOf(false) }
    if (loaderDialogScreen.value) {

        // Dialog function
        Dialog(
            onDismissRequest = {
                loaderDialogScreen.value = false
            },
            properties = DialogProperties(
                usePlatformDefaultWidth = false // experimental
            )
        ) {
            Surface(modifier = Modifier.fillMaxSize()) {

                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                  CircularProgressIndicator()

                }

            }
        }

    }


    //..........................................................................
    AndroidView(
        factory = {
            WebView(it).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                webViewClient = WebViewClient()

                // to play video on a web view
                settings.javaScriptEnabled = true

                webViewClient = object : WebViewClient() {

                    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                        super.onPageStarted(view, url, favicon)
                        // show dialog
                        loaderDialogScreen.value = true
                    }

                    override fun onPageFinished(view: WebView?, url: String?) {
                        super.onPageFinished(view, url)
                        // hide dialog
                        loaderDialogScreen.value = false
                        enableWebsiteButtons(this@apply)
                    }
                }

                loadUrl(url)

                setOnKeyListener { _, keyCode, event ->
                    if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_UP && canGoBack()) {
                        goBack()
                        true
                    } else {
                        false
                    }
                }
            }
        }, update = {
            it.loadUrl(url)
        })
}

fun enableWebsiteButtons(webView: WebView) {
    val script = """
        // Example JavaScript code to enable buttons
        var buttons = document.getElementsByTagName("button");
        for (var i = 0; i < buttons.length; i++) {
            buttons[i].disabled = false;
        }
    """.trimIndent()

    webView.evaluateJavascript(script, null)
}

@Composable
fun TopBar (modifier: Modifier = Modifier,onClick:()->Unit){

    Row (
        modifier= modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Image(
            painter = painterResource(id = R.drawable.internee_logo),
            contentDescription =null,
            modifier = Modifier.size(200.dp)
        )
        IconButton(
            onClick = {
                onClick()
            }
        ) {
            Image(
                painter = painterResource(id = R.drawable.power),
                contentDescription =null,
                modifier = Modifier.size(30.dp)
            )
        }

    }
}

@Composable
@Preview(showBackground = true)
fun TopBarPreview (){
//TopBar()
}



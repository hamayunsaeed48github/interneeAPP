package com.example.interneeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.interneeapp.ui.theme.InterneeAppTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val authViewModel:AuthViewModel by viewModels()
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            InterneeAppTheme {
               SetStatusBarColor(color = Color(0xFFFFFFFF))

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyAppNavigation(authViewModel=authViewModel)

                }
            }
        }
    }
}
//@Composable
//fun App() {
//    val navController = rememberNavController()
//
//    NavHost(navController = navController, startDestination = "splash_screen") {
//        composable("splash_screen") {
//            SplashScreen(navController = navController)
//        }
//        composable("main_screen") {
//            MainScreen()
//        }
//        // Add more composable destinations as needed
//    }
//}

@Composable
fun SetStatusBarColor(color: Color) {
    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setStatusBarColor(color=color)
    }
}




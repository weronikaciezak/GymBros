package com.example.gymbros

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.gymbros.screens.LoginScreen
import com.example.gymbros.screens.PreferencesScreen
import com.example.gymbros.screens.SignUpScreen
import com.example.gymbros.viewModels.AuthViewModel

class MainActivity : ComponentActivity() {
    private val authViewModel = AuthViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavHost(navController, startDestination = "login") {
                composable("login") { LoginScreen(navController, authViewModel) }
                composable("signup") { SignUpScreen(navController, authViewModel) }
                composable("preferences") { PreferencesScreen(authViewModel) }
            }
        }
    }

}
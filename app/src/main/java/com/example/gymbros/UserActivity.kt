package com.example.gymbros

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.gymbros.viewModels.DatabaseViewModel

class UserActivity : ComponentActivity() {
    private val databaseViewModel = DatabaseViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavHost(navController, startDestination = "home") {
                composable("home") { HomePage(navController, databaseViewModel) }
                composable("profile") { Profile(navController, databaseViewModel) }
                composable("match") { Match(databaseViewModel) }
            }
        }
    }
}
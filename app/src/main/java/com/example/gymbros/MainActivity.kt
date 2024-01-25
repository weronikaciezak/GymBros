package com.example.gymbros

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.gymbros.functions.NavigationBar
import com.example.gymbros.functions.TopSection
import com.example.gymbros.ui.theme.GymBrosTheme
import com.example.gymbros.viewModels.DatabaseViewModel

class MainActivity : ComponentActivity() {
    private val databaseViewModel = DatabaseViewModel()
    private val get = databaseViewModel.getUsername()
    private var username = databaseViewModel.currentUsername

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavHost(navController, startDestination = "login") {
                composable("login") { LoginScreen(navController, viewModel(), databaseViewModel) }
                composable("signup") { SignUpScreen(navController, viewModel()) }
                composable("home") { HomePage(navController, databaseViewModel) }
                composable("profile") { Profile(navController, username) }
                composable("match") { Match(databaseViewModel) }
            }
        }
    }
}


@SuppressLint("UnrememberedMutableState")
@Composable
fun HomePage(navController: NavController, databaseViewModel: DatabaseViewModel) {
    val context = LocalContext.current
    GymBrosTheme {
        Scaffold(
            bottomBar = {
                NavigationBar(context, navController, databaseViewModel)
            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                TopSection(databaseViewModel)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    HomePage(navController = rememberNavController(), databaseViewModel = viewModel())
}

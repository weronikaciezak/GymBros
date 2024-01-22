package com.example.gymbros

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
import com.example.gymbros.viewModels.CloudViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class MainActivity : ComponentActivity() {

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        //viewModel = ViewModelProvider(this).get(CloudViewModel::class.java)
        setContent {
            val navController = rememberNavController()
            NavHost(navController, startDestination = "login") {
                composable("login") { LoginScreen(navController, viewModel()) }
                composable("signup") { SignUpScreen(navController, viewModel()) }
                composable("home") { HomePage(navController) }
                composable("profile") { Profile(navController, "username") }
//                composable("profile/{userId}") { backStackEntry ->
//                    backStackEntry.arguments?.getString("userId")
//                        ?.let { Profile(navController, it) }
//                }
                composable("match") { Match(CloudViewModel()) }
            }
        }
    }
}


@Composable
fun HomePage(navController: NavController) {
    val context = LocalContext.current
    GymBrosTheme {
        Scaffold(
            bottomBar = {
                NavigationBar(context, navController)
            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                TopSection()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    HomePage(navController = rememberNavController())
}
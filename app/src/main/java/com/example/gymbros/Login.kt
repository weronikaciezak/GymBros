package com.example.gymbros

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.gymbros.viewModels.AuthViewModel

@Composable
fun LoginScreen(navController: NavController, authViewModel: AuthViewModel) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var text by remember { mutableStateOf("") }
    val context = LocalContext.current
    val i = Intent(context, UserActivity::class.java)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(value = email, onValueChange = { email = it; text = "" }, label = { Text("Email") })
        TextField(value = password, onValueChange = { password = it; text = "" }, label = { Text("Password") })
        Text(text, color = MaterialTheme.colorScheme.error)
        Button(onClick = {
            authViewModel.signInWithEmailAndPassword(email, password)
        }) {
            Text("Login")
        }
        //Text(text = "Nie masz jeszcze konta?", color = Mango)
        Button(onClick = { navController.navigate("signup") }) {
            Text("Sign Up")
        }
        LaunchedEffect(authViewModel.authStatus) {
            authViewModel.authStatus.observeForever { status ->
                if (status == true) {
                    context.startActivity(i)
                } else {
                    text = "nuh uh"
                }
            }
        }
    }
}
@Composable
fun SignUpScreen(navController: NavController, authViewModel: AuthViewModel) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var text by remember { mutableStateOf("") }
    val context = LocalContext.current
    val i = Intent(context, UserActivity::class.java)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(value = email, onValueChange = { email = it; text = "" }, label = { Text("Email") })
        TextField(value = password, onValueChange = { password = it }, label = { Text("Password") })
        TextField(value = username, onValueChange = { username = it }, label = { Text("Username") })
        Button(onClick = {
            authViewModel.signUpWithEmailAndPassword(email, password, username, navController)
        }) {
            Text("Sign Up")
        }
        Text(text, color = MaterialTheme.colorScheme.error)
        LaunchedEffect(authViewModel.authStatus) {
            authViewModel.authStatus.observeForever { status ->
                if (status == true) {
                    context.startActivity(i)
                } else {
                    text = "nuh uh"
                }
            }
        }
    }
}

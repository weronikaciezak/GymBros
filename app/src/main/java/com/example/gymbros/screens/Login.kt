package com.example.gymbros.screens

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
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
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.gymbros.Preferences
import com.example.gymbros.UserActivity
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
        TextField(
            value = email,
            onValueChange = { email = it; text = "" },
            label = { Text("Email") })
        TextField(
            value = password,
            onValueChange = { password = it; text = "" },
            label = { Text("Password") })
        Text(text, color = MaterialTheme.colorScheme.error)
        Button(onClick = {
            authViewModel.signInWithEmailAndPassword(email, password)
        }) {
            Text("Login")
        }
        Button(
            onClick = {
                navController.navigate("signup")
            }, modifier = Modifier.padding(end = 10.dp),
            shape = RoundedCornerShape(50),
        ) {
            Text("Sign Up")
        }
        Text(text = "No account yet? Sign up!",
            Modifier.clickable(onClick = {
                navController.navigate("signup")
            }))
        LaunchedEffect(authViewModel.authStatus) {
            authViewModel.authStatus.observeForever { status ->
                if (status == true) {
                    context.startActivity(i)
                } else {
                    text = "nuh uh" //TODO: change to proper error message
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = email,
            onValueChange = { email = it; text = "" },
            label = { Text("Email") })
        TextField(value = password, onValueChange = { password = it }, label = { Text("Password") })
        TextField(value = username, onValueChange = { username = it }, label = { Text("Username") })
        Button(onClick = {
            authViewModel.signUpWithEmailAndPassword(email, password, username)
        }) {
            Text("Sign Up")
        }
        Text(text, color = MaterialTheme.colorScheme.error)
        LaunchedEffect(authViewModel.authRegStatus) {
            authViewModel.authRegStatus.observeForever { status ->
                if (status == true) {
                    navController.navigate("preferences")
                } else {
                    text = "nuh uh"
                }
            }
        }
    }
}

@Composable
fun PreferencesScreen(authViewModel: AuthViewModel) {
    val context = LocalContext.current
    val i = Intent(context, UserActivity::class.java)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
    ) {
        val preference = remember { mutableStateOf("") }

        PreferencesSelectionScreen(onPreferenceSelected = {
            preference.value = it
        })
        Button(onClick = {
            authViewModel.setPreference(preference.value)
            context.startActivity(i)
        }) {
            Text(text = "Let's go!")
        }
    }
}

@Composable
@Preview(showBackground = true)
fun ShowPreferencesScreen() {
    PreferencesScreen(AuthViewModel())
}

@Composable
fun PreferencesSelectionScreen(onPreferenceSelected: (String) -> Unit) {
    val preferences = Preferences.entries.map { it.description }
    var selectedPreference by remember { mutableStateOf(preferences[0]) }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Choose your fitness preference", style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(16.dp))

        preferences.forEach { preference ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .selectable(
                        selected = (selectedPreference == preference),
                        onClick = {
                            selectedPreference = preference; onPreferenceSelected(preference)
                        },
                        role = Role.RadioButton
                    )
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    modifier = Modifier.padding(end = 16.dp),
                    selected = (selectedPreference == preference),
                    onClick = null
                )
                Text(text = preference)
            }
        }
    }
}
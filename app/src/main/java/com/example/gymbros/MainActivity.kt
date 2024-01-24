package com.example.gymbros

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
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
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.firestore

class MainActivity : ComponentActivity() {
    var username = "username"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavHost(navController, startDestination = "login") {
                composable("login") { LoginScreen(navController, viewModel()) }
                composable("signup") { SignUpScreen(navController, viewModel()) }
                composable("home") { HomePage(navController, username) }
                composable("profile") { Profile(navController, username) }
                composable("match") { Match(CloudViewModel()) }
                composable("damn") { Damn() }
            }
        }
    }
}


@Composable
fun HomePage(navController: NavController, username: String) {
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
                TopSection(username)
                Button(onClick = { navController.navigate("damn") {
                    popUpTo("home") { inclusive = true }
                } }) {
                    Text(text = "Go to Damn")
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    HomePage(navController = rememberNavController(), "username")
}
@Composable
fun Damn(viewModel: MyViewModel = viewModel()) {
    Column {
        Button(onClick = { viewModel.fetchNextUser() }) {
            Text("Fetch User")
        }
        Text(text = viewModel.userData.value?:"null")
    }
}

class MyViewModel : ViewModel() {
    val db = Firebase.firestore
    var lastVisible: DocumentSnapshot? = null
    val userData = mutableStateOf("username")

    fun fetchNextUser() {
        var query = db.collection("users")
            .orderBy("username")
            .limit(1)

        if (lastVisible != null) {
            query = query.startAfter(lastVisible!!)
        }

        query.get().addOnSuccessListener { documentSnapshots ->
            if (!documentSnapshots.isEmpty) {
                val user = documentSnapshots.documents[0]
                println(user.id + " => " + user.data)
                val username = user.getString("username")
                userData.value = username ?: "null"
                lastVisible = user
            } else {
                println("No more users.")
                //lastVisible = null //to jest żeby w kółko pobierało
            }
        }
    }
}
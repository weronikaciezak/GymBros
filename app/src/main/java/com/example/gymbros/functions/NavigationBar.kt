package com.example.gymbros.functions

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.gymbros.ui.theme.GymBrosTheme

@Composable
fun NavigationBar(context: Context, navController: NavController) {
    //NavHost(navController, startDestination = "") {
    //    composable("profile") { Profile(navController, "username") }
    //}
//    val firebaseAuth = FirebaseAuth.getInstance()
//    val user = firebaseAuth.currentUser
//    val userId = user?.uid
//    lateinit var viewModel: CloudViewModel
//    val username = userId?.let { viewModel.getUsername(it) }
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Button(onClick = {
            navController.navigate("home")
        }) { Text(text = "Home") }

        Spacer(modifier = Modifier.width(16.dp))

        Button(onClick = {
            navController.navigate("match")
        }) { Text(text = "Match") }

        Spacer(modifier = Modifier.width(16.dp))

        Button(onClick = {
            navController.navigate("profile")
        }) { Text(text = "Profile") }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GymBrosTheme {
        NavigationBar(context = LocalContext.current, rememberNavController())
    }
}
package com.example.gymbros.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.gymbros.R
import com.example.gymbros.functions.NavigationBar
import com.example.gymbros.viewModels.DatabaseViewModel

@Composable
fun Profile(navController: NavController, databaseViewModel: DatabaseViewModel) {
    val username by remember { mutableStateOf(databaseViewModel.currentUser.value.username) }
    val context = LocalContext.current

    Scaffold(
        bottomBar = {
            NavigationBar(navController)
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            //verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                modifier = Modifier.padding(16.dp),
                painter = painterResource(id = R.drawable.user_default_icon),
                contentDescription = null
            )

            Text(text = "Welcome $username!", style = MaterialTheme.typography.h4)
            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = { }) {
//                AuthViewModel().signOut(context)
//          }) {
                Text(text = "Sign Out")
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun ProfilePreview() {
//    Profile(rememberNavController())
//}
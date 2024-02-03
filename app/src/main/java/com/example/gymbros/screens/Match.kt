package com.example.gymbros.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.gymbros.R
import com.example.gymbros.functions.NavigationBar
import com.example.gymbros.viewModels.DatabaseViewModel

@Composable
fun Match(navController: NavController, viewModel: DatabaseViewModel) {
    val user = viewModel.fetchedUser.value
    Scaffold(
        bottomBar = {
            NavigationBar(navController)
        }
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.user_default_icon),
                contentDescription = "user icon",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clip(RoundedCornerShape(15.dp))
            )

            Text(text = "${user.username}'s Profile")
            Text(text = "Bio: ${user.bio}")
            Text(text = "Preference: ${user.preference}")

            Spacer(modifier = Modifier.padding(16.dp))
            Row {
                Button(
                    onClick = {
                        viewModel.sendFriendRequest()
                        viewModel.fetchNextUser()
                    }, modifier = Modifier
                        .clip(RoundedCornerShape(15.dp))
                        .padding(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Green,
                        contentColor = Color.White
                    )
                ) {
                    Text("Add Friend")
                }
                Button(
                    onClick = { viewModel.fetchNextUser() },
                    modifier = Modifier
                        .clip(RoundedCornerShape(15.dp))
                        .padding(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Red,
                        contentColor = Color.White
                    )
                ) {
                    Text("Next User")
                }
            }
        }
    }
}

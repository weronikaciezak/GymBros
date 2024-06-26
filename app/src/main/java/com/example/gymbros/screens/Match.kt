package com.example.gymbros.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
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
        if (user.id != "null") {
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

                Text(text = "${user.username}", style = MaterialTheme.typography.subtitle1)
                Text(text = "Preference: ${user.preference}")
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.width(300.dp)
                ) {
                    Text(text = "${user.bio}")
                }
                //Text(text = "${user.bio}", modifier = Modifier.fillMaxWidth().align(Alignment.CenterHorizontally).padding(horizontal =  16.dp))

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
        } else {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ){
                Text("No more users to match with!", modifier = Modifier.padding(16.dp), color = Color.Gray)

                Text("Refresh", modifier = Modifier.clickable {
                    viewModel.fetchNextUser()
                })
            }
        }
    }
}

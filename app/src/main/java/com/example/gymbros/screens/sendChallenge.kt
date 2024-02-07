package com.example.gymbros.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Scaffold
import androidx.compose.material.TextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.gymbros.viewModels.DatabaseViewModel

@Composable
fun SendChallenge(navController: NavController, databaseViewModel: DatabaseViewModel) {
    val friends = databaseViewModel.listOfFriends
    val context = LocalContext.current
    var text by remember { mutableStateOf("") }
    val selectedFriends = remember { mutableStateListOf<String>() }

    Scaffold(
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Button(onClick = {
                    navController.navigate("Home")
                },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.White,
                        contentColor = Color.Black
                    )) {
                    Text("Cancel")
                }
                if(friends.isNotEmpty()) {
                    Button(
                        onClick = {
                            databaseViewModel.sendChallenge(selectedFriends, text, context)
                            navController.navigate("Home")
                        },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.LightGray,
                            contentColor = Color.White
                        )
                    ) {
                        Text("Send")
                    }
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            if(friends.isNotEmpty()) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Send someone a challenge!")
                TextField(
                    value = text,
                    onValueChange = { text = it },
                )
            } else {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = "You have no friends to challenge!")
                }

            }
            friends.forEach { friend ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(30.dp)
                        .selectable(
                            selected = selectedFriends.contains(friend.username),
                            onClick = {
                                if (selectedFriends.contains(friend.username)) {
                                    selectedFriends.remove(friend.username)
                                } else {
                                    friend.username?.let { selectedFriends.add(it) }
                                }
                            },
                            role = Role.RadioButton
                        )
                        .padding(horizontal = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        modifier = Modifier.padding(end = 10.dp),
                        selected = selectedFriends.contains(friend.username),
                        onClick = null
                    )
                    friend.username?.let { Text(text = it) }
                }
            }
        }
    }
}
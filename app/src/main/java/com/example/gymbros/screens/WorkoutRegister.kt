package com.example.gymbros.screens

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.gymbros.Preferences
import com.example.gymbros.Workout
import com.example.gymbros.viewModels.DatabaseViewModel

@SuppressLint("UnrememberedMutableState")
@Composable
fun WorkoutRegister(navController: NavController, databaseViewModel: DatabaseViewModel) {
    val friends = databaseViewModel.listOfFriends
    val context = LocalContext.current

    val preferences = Preferences.entries.map { it.description }
    var type by remember { mutableStateOf(preferences[0]) }
    var date by remember { mutableStateOf("") }
    var duration by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    val selectedFriends = remember { mutableStateListOf<String>() }

    Scaffold(
        topBar = {
                 Row(
                     modifier = Modifier
                         .background(Color.White)
                         .padding(16.dp),
                     horizontalArrangement = Arrangement.SpaceAround
                 ) {
                     Text("Register your workout here!", style = MaterialTheme.typography.h5)                 }
                 },
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
                Button(onClick = {
                    databaseViewModel.currentUser.value.username?.let { selectedFriends.add(it) }
                    val users = databaseViewModel.changeFromUsernametoId(selectedFriends)
                    databaseViewModel.registerWorkout(Workout("null", type, description, date, duration, selectedFriends), users)
                    Toast.makeText(context, "Workout registered successfully!", Toast.LENGTH_SHORT).show()
                    navController.navigate("Home")
                },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.LightGray,
                        contentColor = Color.White
                    )) {
                    Text("Done")
                }
            }
        }
    ) { padding ->
        Box(modifier = Modifier.verticalScroll(rememberScrollState())) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
            ) {

                Text(text = "Choose the type of workout you did!")
                preferences.forEach { preference ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(30.dp)
                            .selectable(
                                selected = (type == preference),
                                onClick = {
                                    type = preference;
                                },
                                role = Role.RadioButton
                            )
                            .padding(horizontal = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            modifier = Modifier.padding(end = 10.dp),
                            selected = (type == preference),
                            onClick = null
                        )
                        Text(text = preference)
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Choose the date and duration")
                Row {
                    TextField(
                        value = date,
                        onValueChange = { date = it },
                        label = { Text("date in dd.mm.yy") },
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    TextField(
                        value = duration,
                        onValueChange = { duration = it },
                        label = { Text("duration") },
                        modifier = Modifier.weight(1f)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Add a description")
                TextField(value = description, onValueChange = { description = it })
                Spacer(modifier = Modifier.height(16.dp))
                if(friends.isNotEmpty()) Text(text = "Add participants") //TODO: check if works

                friends.forEach() { friend ->
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
}

@Preview(showBackground = true)
@Composable
fun WorkoutRegisterPreview() {
    //WorkoutRegister(rememberNavController())
}
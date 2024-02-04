package com.example.gymbros.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material3.RadioButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import com.example.gymbros.viewModels.DatabaseViewModel

@Composable
fun EditProfile(navController: NavController, databaseViewModel: DatabaseViewModel) {
    val preferences = Preferences.entries.map { it.description }
    var type by remember { mutableStateOf(databaseViewModel.currentUser.value.preference ?: "") }
    var username by remember { mutableStateOf(databaseViewModel.currentUser.value.username ?: "") }
    var bio by remember { mutableStateOf(databaseViewModel.currentUser.value.bio ?: "") }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp),
        verticalArrangement = Arrangement.Center,
    ) {
        Text("Edit your profile", style = MaterialTheme.typography.h6)
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Username: ", modifier = Modifier.width(70.dp))
            Spacer(modifier = Modifier.width(16.dp))
            TextField(value = username, onValueChange = { username = it })
        }
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Bio:", modifier = Modifier.width(70.dp))
            Spacer(modifier = Modifier.width(16.dp))
            TextField(value = bio, onValueChange = { bio = it })
        }
        Spacer(modifier = Modifier.height(16.dp))
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


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = {
                navController.navigate("Profile")
            },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.White,
                    contentColor = Color.Black
                )) {
                Text("Dismiss")
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(onClick = {
                databaseViewModel.editProfile(username, type, bio)
                databaseViewModel.fetchNextUser() //TODO: change IF USELESS
                Toast.makeText(context, "Changes saved!", Toast.LENGTH_SHORT).show()
                navController.navigate("Profile")
            },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.LightGray,
                    contentColor = Color.White
                )) {
                Text("Save")
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun Show() {
    //EditProfile(rememberNavController())
}
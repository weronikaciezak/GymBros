package com.example.gymbros.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.gymbros.R
import com.example.gymbros.Workout
import com.example.gymbros.functions.NavigationBar
import com.example.gymbros.ui.theme.Mango
import com.example.gymbros.viewModels.AuthViewModel
import com.example.gymbros.viewModels.DatabaseViewModel

@Composable
fun Profile(navController: NavController, databaseViewModel: DatabaseViewModel) {
    val user = databaseViewModel.currentUser.value
    databaseViewModel.getUserData()
    databaseViewModel.getWorkouts()
    val workouts = databaseViewModel.listOfWorkouts
    val w = workouts.distinctBy { it.id }
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
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.user_default_icon),
                contentDescription = null,
                colorFilter = ColorFilter.colorMatrix(ColorMatrix().apply { setToSaturation(0f) }),
                modifier = Modifier
                    .padding(16.dp)
                    .size(200.dp)
                    .clip(shape = RoundedCornerShape(100.dp))
            )

            Text(text = "${user.username}", style = MaterialTheme.typography.h4)
            Spacer(modifier = Modifier.height(14.dp))
            Text(text = "Preference: ${user.preference}", fontWeight = FontWeight.Bold)
            Text(text = "${user.bio}")
            Spacer(modifier = Modifier.height(10.dp))

            Row {
                Button(
                    onClick = {
                        navController.navigate("editProfile")
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.White,
                        contentColor = Color.Black
                    )
                ) {
                    Text(text = "Edit profile")
                }
                Spacer(modifier = Modifier.width(16.dp))
                Button(
                    onClick = {
                        AuthViewModel().signOut(context) //TODO: implement sign out
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.LightGray,
                        contentColor = Color.White
                    )
                ) {
                    Text(text = "Sign Out")
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Divider(color = Color.LightGray, thickness = 1.dp)

            Box (
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .padding(16.dp)
            ) {
                LazyColumn {
                    items(w) { workout ->
                        WorkoutInfoBlock(workout)
                    }
                }
            }
            Box(modifier = Modifier.fillMaxWidth().height(75.dp))
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomEnd
    ) {
        FloatingActionButton(
            onClick = {
                navController.navigate("workoutRegister")
            },
            backgroundColor = Mango,
            modifier = Modifier
                .padding(16.dp)
                .offset(y = (-60).dp)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add", tint = Color.White)
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun WorkoutInfoBlock(workout: Workout) {
    val type = workout.type
    val duration = workout.duration
    val date = workout.date
    val description = workout.description
    val participants = workout.participants
    Card (
        backgroundColor = Color.Gray,
        shape = RoundedCornerShape(15.dp),
        modifier = Modifier
            .padding(10.dp)
            .width(300.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.padding(16.dp)
        ) {
            if(type != "") Text(text = "$type", style = MaterialTheme.typography.subtitle1, color = Color.White)
            if(duration != "") Text(text = "Time: $duration", style = MaterialTheme.typography.body1, color = Color.White)
            if(date != "") Text(text = "Date: $date", style = MaterialTheme.typography.body1, color = Color.White)
            if(description != "") Text(text = "$description", style = MaterialTheme.typography.body1, color = Color.White)
            if (participants != null) {
                Text(text = "Participants: $participants", style = MaterialTheme.typography.body1, color = Color.White)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfilePreview() {
    //Profile(rememberNavController())
    //WorkoutInfoBlock("type", "time")
}
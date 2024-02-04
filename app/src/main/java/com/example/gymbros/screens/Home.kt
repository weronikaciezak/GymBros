package com.example.gymbros.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.gymbros.functions.ChallengeBox
import com.example.gymbros.functions.NavigationBar
import com.example.gymbros.functions.TopSection
import com.example.gymbros.ui.theme.GymBrosTheme
import com.example.gymbros.viewModels.DatabaseViewModel

@Composable
fun HomePage(navController: NavController, databaseViewModel: DatabaseViewModel) {
    databaseViewModel.fetchDataFromFirebase()//to bylo po getuserdata
    databaseViewModel.getUserData() //wtedy dziala
    databaseViewModel.fetchNextChallenge()
    GymBrosTheme {
        Scaffold(
            bottomBar = {
                NavigationBar(navController)
            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                val username = databaseViewModel.currentUser.value.username
                if (username != null) {
                    TopSection(username)
                }
                Text(text = "Welcome Back!",
                    fontSize = 30.sp,
                    modifier = Modifier.padding(20.dp),
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(20.dp))
                Row (
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    Box(
                        modifier = Modifier
                            .height(120.dp)
                            .width(170.dp)
                            .shadow(5.dp)
                            .background(Color.LightGray, shape = RoundedCornerShape(15.dp))
                            .clickable {
                                navController.navigate("workoutRegister")
                            }

                    ) { Text(text = "Register workout", color = Color.White, modifier = Modifier.padding(10.dp), style = MaterialTheme.typography.h6) }

                    Box(
                        modifier = Modifier
                            .height(120.dp)
                            .width(170.dp)
                            .shadow(5.dp)
                            .background(Color.White, shape = RoundedCornerShape(15.dp))
                            .clickable {
                                navController.navigate("challenge")
                            }
                    ) { Text(text = "Challenge someone!", modifier = Modifier.padding(10.dp), style = MaterialTheme.typography.h6) }
                }
                val challenge = databaseViewModel.fetchedChallenge.value
                ChallengeBox(challenge, databaseViewModel)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomePagePreview() {
    //HomePage(navController = rememberNavController(), viewModel())
}

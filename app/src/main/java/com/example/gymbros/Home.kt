package com.example.gymbros

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.gymbros.functions.ChallengeBox
import com.example.gymbros.functions.Chart
import com.example.gymbros.functions.NavigationBar
import com.example.gymbros.functions.TopSection
import com.example.gymbros.ui.theme.GymBrosTheme
import com.example.gymbros.viewModels.DatabaseViewModel

@Composable
fun HomePage(navController: NavController, databaseViewModel: DatabaseViewModel) {
    databaseViewModel.getUsername() //wtedy dziala
    databaseViewModel.fetchDataFromFirebase()
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
                val username = databaseViewModel.currentUsername.value
                val users = databaseViewModel.listOfFriends
                TopSection(username)
                Text(text = "Welcome Back!",
                    fontSize = 30.sp,
                    modifier = Modifier.padding(20.dp),
                    fontWeight = FontWeight.Bold
                )
                //ShowLazyList(users)
                Chart()
                ChallengeBox()

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomePagePreview() {
    HomePage(navController = rememberNavController(), viewModel())
}

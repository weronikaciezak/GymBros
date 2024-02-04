package com.example.gymbros.functions

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material.icons.rounded.Face
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.gymbros.ui.theme.GymBrosTheme

@Composable
fun NavigationBar(navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(Color.White),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Button(
            onClick = {
                navController.navigate("home")
            },
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.Black,
                containerColor = Color.White
            )
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Icon(
                    imageVector = Icons.Rounded.Home,
                    contentDescription = "home",
                    tint = Color.Black//MaterialTheme.colorScheme.onSecondaryContainer
                )
                Text(text = "Home")
            }
        }

        //Spacer(modifier = Modifier.width(16.dp))

        Button(
            onClick = {
                navController.navigate("match")
            },
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.Black,
                containerColor = Color.White
            )
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Icon(
                    imageVector = Icons.Rounded.AddCircle,
                    contentDescription = "match",
                    tint = Color.Black//MaterialTheme.colorScheme.onSecondaryContainer
                )
                Text(text = "Match")
            }
        }

        //Spacer(modifier = Modifier.width(16.dp))

        Button(
            onClick = {
                navController.navigate("friends")
            },
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.Black,
                containerColor = Color.White
            )
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Icon(
                    imageVector = Icons.Rounded.Face,
                    contentDescription = "friends",
                    tint = Color.Black//MaterialTheme.colorScheme.onSecondaryContainer
                )
                Text(text = "Friends")
            }
        }

        //Spacer(modifier = Modifier.width(16.dp))


        Button(
            onClick = {
                navController.navigate("profile")
            },
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.Black,
                containerColor = Color.White
            )
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Icon(
                    imageVector = Icons.Rounded.Person,
                    contentDescription = "profile",
                    tint = Color.Black//MaterialTheme.colorScheme.onSecondaryContainer
                )
                Text(text = "Profile")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GymBrosTheme {
        NavigationBar(rememberNavController())
    }
}
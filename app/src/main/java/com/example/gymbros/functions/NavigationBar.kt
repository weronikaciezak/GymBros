package com.example.gymbros.functions

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.Button
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
        modifier = Modifier.fillMaxWidth().height(56.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(onClick = {
            navController.navigate("home")
        }) { Text(text = "Home") }

        Spacer(modifier = Modifier.width(16.dp))

        Button(onClick = {
            navController.navigate("match")
        }) { Text(text = "Match") }

        Spacer(modifier = Modifier.width(16.dp))

        Button(onClick = {
            navController.navigate("profile")
        }
        ) { Text(text = "Profile")
            Icon(
                imageVector = Icons.Rounded.Menu,
                contentDescription = "Home",
                tint = Color.Red//MaterialTheme.colorScheme.onSecondaryContainer
            )}


        /*Box(
            modifier = Modifier
                .height(50.dp)
                .width(60.dp)
                .border(4.dp, Color.Black)
                .clip(RoundedCornerShape(15.dp))
                .background(Color.LightGray)
                .clickable {}
                .padding(6.dp)
        ) {
            Icon(
                imageVector = Icons.Rounded.Menu,
                contentDescription = "Home",
                tint = Color.White//MaterialTheme.colorScheme.onSecondaryContainer
            )
        }*/
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GymBrosTheme {
        NavigationBar(rememberNavController())
    }
}
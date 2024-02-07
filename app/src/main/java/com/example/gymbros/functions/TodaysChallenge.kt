package com.example.gymbros.functions

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.gymbros.Workout
import com.example.gymbros.ui.theme.Mango
import com.example.gymbros.viewModels.DatabaseViewModel

@Composable
fun ChallengeBox(challenge: String, databaseViewModel: DatabaseViewModel) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(10.dp)
            .shadow(5.dp)
            .clip(RoundedCornerShape(18.dp))
            .background(color = Color.White),

        contentAlignment = Alignment.TopStart
    ) {
        if(challenge != "") {
            Column {
                Text(text = "Current challenge",
                    modifier = Modifier.padding(15.dp),
                    style = MaterialTheme.typography.titleMedium)

                if(challenge != "You have no challenges.") {
                    Text(text = "* $challenge", modifier = Modifier.padding(10.dp))
                Row (
                    modifier = Modifier
                        .padding(20.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,

                ) {
                    Text(text = "Skip", modifier = Modifier.clickable {
                        databaseViewModel.fetchNextChallenge()
                        databaseViewModel.deleteChallenge(challenge)
                    })
                    Button(onClick = {
                        databaseViewModel.fetchNextChallenge()
                        val list = mutableListOf<String>()
                        databaseViewModel.currentUser.value.id?.let { list.add(it) }
                        databaseViewModel.registerWorkout(Workout("null", "Challenge", challenge, "", "", list), list)
                        databaseViewModel.deleteChallenge(challenge)
                    },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Mango,
                            contentColor = Color.White
                        )) {
                        Text(text = "Mark as done")
                    }
                }
                } else {
                    Text(text = "  No challenges for now", modifier = Modifier.padding(20.dp))
                }
            }
        } else {
            Text(text = "No challenges for now",
                modifier = Modifier.padding(20.dp),
                style = MaterialTheme.typography.titleMedium)
        }
    }
}

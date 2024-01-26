package com.example.gymbros.functions

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(showBackground = true)
@Composable
fun ChallengeBox() {
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
        Column {
            Text(text = "Today's challenge",
                modifier = Modifier.padding(15.dp),
                style = MaterialTheme.typography.titleMedium)
            Text(text = "* 100 push-ups", modifier = Modifier.padding(10.dp))
        }

    }
}
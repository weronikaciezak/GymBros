package com.example.gymbros

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(showBackground = true)
@Composable
fun FriendsPreview() {
    val users = listOf("user1", "user2", "user3")
    ShowLazyList(users)
}

@Composable
fun ShowLazyList(users: List<String>) {
    LazyColumn {
        items(users) { user ->
            FriendsList(user)
        }
    }
}

@Composable
fun FriendsList(user: String) {
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(10.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.user_default_icon),
            contentDescription = null,
            modifier = Modifier
                .size(60.dp)
                .clip(shape = RoundedCornerShape(30.dp))
        )
        Column (
            modifier = Modifier.padding(10.dp)
        ) {
            Text(text = user, modifier = Modifier.padding(start = 8.dp), fontWeight = FontWeight.Bold)
            Row {
                Text(
                    "bieganie",
                    modifier = Modifier.padding(start = 8.dp),
                    style = MaterialTheme.typography.overline,
                    color = Color.DarkGray
                )
            }
        }
        Row (
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.End
        ){
            Button(onClick = {

            }, modifier = Modifier.padding(end = 10.dp),
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.LightGray,
                    contentColor = Color.Black
                )
            ) {
                Text("Add friend", fontWeight = FontWeight.Light)
            }
        }
    }
}
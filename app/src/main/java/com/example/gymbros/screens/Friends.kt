package com.example.gymbros.screens

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
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.gymbros.R
import com.example.gymbros.User
import com.example.gymbros.functions.NavigationBar
import com.example.gymbros.viewModels.DatabaseViewModel


@Composable
fun Friends(navController: NavController, databaseViewModel: DatabaseViewModel) {
    databaseViewModel.getUserData() //TODO: remove if doesn't work
    val friends = databaseViewModel.listOfFriends
    val friendrequests = databaseViewModel.friendRequests
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
            if (friendrequests.isNotEmpty()) {
                Text(
                    text = "Friend Requests",
                    style = MaterialTheme.typography.subtitle1,
                    modifier = Modifier.padding(20.dp),
                    fontWeight = FontWeight.Bold
                )
                ShowFriendRequests(friendrequests, databaseViewModel)
            }

            Text(
                text = "Friends",
                style = MaterialTheme.typography.subtitle1,
                modifier = Modifier.padding(20.dp),
                fontWeight = FontWeight.Bold
            )
            if (friends.isEmpty()) {
                Text(
                    text = "You have no friends yet",
                    modifier = Modifier.padding(20.dp),
                    fontWeight = FontWeight.Light
                )
            } else {
                ShowFriends(friends)
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun FriendsPreview() {
    val users = mutableListOf(User("1", "user1", "bieganie"), User("2", "user2", "bieganie"))
    ShowFriends(users)
}

@Composable
fun ShowFriendRequests(users: MutableList<User>, databaseViewModel: DatabaseViewModel) {
    LazyColumn {
        items(users) { user ->
            FriendRequestList(user, databaseViewModel)
        }
    }
}

@Composable
fun ShowFriends(users: MutableList<User>) {
    LazyColumn {
        items(users) { user ->
            FriendsList(user)
        }
    }
}

@Composable
fun FriendRequestList(user: User, databaseViewModel: DatabaseViewModel) {
    Row(
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
        Column(
            modifier = Modifier.padding(10.dp)
        ) {
            Text(
                text = user.username!!,
                modifier = Modifier.padding(start = 8.dp),
                fontWeight = FontWeight.Bold
            )
            Row {
                Text(
                    text = user.preference!!,
                    modifier = Modifier.padding(start = 8.dp),
                    style = MaterialTheme.typography.overline,
                    color = Color.DarkGray
                )
            }
        }
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.End
        ) {
            Button(
                onClick = {
                    databaseViewModel.acceptFriendRequest(user)
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

@Composable
fun FriendsList(user: User) {
    Row(
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
        Column(
            modifier = Modifier.padding(10.dp)
        ) {
            Text(
                text = user.username!!,
                modifier = Modifier.padding(start = 8.dp),
                fontWeight = FontWeight.Bold
            )
            Row {
                Text(
                    text = user.preference!!,
                    modifier = Modifier.padding(start = 8.dp),
                    style = MaterialTheme.typography.overline,
                    color = Color.DarkGray
                )
            }
        }
    }
}
package com.example.gymbros.shit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.gymbros.User
import com.example.gymbros.ui.theme.Mango

@Composable
fun Matchdemo(viewModel: CloudViewModel) {
    when (val result = viewModel.response.value) {
        is DataState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is DataState.Success -> {
            ShowLazyList(result.data)
        }

        is DataState.Failure -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = result.message,
                    fontSize = MaterialTheme.typography.h5.fontSize,
                )
            }
        }

        else -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Error Fetching data",
                    fontSize = MaterialTheme.typography.h5.fontSize,
                )
            }
        }
    }
}

@Composable
fun ShowLazyList(users: MutableList<User>) {
    LazyColumn {
        items(users) { user ->
            CardItem(user)
        }
    }
}

@Composable
fun CardItem(user: User) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(10.dp)
    ) {

        Box(modifier = Modifier.fillMaxSize()) {
            /*Image(
                painter = rememberImagePainter(user.Image),
                modifier = Modifier.fillMaxSize(),
                contentDescription = "My content description",
                contentScale = ContentScale.FillWidth
            )*/

            Text(
                text = user.username!!,
                fontSize = MaterialTheme.typography.h5.fontSize,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .background(Mango),
                textAlign = TextAlign.Center,
                color = Color.White
            )
        }

    }
}
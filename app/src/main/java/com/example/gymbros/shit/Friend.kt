package com.example.gymbros.shit
//
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.width
//import androidx.compose.foundation.lazy.LazyRow
//import androidx.compose.foundation.lazy.items
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.Card
//import androidx.compose.material.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.draw.shadow
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.unit.dp
//import com.example.gymbros.R
//import com.example.gymbros.User
//
//
//@Composable
//fun ShowLazyList(users: MutableList<User>) {
//    LazyRow{
//        items(users) { users ->
//            CardItem(users)
//        }
//    }
//}
//
//@Composable
//fun CardItem(user: User) {
//    Column (
//        modifier = Modifier.padding(10.dp),
//        horizontalAlignment = Alignment.CenterHorizontally
//    ){
//        Card(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(80.dp)
//                .width(80.dp)
//                .shadow(5.dp)
//                .clip(RoundedCornerShape(18.dp))
//        ) {
//            Box(
//                modifier = Modifier.fillMaxSize(),
//                contentAlignment = Alignment.Center
//            ) {
//                Image(
//                    painter = painterResource(id = R.drawable.user_default_icon),
//                    contentDescription = null
//                )
//            }
//        }
//        user.username?.let {
//            Text(
//                text = it,
//                color = Color.Black
//            )
//        }
//    }
//}

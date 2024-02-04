package com.example.gymbros.functions

import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Preview(showBackground = true)
@Composable
fun Chart() {
//    Card(
//        //elevation = 4.dp,
//        //backgroundColor = Color.White,
//        shape = RoundedCornerShape(15.dp),
//        border = BorderStroke(1.dp, Color.Black),
//        modifier = Modifier.padding(8.dp).fillMaxWidth()
//    ) {
//
//        Row (
//            modifier = Modifier
//                .padding(20.dp)
//                .width(300.dp)
//                .height(50.dp),
//            horizontalArrangement = Arrangement.SpaceBetween,
//            verticalAlignment = Alignment.CenterVertically
//        ){
//            Box(
//
//                modifier = Modifier
//                    .clip(RoundedCornerShape(15.dp))
//                    //.background(MaterialTheme.colorScheme.secondaryContainer)
//                    .background(Mango)
//                    .clickable {
//
//                    }
//                    .padding(6.dp),
//            ) {
//                Icon(
//                    imageVector = Icons.Rounded.Add,
//                    contentDescription = "Search",
//                    tint = Color.White//MaterialTheme.colorScheme.onSecondaryContainer
//                )
//            }
//            Spacer(modifier = Modifier.width(12.dp))
//            Text(text = "register workout",
//                style = MaterialTheme.typography.titleMedium,
//                color = Mango,
//                fontSize = 20.sp,
//                fontFamily = MaterialTheme.typography.labelSmall.fontFamily
//            )
//        }
//    }
    Button(
        onClick = { },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.White,
            contentColor = Color.Black
        )
    ) {
        Text(text = "register workout")
    }
}

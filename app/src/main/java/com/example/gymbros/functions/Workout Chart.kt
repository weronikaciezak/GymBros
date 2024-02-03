package com.example.gymbros.functions

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gymbros.ui.theme.Mango

@Preview(showBackground = true)
@Composable
fun Chart() {
    Card(
        //elevation = 4.dp,
        //backgroundColor = Color.White,
        shape = RoundedCornerShape(15.dp),
        border = BorderStroke(1.dp, Color.Black),
        modifier = Modifier.padding(8.dp).fillMaxWidth()
    ) {

        Row (
            modifier = Modifier
                .padding(20.dp)
                .width(300.dp)
                .height(50.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            Box(

                modifier = Modifier
                    .clip(RoundedCornerShape(15.dp))
                    //.background(MaterialTheme.colorScheme.secondaryContainer)
                    .background(Mango)
                    .clickable {

                    }
                    .padding(6.dp),
            ) {
                Icon(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = "Search",
                    tint = Color.White//MaterialTheme.colorScheme.onSecondaryContainer
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Text(text = "register workout",
                style = MaterialTheme.typography.titleMedium,
                color = Mango,
                fontSize = 20.sp,
                fontFamily = MaterialTheme.typography.labelSmall.fontFamily
            )
        }
    }
}

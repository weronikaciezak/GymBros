package com.example.gymbros.functions

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(showBackground = true)
@Composable
fun Schedule() {
    Row (
        modifier = Modifier
            .padding(20.dp)
            .width(300.dp)
            .height(50.dp),
        //horizontalArrangement = Arrangement.SpaceBetween,
        //verticalAlignment = Alignment.CenterVertically
    ){
        WeekList()
    }
}

@Composable
fun WeekList() {
    val days = listOf("M", "T", "W", "T", "F", "S", "S")
    LazyRow {
        items(days) { index ->
            ShowDays(index)
        }
    }
}

@SuppressLint("UnrememberedMutableState")
@Composable
fun ShowDays(day: String) {
    var clicked = true
    Box(modifier = Modifier
        .padding(6.dp)
        .background(if (clicked) Color.White else Color.LightGray)
        .clickable {
            clicked = !clicked
        },
    ) {
        Text(text = day)
    }
}
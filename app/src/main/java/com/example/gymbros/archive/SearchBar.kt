package com.example.gymbros.archive

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier

@Composable
fun SearchBar(viewModel: SearchViewModel) {
    val searchText by viewModel.searchText.collectAsState()

    TextField(
        value = searchText,
        onValueChange = viewModel::onSearchTextChange,
        label = { Text("Search for users") },
        modifier = Modifier.fillMaxWidth()
    )
}
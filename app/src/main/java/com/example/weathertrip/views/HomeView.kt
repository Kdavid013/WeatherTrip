package com.example.weathertrip.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.weathertrip.viewmodels.HomeViewViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun HomeView(
    viewModel: HomeViewViewModel = viewModel(),
    modifier: Modifier = Modifier
){
    Row() {
        Text("Home")
    }
}

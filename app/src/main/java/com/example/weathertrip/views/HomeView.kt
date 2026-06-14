package com.example.weathertrip.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePicker
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import com.example.weathertrip.viewmodels.HomeViewViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weathertrip.apptheme.*
import com.example.weathertrip.ui.elements.InputField


@Composable
fun HomeView(
    viewModel: HomeViewViewModel = viewModel(),
    modifier: Modifier = Modifier
){


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(colors = gradientColors))
            .padding(30.dp)
    )
    {
        InputField("Start point", "From")
        Spacer(modifier = Modifier.height(30.dp))
        InputField("Destination", "To")
        Text("Date:")
        DatePicker(
            state = rememberDatePickerState(),
        )
    }

}

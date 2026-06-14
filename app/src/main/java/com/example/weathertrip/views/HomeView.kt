package com.example.weathertrip.views

import android.graphics.fonts.Font
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.DatePicker
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weathertrip.viewmodels.HomeViewViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weathertrip.R
import com.example.weathertrip.apptheme.*
import com.example.weathertrip.ui.elements.InputField


@Composable
fun HomeView(
    viewModel: HomeViewViewModel = viewModel(),
    modifier: Modifier = Modifier
){
    val listColor = listOf(gradientTop, gradientBottom)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(colors = listColor))
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

package com.example.weathertrip.views

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weathertrip.viewmodels.HomeViewViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weathertrip.apptheme.*
import com.example.weathertrip.ui.elements.InputField


@Composable
fun HomeView(
    viewModel: HomeViewViewModel = viewModel(),
    modifier: Modifier = Modifier
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(colors = gradientColors))
            .padding(30.dp),
        verticalArrangement = Arrangement.SpaceBetween
    )
    {
        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            InputField("Start point", "From")
            InputField("Destination", "To")
            InputField("Date", "Pick a date")
//        DatePicker(
//            state = rememberDatePickerState(),
//        )
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = { /* TODO: on click */ },
                shape = CircleShape,
                modifier = Modifier
                    .size(100.dp),
                border = BorderStroke(1.dp, fontColor),
                colors = ButtonDefaults.buttonColors(
                    containerColor = fieldColor
                ),

                ) {
                Text(
                    "GO",
                    fontFamily = interFontFamily,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 32.sp,
                    color = fontColor
                )
            }
        }
    }
}

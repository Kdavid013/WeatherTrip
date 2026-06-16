package com.example.weathertrip.views

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weathertrip.apptheme.fieldColor
import com.example.weathertrip.apptheme.fontColor
import com.example.weathertrip.apptheme.gradientColors
import com.example.weathertrip.apptheme.interFontFamily

@Composable
fun SettingsView(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(colors = gradientColors))
            .padding(30.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text(
                text = "Language",
                fontFamily = interFontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp,
                color = fontColor
            )
            Button(
                onClick = { /* TODO: Handle click */ },
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .height(50.dp)
                    .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = fieldColor
                    ),
                    border = BorderStroke(2.dp,fontColor)
            ) {
                Text("English",
                    fontFamily = interFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 24.sp
                )
            }
            Button(
                onClick = { /* TODO: Handle click */ },
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .height(50.dp)
                    .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                    containerColor = fieldColor
                ),
                border = BorderStroke(2.dp,fontColor)
            ) {
                Text("Magyar",
                    fontFamily = interFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 24.sp
                )
            }
            Button(
                onClick = { /* TODO: Handle click */ },
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .height(50.dp)
                    .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                    containerColor = fieldColor
                ),
                border = BorderStroke(2.dp,fontColor)
            ) {
                Text("Deutsch",
                    fontFamily = interFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 24.sp
                    )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text("Version:",
                fontFamily = interFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 20.sp,
                color = fontColor
                )
            Text("0.0.2",
                fontFamily = interFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 20.sp,
                color = fontColor
            )
            Text("Created by",
                fontFamily = interFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 20.sp,
                color = fontColor
            )
            Text("David Karacs",
                fontFamily = interFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 20.sp,
                color = fontColor
            )
        }
    }
}
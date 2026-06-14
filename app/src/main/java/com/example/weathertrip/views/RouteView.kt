package com.example.weathertrip.views

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.weathertrip.apptheme.cardColor
import com.example.weathertrip.apptheme.gradientColors

@Composable
fun RouteView(
    modifier: Modifier = Modifier
) {
    val innerCircleRadius = 10.dp
    val outerCircleRadius = 12.dp

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(colors = gradientColors))
            .padding(30.dp)
    ) {
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            ) {
                Card(
                    colors = CardDefaults.cardColors(containerColor = cardColor),
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier
                        .height(150.dp)
                        .fillMaxWidth()
                        .drawBehind {
                            drawCircle(
                                radius = outerCircleRadius.toPx(),
                                center = Offset(0f, size.height / 2),
                                color = cardColor
                            )
                        }

                ) {

                    Column(
                        modifier = Modifier
                            .padding(16.dp, 8.dp, 8.dp, 8.dp)
                            .fillMaxSize()
                    ) {
                        Text(text = "kártya")

                    }


                }
                Canvas(
                    modifier = Modifier
                        .offset {
                            IntOffset(
                                x = -innerCircleRadius.roundToPx(), // Eltolás balra
                                y = (150.dp / 2).roundToPx() - innerCircleRadius.roundToPx() // Eltolás lefelé (kártya közepe - kör sugara)
                            )
                        }// Bal szélen, középen
                        .size(innerCircleRadius * 2) // A kör mérete
                ) {
                    drawCircle(
                        color = Color.Black,
                        radius = innerCircleRadius.toPx()
                    )
                }
            }
        }


    }
}
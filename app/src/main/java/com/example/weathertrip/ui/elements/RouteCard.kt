package com.example.weathertrip.ui.elements

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weathertrip.R
import com.example.weathertrip.apptheme.cardColor
import com.example.weathertrip.apptheme.fontColor
import com.example.weathertrip.apptheme.interFontFamily
import com.example.weathertrip.apptheme.lineColor

@Composable
fun RouteCard (
    cityName : String,
    currentTemp: String,
    expectedTemp: String,
    expectedTime: String = "15:00"
) {



    val innerCircleRadius = 10.dp
    val outerCircleRadius = 12.dp

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
    ) {
        Card(
            colors = CardDefaults.cardColors(containerColor = cardColor),
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
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
                    .padding(16.dp, 8.dp, 16.dp, 8.dp)
                    .fillMaxSize()
            ) {
                Text(
                    text = cityName,
                    fontFamily = interFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    color = fontColor
                )
                Spacer(modifier = Modifier.height(12.dp))
                HorizontalDivider(
                    thickness = 1.dp,
                    color = fontColor
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
//                            verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(8.dp)
                    ) {
                        Text(
                            "Now:",
                            fontFamily = interFontFamily,
                            fontWeight = FontWeight.Normal,
                            fontSize = 12.sp,
                            color = fontColor
                        )
                        Spacer(Modifier.height(4.dp))
                        Row() {
                            Icon(
                                painter = painterResource(R.drawable.sunny),
                                contentDescription = "icon",
                                tint = Color.Unspecified,
                                modifier = Modifier
                                    .size(35.dp)
                            )
                            Text(
                                text = currentTemp,
                                fontFamily = interFontFamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 36.sp,
                                color = fontColor
                            )
                        }
                    }
                    VerticalDivider(
                        thickness = 1.dp,
                        color = fontColor
                    )
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(8.dp)
                    ) {
                        Text(
                            "Expected:",
                            fontFamily = interFontFamily,
                            fontWeight = FontWeight.Normal,
                            fontSize = 12.sp,
                            color = fontColor
                        )
                        Spacer(Modifier.height(4.dp))
                        Row() {
                            Icon(
                                painter = painterResource(R.drawable.rain),
                                contentDescription = "icon",
                                tint = Color.Unspecified,
                                modifier = Modifier
                                    .size(35.dp)
                            )
                            Text(
                                text = expectedTemp,
                                fontFamily = interFontFamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 36.sp,
                                color = fontColor
                            )
                        }
                        Spacer(Modifier.height(10.dp))
                        Text(
                            "At $expectedTime",
                            fontFamily = interFontFamily,
                            fontWeight = FontWeight.Normal,
                            fontSize = 12.sp,
                            color = fontColor
                        )
                    }
                }
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
                color = lineColor,
                radius = innerCircleRadius.toPx()
            )

        }
    }
}
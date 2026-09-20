package com.example.weathertrip.ui.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weathertrip.models.TravelMode

@Composable
fun TravelModeSelector(
    selectedMode: TravelMode, // Feltételeztem, hogy String, de cseréld át a saját Enumodra, ha azt használsz!
    onModeSelected: (TravelMode) -> Unit,
    modifier: Modifier = Modifier
) {
    val modes = TravelMode.entries

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp)
            // Közös külső fehér keret, teljesen lekerekített sarkokkal
            .border(1.dp, Color.White, RoundedCornerShape(50))
            .clip(RoundedCornerShape(50)), // Gondoskodik róla, hogy a benne lévő hátterek ne lógjanak ki a sarkoknál
        verticalAlignment = Alignment.CenterVertically
    ) {
        modes.forEachIndexed { index, mode ->
            val isSelected = selectedMode == mode

            Box(
                modifier = Modifier
                    .weight(1f) // Mindegyik gomb egyenlő szélességet kap
                    .fillMaxHeight()
                    .clickable { onModeSelected(mode) }
                    .then(
                        if (isSelected) {
                            // "Benyomott" (Concave) hatás szimulálása
                            Modifier
                                .background(
                                    Brush.verticalGradient(
                                        colors = listOf(
                                            Color.Black.copy(alpha = 0.4f), // Erősebb sötét árnyék felül
                                            Color.Transparent // Lefelé halványul
                                        ),
                                        startY = 0f,
                                        endY = 50f
                                    )
                                )
                                .background(Color.Black.copy(alpha = 0.15f)) // Az egész kicsit sötétebb lesz
                        } else {
                            // "Kidomborodó" alapállapot
                            // Használhatunk egy nagyon enyhe fehér gradienst a tetején, ha még domborúbb hatást szeretnél:
                            Modifier.background(Color.Transparent)
                        }
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = mode.name,
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
                )
            }

            // Függőleges elválasztó vonalak a gombok között
            if (index < modes.size - 1) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(1.dp)
                        .background(Color.White)
                )
            }
        }
    }
}
package com.example.weathertrip.ui.elements

import android.graphics.BlurMaskFilter
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun Modifier.innerShadow(
    color: Color = Color.Black.copy(alpha = 0.4f), // Erősebb sötétítés
    blur: Dp = 6.dp,
    offsetX: Dp = 2.dp,
    offsetY: Dp = 2.dp
) = this.drawWithContent {
    drawContent()
    drawIntoCanvas { canvas ->
        val paint = Paint().apply {
            this.color = color
            asFrameworkPaint().apply {
                maskFilter = BlurMaskFilter(blur.toPx(), BlurMaskFilter.Blur.NORMAL)
            }
        }
        // Rajzolunk egy elmosott réteget, ami eltolva sötétíti a széleket
        canvas.drawRect(
            left = offsetX.toPx(),
            top = offsetY.toPx(),
            right = size.width + offsetX.toPx(), // Kicsit kilógatjuk, hogy csak bent legyen árnyék
            bottom = size.height + offsetY.toPx(),
            paint = paint
        )
    }
}
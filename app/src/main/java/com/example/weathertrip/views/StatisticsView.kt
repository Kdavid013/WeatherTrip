package com.example.weathertrip.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import co.yml.charts.axis.AxisData
import co.yml.charts.common.model.Point
import com.example.weathertrip.R
import co.yml.charts.ui.barchart.models.BarData
import co.yml.charts.ui.barchart.models.BarPlotData
import co.yml.charts.ui.barchart.models.BarStyle
import co.yml.charts.ui.barchart.models.GroupBar
import co.yml.charts.ui.combinedchart.CombinedChart
import co.yml.charts.ui.combinedchart.model.CombinedChartData
import co.yml.charts.ui.linechart.model.IntersectionPoint
import co.yml.charts.ui.linechart.model.Line
import co.yml.charts.ui.linechart.model.LinePlotData
import co.yml.charts.ui.linechart.model.LineStyle
import co.yml.charts.ui.linechart.model.SelectionHighlightPoint
import co.yml.charts.ui.linechart.model.SelectionHighlightPopUp
import com.example.weathertrip.apptheme.accentColor
import com.example.weathertrip.apptheme.cardColor
import com.example.weathertrip.apptheme.fieldColor
import com.example.weathertrip.apptheme.fontColor
import com.example.weathertrip.apptheme.gradientColors
import com.example.weathertrip.apptheme.interFontFamily
import com.example.weathertrip.viewmodels.BleViewModel
import com.example.weathertrip.viewmodels.RouteViewViewModel
import java.util.Locale
import kotlin.math.max

@Composable
fun StatisticsView(
    modifier: Modifier = Modifier,
    routeViewModel: RouteViewViewModel = viewModel(),
    bleViewModel: BleViewModel? = null
) {
    val routeCities by routeViewModel.routeCities.collectAsState()
    val bleHistory = bleViewModel?.temperatureHistory ?: emptyList()

    // --- 1. ADATOK ELŐKÉSZÍTÉSE ---
    val hasRoute = routeCities.isNotEmpty()
    val stopNames = if (hasRoute) {
        routeCities.map { it.cityName }
    } else {
        listOf("Indulás", "Megálló 1", "Megálló 2", "Célállomás")
    }

    val expectedTemps = if (hasRoute) {
        routeCities.map { parseTemp(it.expectedTemp) }
    } else {
        listOf(20f, 24f, 23f, 21f)
    }

    val measuredTemps = if (bleHistory.isNotEmpty()) {
        bleHistory.toList()
    } else {
        listOf(19.5f, 23.0f, 23.8f, 21.2f)
    }

    val listSize = max(stopNames.size, measuredTemps.size)

    // --- 2. DIAGRAM MODELLEK ---

    // Oszlopdiagram (Várható értékek)
    val groupBarList = (0 until listSize).map { index ->
        val temp = expectedTemps.getOrElse(index) { expectedTemps.lastOrNull() ?: 20f }
        val label = stopNames.getOrElse(index) { "M${index + 1}" }
        GroupBar(
            label = label,
            barList = listOf(BarData(point = Point(index.toFloat(), temp), label = "Várható"))
        )
    }

    val barPlotData = BarPlotData(
        groupBarList = groupBarList,
        barStyle = BarStyle(barWidth = 24.dp),
        barColorPaletteList = listOf(fieldColor)
    )

    // Vonaldiagram (Mért értékek)
    val linePoints = measuredTemps.take(listSize).mapIndexed { index, temp ->
        Point(index.toFloat(), temp)
    }

    val linePlotData = LinePlotData(
        lines = listOf(
            Line(
                dataPoints = linePoints,
                lineStyle = LineStyle(color = accentColor),
                intersectionPoint = IntersectionPoint(color = accentColor),
                selectionHighlightPoint = SelectionHighlightPoint(color = accentColor),
                selectionHighlightPopUp = SelectionHighlightPopUp()
            )
        )
    )

    // Tengelyek kalkulációja
    val allTemps = expectedTemps + measuredTemps
    val minTemp = (allTemps.minOrNull() ?: 0f) - 2f
    val maxTemp = (allTemps.maxOrNull() ?: 30f) + 2f
    val ySteps = 5
    val tempStep = (maxTemp - minTemp) / ySteps

    val xAxisData = AxisData.Builder()
        .axisStepSize(100.dp) // Oszlopok közötti távolság
        .steps(listSize - 1)
        .bottomPadding(60.dp) // Hely a megdöntött szövegnek
        .axisLabelAngle(45f) // Szöveg megdöntése 45 fokban
        .axisLineColor(fontColor)
        .axisLabelColor(fontColor)
        .backgroundColor(Color.Transparent) // Átlátszó háttér
        .labelData { index -> stopNames.getOrElse(index) { "" } }
        .build()

    val yAxisData = AxisData.Builder()
        .steps(ySteps)
        .labelAndAxisLinePadding(12.dp)
        .axisOffset(10.dp)
        .axisLineColor(fontColor)
        .axisLabelColor(fontColor)
        .backgroundColor(Color.Transparent) // Átlátszó háttér
        .labelData { index -> String.format(Locale.US, "%.0f°C", minTemp + (index * tempStep)) }
        .build()

    val combinedChartData = CombinedChartData(
        combinedPlotDataList = listOf(barPlotData, linePlotData),
        xAxisData = xAxisData,
        yAxisData = yAxisData,
        backgroundColor = Color.Transparent // Átlátszó háttér
    )

    // --- 3. UI FELÉPÍTÉS ---
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(colors = gradientColors))
            .verticalScroll(rememberScrollState())
            .padding(24.dp)
    ) {
        Text(
            text = stringResource(R.string.statistics_title),
            fontFamily = interFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            color = fontColor
        )
        Text(
            text = stringResource(R.string.statistics_subtitle),
            fontFamily = interFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            color = fontColor.copy(alpha = 0.8f)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Jelmagyarázat (Legend)
        Card(
            colors = CardDefaults.cardColors(containerColor = cardColor),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.size(16.dp).background(fieldColor, shape = RoundedCornerShape(4.dp)))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(stringResource(R.string.legend_expected), fontFamily = interFontFamily, fontSize = 12.sp, color = fontColor)
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.size(16.dp, 4.dp).background(accentColor))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(stringResource(R.string.legend_measured), fontFamily = interFontFamily, fontSize = 12.sp, color = fontColor)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Diagram Kártya
        Card(
            colors = CardDefaults.cardColors(containerColor = cardColor),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(350.dp)
        ) {
            // MaterialTheme felülírása, hogy a YCharts rejtett rétegei ne fehérek legyenek
            MaterialTheme(
                colorScheme = MaterialTheme.colorScheme.copy(
                    surface = cardColor,
                    background = cardColor
                )
            ) {
                CombinedChart(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Transparent)
                        // Itt adunk margót a diagramnak, hogy a balra lógó szövegek ne vágódjanak le
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    combinedChartData = combinedChartData
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Összegző kártya
        Card(
            colors = CardDefaults.cardColors(containerColor = cardColor),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = stringResource(R.string.overview_title),
                    fontFamily = interFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    color = fontColor
                )
                Spacer(modifier = Modifier.height(8.dp))
                HorizontalDivider(color = fontColor.copy(alpha = 0.3f))
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(stringResource(R.string.avg_expected), fontFamily = interFontFamily, fontSize = 12.sp, color = fontColor.copy(alpha = 0.8f))
                        val avgExpected = if (expectedTemps.isNotEmpty()) expectedTemps.average() else 0.0
                        Text(
                            text = String.format(Locale.US, "%.1f °C", avgExpected),
                            fontFamily = interFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = fontColor
                        )
                    }

                    Column {
                        Text(stringResource(R.string.last_measured), fontFamily = interFontFamily, fontSize = 12.sp, color = fontColor.copy(alpha = 0.8f))
                        val lastMeasured = bleViewModel?.temperature ?: "--"
                        Text(
                            text = if (lastMeasured != "--") "$lastMeasured °C" else String.format(Locale.US, "%.1f °C", measuredTemps.lastOrNull() ?: 0f),
                            fontFamily = interFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = accentColor
                        )
                    }
                }
            }
        }
    }
}

private fun parseTemp(tempStr: String): Float {
    val clean = tempStr.replace("°C", "").replace("°", "").trim()
    return clean.toFloatOrNull() ?: 20f
}
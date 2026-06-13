package com.example.weathertrip.composeui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Shapes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.weathertrip.navbar.NavItem
import kotlinx.coroutines.selects.select

@Composable
fun navBar(navItemList: List<NavItem>) {

    var selectedIndex by remember{
        mutableStateOf(0)
    }

    val customShapes = MaterialTheme.shapes.copy(
        extraLarge = CircleShape
    )

    MaterialTheme(shapes = customShapes){
        NavigationBar(
            containerColor = Color(0xff0387CE),
            modifier = Modifier
                .drawBehind {
                    val borderSize = 4.dp.toPx()
                    drawLine(
                        color = Color(0xffFFB300),
                        start = Offset(0f, 0f),
                        end = Offset(size.width, 0f),
                        strokeWidth = borderSize
                    )
                }
                .height(125.dp)
        ) {
            navItemList.forEachIndexed { index, item ->
                NavigationBarItem(
                    icon = {
                    Icon(item.icon,
                        contentDescription = item.label,
                        modifier = Modifier
                        .size(30.dp))
                },
                    onClick = {
                        selectedIndex = index

                    },
                    selected = index == selectedIndex,
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.Black,
                        unselectedIconColor = Color(0xfff3f3f3),
                        indicatorColor = Color(0xffFFB300)
                    )
                )
            }
        }
    }

}


package com.example.weathertrip.composeui

import android.R.attr.top
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavKey
import com.example.weathertrip.navigation.TOP_LEVEL_DESTINATIONS

@Composable
fun NavBar(
    selectedKey: NavKey,
    onSelectKey: (NavKey) -> Unit,
    modifier: Modifier = Modifier
    ) {
    BottomAppBar(
        modifier = modifier
            .background(Color(0xffFFB300))
            .padding(top = 3.dp),
        containerColor = Color(0xff0387CE)
    ) {
        TOP_LEVEL_DESTINATIONS.forEach { (topLevelDestination, data) ->
            NavigationBarItem(
                selected = topLevelDestination == selectedKey,
                onClick = {
                    onSelectKey(topLevelDestination)
                },
                icon = {
                    Icon(
                        painter = painterResource(id = data.icon),
                        contentDescription = data.label
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.Black,
                    unselectedIconColor = Color(0xfff3f3f3),
                    indicatorColor = Color(0xffFFB300)
                )
            )
        }
    }



/*
    var selectedIndex by remember {
        mutableStateOf(0)
    }
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

            val isSelected = index == selectedIndex

            NavigationBarItem(
                icon = {
                    Icon(
                        item.icon,
                        contentDescription = item.label,
                        modifier = Modifier
                            .size(30.dp)
                            .drawBehind {
                                if (isSelected) {
                                    drawCircle(
                                        color = Color(0xffFFB300),
                                        radius = size.maxDimension * 0.8f
                                    )
                                }
                            }
                    )
                },
                onClick = {
                    selectedIndex = index
                },
                selected = isSelected,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.Black,
                    unselectedIconColor = Color(0xfff3f3f3),
                    indicatorColor = Color.Transparent
                )
            )
        }
    }*/
}


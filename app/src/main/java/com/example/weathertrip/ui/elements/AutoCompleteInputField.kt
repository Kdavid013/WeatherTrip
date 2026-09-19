package com.example.weathertrip.ui.elements

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.weathertrip.R
import com.example.weathertrip.apptheme.fieldColor
import com.example.weathertrip.apptheme.fontColor
import com.example.weathertrip.apptheme.interFontFamily
import com.example.weathertrip.responses.SuggestionItem


@Composable
fun AutoCompleteInputField(
    label: String,
    placeholder: String,
    value: String,
    suggestions: List<SuggestionItem>,
    onValueChange: (String) -> Unit,
    onSuggestionSelected: (SuggestionItem) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    var displayedSuggestions by remember { mutableStateOf(emptyList<SuggestionItem>()) }

    LaunchedEffect(suggestions) {
        if (suggestions.isNotEmpty()) {
            displayedSuggestions = suggestions
            expanded = true
        } else {
            expanded = false
        }
    }

    Column(modifier = modifier
        .fillMaxWidth()
        .zIndex(if (expanded) 1f else 0f)) {

        Text(
            text = label,
            fontSize = 20.sp,
            fontFamily = interFontFamily,
            color = fontColor,
            modifier = Modifier.padding(bottom = 6.dp)
        )

        Column(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = value,
                onValueChange = onValueChange,
                placeholder = { Text(placeholder) },
                singleLine = true,
                shape = if (expanded) RoundedCornerShape(
                    topStart = 30.dp,
                    topEnd = 30.dp,
                    bottomStart = 0.dp,
                    bottomEnd = 0.dp
                ) else RoundedCornerShape(30.dp),
                colors = TextFieldDefaults.colors(
                    focusedTextColor = fontColor,
                    unfocusedTextColor = fontColor,
                    focusedContainerColor = fieldColor,
                    unfocusedContainerColor = fieldColor,
                    focusedIndicatorColor = androidx.compose.ui.graphics.Color.Transparent,
                    unfocusedIndicatorColor = androidx.compose.ui.graphics.Color.Transparent,

                    ),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(width = 2.dp,
                        color = fontColor,
                        shape = if (expanded) RoundedCornerShape(
                            topStart = 30.dp,
                            topEnd = 30.dp,
                            bottomStart = 0.dp,
                            bottomEnd = 0.dp
                        ) else RoundedCornerShape(30.dp)
                    ),
                trailingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.chevron_down),
                        contentDescription = "Chevron down",
                        tint = fontColor,
                        modifier = Modifier
                            .size(20.dp)
                    )
                }
            )

            // A Box eltűnt! A layout modifiert közvetlenül az animáció kapja
            AnimatedVisibility(
                visible = expanded,
                enter = expandVertically(),
                exit = shrinkVertically(),
                modifier = Modifier
            ) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .heightIn(max = 220.dp)
                            .background(color = fieldColor)
                    ) {
                        items(suggestions) { item ->
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        onSuggestionSelected(item)
                                        expanded = false
                                    }
                                    .padding(horizontal = 16.dp, vertical = 12.dp)
                            ) {
                                Text(
                                    text = item.name,
                                    fontFamily = interFontFamily,
                                    fontWeight = FontWeight.Bold,
                                    color = fontColor
                                )
                                item.place_formatted?.let {
                                    Text(
                                        text = it,
                                        fontSize = 12.sp,
                                        color = fontColor
                                    )
                                }
                            }
                            HorizontalDivider(color = Color.LightGray.copy(alpha = 0.5f))
                        }
                    }
                }
            }
        }
    }
}
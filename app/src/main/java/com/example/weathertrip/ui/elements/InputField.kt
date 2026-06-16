package com.example.weathertrip.ui.elements

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weathertrip.R
import com.example.weathertrip.apptheme.fieldColor
import com.example.weathertrip.apptheme.fontColor
import com.example.weathertrip.apptheme.interFontFamily

@Composable
fun InputField(
    label: String,
    fieldText: String,
    modifier: Modifier = Modifier
){
    Text(label,
        modifier = Modifier,
        fontSize = 30.sp,
        color = fontColor,
        fontFamily = interFontFamily,
        fontWeight = FontWeight.Normal
    )
    TextField(
        state = rememberTextFieldState(initialText = ""),
        placeholder = {Text(fieldText)},
        shape = RoundedCornerShape(50),
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
                shape = RoundedCornerShape(50)),
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
}
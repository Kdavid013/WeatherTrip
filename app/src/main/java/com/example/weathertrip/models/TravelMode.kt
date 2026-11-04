package com.example.weathertrip.models

import androidx.annotation.StringRes
import com.example.weathertrip.R

enum class TravelMode(
    @StringRes val nameRes: Int,
    val displayName: String,
    val apiProfile: String
) {
    DRIVING(R.string.mode_driving, "Driving", "driving"),
    CYCLING(R.string.mode_cycling, "Cycling", "cycling"),
    WALKING(R.string.mode_walking, "Walking", "walking")
}

package com.example.weathertrip.models

enum class TravelMode(val displayName: String, val apiProfile: String) {
    DRIVING("Driving", "driving"),
    CYCLING("Cycling", "cycling"),
    WALKING("Walking", "walking")
}
package com.example.weathertrip.responses

data class MapboxSuggestResponse(
    val suggestions: List<SuggestionItem>
)

data class SuggestionItem(
    val name: String,
    val place_formatted: String?,
    val mapbox_id: String
)
package com.example.weathertrip.responses

data class MapboxSuggestResponse(
    val suggestions: List<SuggestionItemDTO>
)

data class SuggestionItemDTO(
    val name: String,
    val place_formatted: String?,
    val mapbox_id: String
)


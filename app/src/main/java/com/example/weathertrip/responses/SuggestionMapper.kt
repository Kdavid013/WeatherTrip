package com.example.weathertrip.responses

import com.example.weathertrip.models.SuggestionItemModel

fun SuggestionItemDTO.toDomainModel(): SuggestionItemModel {
    return SuggestionItemModel(
        id = this.mapbox_id,
        title = this.name,
        subtitle = this.place_formatted
    )
}
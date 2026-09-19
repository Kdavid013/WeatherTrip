package com.example.weathertrip.network

import com.example.weathertrip.responses.MapboxSuggestResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface DirectionsApiService {
    @GET("search/searchbox/v1/suggest")
    suspend fun getSuggestions(
        @Query("q") query: String,
        @Query("access_token") accessToken: String,
        @Query("session_token") sessionToken: String,
        @Query("language") language: String = "hu"
    ): MapboxSuggestResponse
}
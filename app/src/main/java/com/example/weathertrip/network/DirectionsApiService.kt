package com.example.weathertrip.network

import com.example.weathertrip.BuildConfig
import com.example.weathertrip.responses.MapboxRetrieveResponse
import com.example.weathertrip.responses.MapboxRouteResponse
import com.example.weathertrip.responses.MapboxSuggestResponse
import com.example.weathertrip.responses.ReverseGeoResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DirectionsApiService {
    @GET("search/searchbox/v1/suggest")
    suspend fun getSuggestions(
        @Query("q") query: String,
        @Query("access_token") accessToken: String = BuildConfig.API_TOKEN,
        @Query("session_token") sessionToken: String,
        @Query("language") language: String = "hu"
    ): MapboxSuggestResponse

    @GET("search/searchbox/v1/retrieve/{id}")
    suspend fun getRetrieve(
        @Path("id") id: String,
        @Query("access_token") accessToken: String = BuildConfig.API_TOKEN,
        @Query("session_token") sessionToken: String,
        @Query("language") language: String = "hu"
    ): MapboxRetrieveResponse

    @GET("directions/v5/{profile}/{coordinates}")
    suspend fun getRoute(
        @Path("profile", encoded = true) profile: String,
        @Path("coordinates") coordinates: String,
        @Query("geometries") geometries: String = "geojson",
        @Query("access_token") accessToken: String = BuildConfig.API_TOKEN,
        @Query("language") language: String = "hu"
    ): MapboxRouteResponse

    @GET("search/geocode/v6/reverse")
    suspend fun getCityName(
        @Query("longitude") longitude: Double,
        @Query("latitude") latitude: Double,
        @Query("access_token") accessToken: String = BuildConfig.API_TOKEN,
        @Query("language") language: String = "hu",
        @Query("types") types: String = "place"
    ): ReverseGeoResponse

}
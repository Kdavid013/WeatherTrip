package com.example.weathertrip.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weathertrip.BuildConfig
import com.example.weathertrip.responses.SuggestionItem
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.UUID

// Modell a Mapbox találathoz
@OptIn(FlowPreview::class)
class HomeViewViewModel : ViewModel() {
    private val mapboxToken = BuildConfig.API_TOKEN
    private val sessionToken = UUID.randomUUID().toString()

    // Kezdőpont állapotai
    val startQuery = MutableStateFlow("")
    private val _startSuggestions = MutableStateFlow<List<SuggestionItem>>(emptyList())
    val startSuggestions: StateFlow<List<SuggestionItem>> = _startSuggestions.asStateFlow()

    private var lastSelectedStart = ""

    // Célállomás állapotai
    val destinationQuery = MutableStateFlow("")
    private val _destinationSuggestions = MutableStateFlow<List<SuggestionItem>>(emptyList())
    val destinationSuggestions: StateFlow<List<SuggestionItem>> = _destinationSuggestions.asStateFlow()

    private var lastSelectedDestination = ""

    init {
        // Kezdőpont figyelése debounce-szal
        viewModelScope.launch {
            startQuery
                .debounce(600L)
                .filter { it.trim().length >= 3 && it != lastSelectedStart}
                .distinctUntilChanged()
                .collectLatest { query ->
                    _startSuggestions.value = fetchSuggestions(query)
                }


        }

        // Célállomás figyelése debounce-szal
        viewModelScope.launch {
            destinationQuery
                .debounce(600L)
                .filter { it.trim().length >= 3 && it != lastSelectedDestination }
                .distinctUntilChanged()
                .collectLatest { query ->
                    _destinationSuggestions.value = fetchSuggestions(query)
                }
        }
    }

    fun onStartQueryChange(newText: String) {
        Log.d("MapboxDebug", "onStartQueryChange hívva: $newText")
        startQuery.value = newText
        if (newText.isBlank()) _startSuggestions.value = emptyList()
    }

    fun onDestinationQueryChange(newText: String) {
        destinationQuery.value = newText
        if (newText.isBlank()) _destinationSuggestions.value = emptyList()

    }

    fun selectStartLocation(item: SuggestionItem) {
        startQuery.value = item.name
        lastSelectedStart = item.name
        _startSuggestions.value = emptyList() // lista elrejtése kiválasztás után
    }

    fun selectDestinationLocation(item: SuggestionItem) {
        destinationQuery.value = item.name
        lastSelectedDestination = item.name
        _destinationSuggestions.value = emptyList()
    }

    private suspend fun fetchSuggestions(query: String): List<SuggestionItem> {
        Log.d("MapboxDebug", "fetchSuggestions elindult a következőre: $query")
        return try {
            val response = NetworkModule.mapboxApi.getSuggestions(
                query = query,
                accessToken = mapboxToken,
                sessionToken = sessionToken
            )
            response.suggestions
        } catch (e: Exception) {
            emptyList()
        }
    }
}
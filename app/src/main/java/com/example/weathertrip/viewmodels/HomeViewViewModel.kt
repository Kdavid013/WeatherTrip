package com.example.weathertrip.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weathertrip.models.SuggestionItemModel
import com.example.weathertrip.models.TravelMode
import com.example.weathertrip.responses.SuggestionItemDTO
import com.example.weathertrip.responses.toDomainModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

// Modell a Mapbox találathoz
@OptIn(FlowPreview::class)
class HomeViewViewModel : ViewModel() {

    private var currentSessionToken = java.util.UUID.randomUUID().toString()

    // Kezdőpont állapotai
    val startQuery = MutableStateFlow("")
    private val _startSuggestions = MutableStateFlow<List<SuggestionItemModel>>(emptyList())
    val startSuggestions: StateFlow<List<SuggestionItemModel>> = _startSuggestions.asStateFlow()
    private val _startCoordinates = MutableStateFlow<Pair<Double, Double>?>(null)
    val startCoordinates = _startCoordinates.asStateFlow()
    private var lastSelectedStart = ""

    // Célállomás állapotai
    val destinationQuery = MutableStateFlow("")
    private val _destinationSuggestions = MutableStateFlow<List<SuggestionItemModel>>(emptyList())
    val destinationSuggestions: StateFlow<List<SuggestionItemModel>> = _destinationSuggestions.asStateFlow()
    private val _destinationCoordinates = MutableStateFlow<Pair<Double, Double>?>(null)
    val destinationCoordinates = _destinationCoordinates.asStateFlow()
    private var lastSelectedDestination = ""

    private val _selectedTravelMode = MutableStateFlow(TravelMode.DRIVING)
    val selectedTravelMode = _selectedTravelMode.asStateFlow()

    private val _navigateEvent = MutableSharedFlow<Pair<String,String>>()
    val navigateEvent = _navigateEvent.asSharedFlow()

    fun onTravelModeChange(mode: TravelMode) {
        _selectedTravelMode.value = mode
        Log.d("MapboxDebug", "Utazási mód módosítva: ${mode.displayName}")
    }

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

    fun selectStartLocation(item: SuggestionItemModel) {
        startQuery.value = item.title
        lastSelectedStart = item.title
        _startSuggestions.value = emptyList() // lista elrejtése kiválasztás után

        viewModelScope.launch {
            val coords = fetchCoordinates(item.id)
            _startCoordinates.value = coords
            Log.d("MapboxDebug", "Kezdőpont mentve: $coords")
        }
    }

    fun selectDestinationLocation(item: SuggestionItemModel) {


        destinationQuery.value = item.title
        lastSelectedDestination = item.title
        _destinationSuggestions.value = emptyList()

        viewModelScope.launch {
            val coords = fetchCoordinates(item.id)
            _destinationCoordinates.value = coords
            Log.d("MapboxDebug", "Célállomás mentve: $coords")
        }
    }

    private suspend fun fetchCoordinates (mapboxId: String) : Pair<Double, Double>? {
       return try{
            val response = NetworkModule.mapboxApi.getRetrieve(id = mapboxId, sessionToken = currentSessionToken)
            val coordinateList = response.features.firstOrNull()?.geometry?.coordinates

           if (coordinateList != null && coordinateList.size >= 2){
               Pair(coordinateList[0],coordinateList[1])
           }else{
               null
           }
        }
        catch (e: Exception){

            Log.e("MapboxDebug", "Hiba a Retrieve végponton! Ugye jó az ID?", e)
            null
        }
    }

    private suspend fun fetchSuggestions(query: String): List<SuggestionItemModel> {
        Log.d("MapboxDebug", "fetchSuggestions elindult a következőre: $query")
        return try {
            val response = NetworkModule.mapboxApi.getSuggestions(
                query = query,
                sessionToken = currentSessionToken
            )
            response.suggestions.map {it.toDomainModel()}
        } catch (e: Exception) {
            Log.e("MapboxDebug", "Hiba a Suggest végponton!", e)
            emptyList()
        }
    }

    fun onGoClicked() {
        val start = _startCoordinates.value
        val dest = _destinationCoordinates.value

        if (start != null && dest != null) {
            // Itt van a helye a string formázásnak (üzleti logika)
            val coordinatesString = "${start.first},${start.second};${dest.first},${dest.second}"

            // Itt van a helye az API specifikus konverziónak (üzleti logika)
            val profile = when (_selectedTravelMode.value) {
                TravelMode.DRIVING -> "mapbox/driving"
                TravelMode.CYCLING -> "mapbox/cycling"
                TravelMode.WALKING -> "mapbox/walking"
                else -> "mapbox/driving"
            }

            // Esemény kiküldése a UI-nak
            viewModelScope.launch {
                _navigateEvent.emit(Pair(profile, coordinatesString))
            }
        } else {
            // Opcionális: Itt lehetne egy másik eseményt küldeni pl. hibaüzenet megjelenítésére
            Log.d("MapboxDebug", "Hiányzó adatok a navigációhoz")
        }
    }

}
package com.example.weathertrip.viewmodels


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RouteViewViewModel : ViewModel() {
    
    private val _routeCities = MutableStateFlow<List<String>>(emptyList())
    val routeCities = _routeCities.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    fun loadRoute(profile: String, coordinates: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = NetworkModule.mapboxApi.getRoute(
                    profile = profile,
                    coordinates = coordinates,
                )

                val cityNames = response.waypoints.map { it.name }.filter { it.isNotEmpty() }
                Log.e("RouteDebug", "Útvonal létrehozva!")
                println(response)
                _routeCities.value = cityNames

            } catch (e: Exception) {
                Log.e("RouteDebug", "Hiba az útvonal lekérésekor!", e)
                _routeCities.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }
}
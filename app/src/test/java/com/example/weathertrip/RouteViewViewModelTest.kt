package com.example.weathertrip

import com.example.weathertrip.viewmodels.RouteViewViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Test

class RouteViewViewModelTest {

    @Test
    fun loadDummyData_populatesRouteCitiesAndSetsLoadingFalse() {
        val viewModel = RouteViewViewModel()

        viewModel.loadDummyData()

        val cities = viewModel.routeCities.value
        val isLoading = viewModel.isLoading.value

        assertFalse(isLoading)
        assertEquals(5, cities.size)
        assertEquals("Budapest", cities[0].cityName)
        assertEquals("Tatabánya", cities[1].cityName)
        assertEquals("Győr", cities[2].cityName)
        assertEquals("Hegyeshalom", cities[3].cityName)
        assertEquals("Bécs", cities[4].cityName)
    }
}

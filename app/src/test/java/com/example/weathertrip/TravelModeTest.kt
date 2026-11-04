package com.example.weathertrip

import com.example.weathertrip.models.TravelMode
import org.junit.Assert.assertEquals
import org.junit.Test

class TravelModeTest {

    @Test
    fun travelMode_enumEntriesHaveCorrectProperties() {
        assertEquals("Driving", TravelMode.DRIVING.displayName)
        assertEquals("driving", TravelMode.DRIVING.apiProfile)

        assertEquals("Cycling", TravelMode.CYCLING.displayName)
        assertEquals("cycling", TravelMode.CYCLING.apiProfile)

        assertEquals("Walking", TravelMode.WALKING.displayName)
        assertEquals("walking", TravelMode.WALKING.apiProfile)

        assertEquals(3, TravelMode.entries.size)
    }
}

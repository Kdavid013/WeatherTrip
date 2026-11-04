package com.example.weathertrip

import com.example.weathertrip.viewmodels.BleViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Test
import java.lang.reflect.Constructor

class BleViewModelTest {

    @Test
    fun loadDummyData_populatesTemperatureHistoryAndState() {
        val dummyCentral = createDummyCentral()
        val viewModel = BleViewModel(dummyCentral)

        viewModel.loadDummyData()

        assertEquals("Teszt mód (Mock adatok)", viewModel.connectionState)
        assertEquals("24.5", viewModel.temperature)
        assertEquals("55.0", viewModel.humidity)
        assertEquals(9, viewModel.temperatureHistory.size)
        assertEquals(23.5f, viewModel.temperatureHistory[0], 0.01f)
    }

    @Test
    fun stopScanning_returnsEarly_whenNotScanning() {
        val dummyCentral = createDummyCentral()
        val viewModel = BleViewModel(dummyCentral)

        assertFalse(viewModel.isScanning)

        viewModel.stopScanning()
        assertFalse(viewModel.isScanning)
    }

    private fun createDummyCentral(): com.welie.blessed.BluetoothCentralManager {
        val unsafeClass = Class.forName("sun.misc.Unsafe")
        val field = unsafeClass.getDeclaredField("theUnsafe")
        field.isAccessible = true
        val unsafe = field.get(null)
        val allocateInstance = unsafeClass.getMethod("allocateInstance", Class::class.java)
        return allocateInstance.invoke(unsafe, com.welie.blessed.BluetoothCentralManager::class.java) as com.welie.blessed.BluetoothCentralManager
    }
}

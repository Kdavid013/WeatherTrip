package com.example.weathertrip.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.welie.blessed.BluetoothCentralManager
import com.welie.blessed.BluetoothPeripheral
import com.welie.blessed.BluetoothPeripheralCallback
import com.welie.blessed.GattStatus
import java.util.UUID

class BleViewModel(private val central: BluetoothCentralManager) : ViewModel() {

    var connectionState by mutableStateOf("Nincs csatlakozva")
        private set

    // Állapotváltozók az időjárási adatoknak
    var temperature by mutableStateOf("--")
        private set

    var humidity by mutableStateOf("--")
        private set

    val discoveredDevices = mutableStateListOf<BluetoothPeripheral>()

    var isScanning by mutableStateOf(false)
        private set

    private val serviceUuid = UUID.fromString("0000180F-0000-1000-8000-00805f9b34fb")
    private val charUuid = UUID.fromString("00002a19-0000-1000-8000-00805f9b34fb")

    fun startScanning() {
        if (isScanning) return
        discoveredDevices.clear()
        connectionState = "Eszközök keresése..."
        isScanning = true
        central.scanForPeripherals()
    }

    fun stopScanning() {
        if (!isScanning) return
        central.stopScan()
        isScanning = false
        if (connectionState == "Eszközök keresése...") {
            connectionState = "Keresés leállítva"
        }
    }

    fun addDiscoveredDevice(peripheral: BluetoothPeripheral) {
        if (!discoveredDevices.any { it.address == peripheral.address } && !peripheral.name.isNullOrBlank()) {
            discoveredDevices.add(peripheral)
        }
    }

    // --- Csatlakozás és Adatfogadás ---
    private val peripheralCallback = object : BluetoothPeripheralCallback() {

        override fun onServicesDiscovered(peripheral: BluetoothPeripheral) {
            connectionState = "Kapcsolódva! Feliratkozás az adatokra..."
            val characteristic = peripheral.getCharacteristic(serviceUuid, charUuid)

            if (characteristic != null) {
                // Bekapcsoljuk az értesítéseket (NOTIFY) a karakterisztikára
                val success = peripheral.setNotify(characteristic, true)
                if (!success) {
                    connectionState = "Hiba: Nem sikerült feliratkozni az értesítésekre!"
                }
            } else {
                connectionState = "Hiba: Karakterisztika nem található!"
            }
        }

        // Ide érkeznek be az adatok minden alkalommal, amikor az ESP32 meghívja a notify() függvényt
        override fun onCharacteristicUpdate(
            peripheral: BluetoothPeripheral,
            value: ByteArray,
            characteristic: android.bluetooth.BluetoothGattCharacteristic,
            status: GattStatus
        ) {
            if (status == GattStatus.SUCCESS && characteristic.uuid == charUuid) {
                val payload = String(value, Charsets.UTF_8) // pl. "24.5,60.0"
                val parts = payload.split(",")
                if (parts.size == 2) {
                    temperature = parts[0]
                    humidity = parts[1]
                    connectionState = "Adatok fogadása folyamatban"
                }
            }
        }

        override fun onNotificationStateUpdate(
            peripheral: BluetoothPeripheral,
            characteristic: android.bluetooth.BluetoothGattCharacteristic,
            status: GattStatus
        ) {
            if (status == GattStatus.SUCCESS) {
                connectionState = "Adatfogadás aktív"
            } else {
                connectionState = "Hiba az értesítés beállításakor: $status"
            }
        }
    }

    fun connectToEsp32(peripheral: BluetoothPeripheral) {
        stopScanning()
        try {
            connectionState = "Csatlakozás ide: ${peripheral.name}..."
            central.connectPeripheral(peripheral, peripheralCallback)
        } catch (e: Exception) {
            connectionState = "Hiba: ${e.localizedMessage}"
        }
    }
}
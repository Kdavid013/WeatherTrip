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
import com.welie.blessed.WriteType
import java.util.UUID

class BleViewModel(private val central: BluetoothCentralManager) : ViewModel() {

    var connectionState by mutableStateOf("Nincs csatlakozva")
        private set

    // Ebbe a listába gyűjtjük a megtalált eszközöket, a Compose UI látni fogja a változást
    val discoveredDevices = mutableStateListOf<BluetoothPeripheral>()

    var isScanning by mutableStateOf(false)
        private set

    private val serviceUuid = UUID.fromString("0000180F-0000-1000-8000-00805f9b34fb")
    private val charUuid = UUID.fromString("00002a19-0000-1000-8000-00805f9b34fb")

    // --- Keresés (Scan) indítása és leállítása ---
    fun startScanning() {
        if (isScanning) return
        discoveredDevices.clear()
        connectionState = "Eszközök keresése..."
        isScanning = true

        // Elindítjuk a keresést. A Blessed a MainActivity-ben megadott callback-en
        // keresztül fogja visszadni a találatokat, de mi most közvetlenül a central-ból is lekérhetjük,
        // vagy kézzel is menedzselhetjük. De a legtisztább, ha adunk neki egy szűrést vagy átírjuk a MainActivity-t.
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

    // Ezt a függvényt fogjuk hívni a MainActivity callback-jéből, hogy beillesszük a talált eszközt
    fun addDiscoveredDevice(peripheral: BluetoothPeripheral) {
        // Csak akkor adjuk hozzá, ha még nincs benne a listában és van neve (opcionális szűrés)
        if (!discoveredDevices.contains(peripheral) && !peripheral.name.isNullOrBlank()) {
            discoveredDevices.add(peripheral)
        }
    }

    // --- Csatlakozás ---
    private val peripheralCallback = object : BluetoothPeripheralCallback() {
        override fun onServicesDiscovered(peripheral: BluetoothPeripheral) {
            connectionState = "Szolgáltatások feltérképezve, adatküldés..."
            val characteristic = peripheral.getCharacteristic(serviceUuid, charUuid)
            if (characteristic != null) {
                val dataToSend = byteArrayOf(0x01)
                peripheral.writeCharacteristic(characteristic, dataToSend, WriteType.WITH_RESPONSE)
            } else {
                connectionState = "Hiba: Karakterisztika nem található!"
            }
        }

        override fun onCharacteristicWrite(
            peripheral: BluetoothPeripheral, value: ByteArray, characteristic: android.bluetooth.BluetoothGattCharacteristic, status: GattStatus
        ) {
            connectionState = if (status == GattStatus.SUCCESS) "Adat elküldve!" else "Hiba: $status"
        }
    }

    fun connectToEsp32(peripheral: BluetoothPeripheral) {
        stopScanning() // Csatlakozás előtt érdemes leállítani a keresést
        try {
            connectionState = "Csatlakozás ide: ${peripheral.name}..."
            central.connectPeripheral(peripheral, peripheralCallback)
        } catch (e: Exception) {
            connectionState = "Hiba: ${e.localizedMessage}"
        }
    }
}
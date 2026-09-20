package com.example.weathertrip

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import com.example.weathertrip.navigation.NavigationRoot
import com.example.weathertrip.ui.theme.WeatherTripTheme
import com.example.weathertrip.viewmodels.BleViewModel
import com.example.weathertrip.views.BleScreen
import com.welie.blessed.BluetoothCentralManager
import com.welie.blessed.BluetoothCentralManagerCallback
import com.welie.blessed.BluetoothPeripheral

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        // Előbb hozzuk létre a central-t egy ideiglenes null kód miatt, vagy trükközzünk a sorrenddel:
        // Mivel a callback-nek kell a viewModel, de a central-nak meg a callback kell,
        // egy késleltetett változót (var) használunk a callback-hez.

        lateinit var viewModel: BleViewModel

        val managerCallback = object : BluetoothCentralManagerCallback() {
            override fun onDiscoveredPeripheral(peripheral: BluetoothPeripheral, scanResult: android.bluetooth.le.ScanResult) {
                // Átadjuk a megtalált eszközt a ViewModelnek
                viewModel.addDiscoveredDevice(peripheral)
            }
        }

        val handler = Handler(Looper.getMainLooper())
        val central = BluetoothCentralManager(applicationContext, managerCallback, handler)

        viewModel = BleViewModel(central)

        setContent {
            NavigationRoot(bleViewModel = viewModel)
        }
    }
}

package com.example.weathertrip.views

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weathertrip.viewmodels.BleViewModel

@Composable
fun BleScreen(viewModel: BleViewModel = viewModel()) {

    // 1. Frissített engedélykérő: minden szükséges jogot megvizsgál
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val scanGranted = permissions[Manifest.permission.BLUETOOTH_SCAN] == true
        val connectGranted = permissions[Manifest.permission.BLUETOOTH_CONNECT] == true
        val fineLocationGranted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true

        // Csak akkor indítunk scannelést, ha minden kritikus engedély megvan
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (scanGranted && connectGranted && fineLocationGranted) {
                viewModel.startScanning()
            }
        } else {
            if (fineLocationGranted) {
                viewModel.startScanning()
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .padding(top = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Állapot: ${viewModel.connectionState}", fontSize = 16.sp)

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            if (viewModel.isScanning) {
                viewModel.stopScanning()
            } else {
                // 2. A gomb megnyomásakor kötelezően átadjuk a helyadat-kérést is!
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    permissionLauncher.launch(
                        arrayOf(
                            Manifest.permission.BLUETOOTH_SCAN,
                            Manifest.permission.BLUETOOTH_CONNECT,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        )
                    )
                } else {
                    permissionLauncher.launch(
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
                    )
                }
            }
        }) {
            Text(if (viewModel.isScanning) "Keresés leállítása" else "Eszközök keresése")
        }
        // Dinamikus lista a megtalált BLE eszközökből
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(viewModel.discoveredDevices) { peripheral ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { viewModel.connectToEsp32(peripheral) },
                    colors = CardDefaults.cardColors()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = peripheral.name ?: "Ismeretlen eszköz", fontSize = 16.sp)
                        Text(text = "MAC: ${peripheral.address}", fontSize = 12.sp)
                    }
                }
            }
        }
    }
}
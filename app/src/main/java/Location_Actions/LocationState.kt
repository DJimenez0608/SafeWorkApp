package com.example.main.Location_Actions

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Looper
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModel
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

data class LocationState(val latitude : Double = 0.0, val longitude : Double = 0.0)

class LocationViewModel : ViewModel(){
    private val _uiState = MutableStateFlow(LocationState())
    val state: StateFlow<LocationState> = _uiState

    fun update(longitude: Double, latitude: Double){
        _uiState.update { it.copy(longitude,latitude) }
    }
}
@SuppressLint("MissingPermission")
@Composable
fun GPSScreen(viewModel: LocationViewModel) {
    val state by viewModel.state.collectAsState()
    var active by remember { mutableStateOf(true) }
    val context = LocalContext.current
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }

    val locationRequest = LocationRequest.Builder(
        Priority.PRIORITY_HIGH_ACCURACY, 5000 // cada 5s
    ).build()

    val locationCallback = remember {
        object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                for (location in result.locations) {
                    viewModel.update(location.longitude, location.latitude)
                }
            }
        }
    }
    LaunchedEffect(active) {
        if (active) {
            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            )
        } else {
            fusedLocationClient.removeLocationUpdates(locationCallback)
        }
    }

    Column {
        Row {
            Text("Longitud: ")
            Text(state.longitude.toString())
        }
        Row {
            Text("Latitud: ")
            Text(state.latitude.toString())
        }

        if (active) {
            Button(onClick = { active = false }) {
                Text("Stop Location Updates", color = Color.Red)
            }
        } else {
            Button(onClick = { active = true }) {
                Text("Start Location Updates", color = Color.White)
            }
        }
    }
}

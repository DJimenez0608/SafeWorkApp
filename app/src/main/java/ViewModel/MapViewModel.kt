package com.example.main.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.main.Location_Actions.ServicioUbicacionAndroid
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class MapViewModel(app: Application): AndroidViewModel(app) {
    private val ubicacion = ServicioUbicacionAndroid(app)

    // Ubicación en tiempo real (cada 7s)
    val flujoUbicacion = ubicacion.flujo(7000L).stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000L), null
    )
}

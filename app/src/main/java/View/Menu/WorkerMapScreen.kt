package com.example.main.Screens.Menu

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.main.CompReusable.LocationPermissionGate
import com.example.main.ViewModel.MapViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*

@Composable
fun WorkerMapScreen(
    vm: MapViewModel = viewModel(),
    zonaObra: List<LatLng> = listOf( // <-- EDITA con tu polígono real
        LatLng(4.6501, -74.0620),
        LatLng(4.6509, -74.0600),
        LatLng(4.6496, -74.0590),
        LatLng(4.6489, -74.0615)
    )
) {
    LocationPermissionGate(
        onGranted = {
            val ubicacion = vm.flujoUbicacion.collectAsState().value
            val cameraPositionState = rememberCameraPositionState {
                position = CameraPosition.fromLatLngZoom(zonaObra.first(), 16f)
            }

            val dentro = remember(ubicacion, zonaObra) {
                ubicacion?.let { pointInPolygon(LatLng(it.lat, it.lon), zonaObra) } ?: false
            }

            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState,
                properties = MapProperties(isMyLocationEnabled = true),
                uiSettings = MapUiSettings(myLocationButtonEnabled = true, zoomControlsEnabled = false)
            ) {
                Polygon(
                    points = zonaObra,
                    fillColor = if (dentro) 0x55FFA500 else 0x22FFA500, // ámbar con distinto alpha
                    strokeColor = 0xAAFFA500.toInt(),
                    strokeWidth = 5f
                )
            }

            LaunchedEffect(ubicacion) {
                val loc = ubicacion ?: return@LaunchedEffect
                cameraPositionState.animate(
                    update = CameraUpdateFactory.newLatLngZoom(LatLng(loc.lat, loc.lon), 17f)
                )
            }
        }
    )
}

/** Punto en polígono (ray-casting) */
fun pointInPolygon(point: LatLng, polygon: List<LatLng>): Boolean {
    var inside = false
    var j = polygon.lastIndex
    for (i in polygon.indices) {
        val xi = polygon[i].longitude; val yi = polygon[i].latitude
        val xj = polygon[j].longitude; val yj = polygon[j].latitude
        val intersect = ((yi > point.latitude) != (yj > point.latitude)) &&
                (point.longitude < (xj - xi) * (point.latitude - yi) / ((yj - yi) + 1e-9) + xi)
        if (intersect) inside = !inside
        j = i
    }
    return inside
}

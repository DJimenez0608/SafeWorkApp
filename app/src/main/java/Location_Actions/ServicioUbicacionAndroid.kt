package com.example.main.Location_Actions

import android.annotation.SuppressLint
import android.content.Context
import com.google.android.gms.location.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

data class LecturaUbicacion(
    val lat: Double,
    val lon: Double,
    val alt: Double?,
    val velocidad: Float?,
    val rumbo: Float?,
    val precision: Float?,
    val timestamp: Long
)

class ServicioUbicacionAndroid(private val context: Context) {
    private val client by lazy { LocationServices.getFusedLocationProviderClient(context) }

    @SuppressLint("MissingPermission")
    suspend fun obtenerUnaVez(): LecturaUbicacion? = suspendCancellableCoroutine { cont ->
        client.lastLocation
            .addOnSuccessListener { loc ->
                if (loc != null) {
                    cont.resume(
                        LecturaUbicacion(
                            lat = loc.latitude,
                            lon = loc.longitude,
                            alt = loc.altitude.takeIf { it != 0.0 },
                            velocidad = loc.speed,
                            rumbo = loc.bearing,
                            precision = loc.accuracy,
                            timestamp = System.currentTimeMillis()
                        )
                    )
                } else cont.resume(null)
            }
            .addOnFailureListener { cont.resume(null) }
    }

    @SuppressLint("MissingPermission")
    fun flujo(intervalMs: Long = 7000L): Flow<LecturaUbicacion> = callbackFlow {
        val req = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, intervalMs).build()
        val cb = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                val loc = result.lastLocation ?: return
                trySend(
                    LecturaUbicacion(
                        lat = loc.latitude,
                        lon = loc.longitude,
                        alt = loc.altitude.takeIf { it != 0.0 },
                        velocidad = loc.speed,
                        rumbo = loc.bearing,
                        precision = loc.accuracy,
                        timestamp = System.currentTimeMillis()
                    )
                )
            }
        }
        client.requestLocationUpdates(req, cb, context.mainLooper)
        awaitClose { client.removeLocationUpdates(cb) }
    }
}

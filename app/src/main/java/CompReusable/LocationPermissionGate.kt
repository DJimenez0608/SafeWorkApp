@file:OptIn(com.google.accompanist.permissions.ExperimentalPermissionsApi::class)

package com.example.main.CompReusable

import android.Manifest
import android.os.Build
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.google.accompanist.permissions.rememberMultiplePermissionsState

@Composable
fun LocationPermissionGate(
    onGranted: @Composable () -> Unit,
    rationale: @Composable () -> Unit = {
        Text("Necesitamos tu ubicación para mostrar si estás dentro de tu zona de trabajo.")
    },
    onDeniedPermanently: @Composable () -> Unit = {
        Text("Permiso de ubicación denegado. Ve a Ajustes para habilitarlo.")
    }
) {
    val perms = if (Build.VERSION.SDK_INT >= 31) {
        listOf(Manifest.permission.ACCESS_FINE_LOCATION)
    } else {
        listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    }

    val state = rememberMultiplePermissionsState(perms)

    LaunchedEffect(Unit) {
        state.launchMultiplePermissionRequest()
    }

    when {
        state.allPermissionsGranted -> onGranted()
        state.shouldShowRationale   -> rationale()
        else                        -> onDeniedPermanently()
    }
}

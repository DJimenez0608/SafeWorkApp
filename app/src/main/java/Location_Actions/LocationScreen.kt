package com.example.main.Location_Actions

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.location.LocationServices

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun LocationScreen (navController: NavController){
    val locationPermission = rememberPermissionState(
        android.Manifest.permission.ACCESS_FINE_LOCATION
    )
    if(locationPermission.status.isGranted){
        Column {

            LastLocation()
        }
    }else{
        Button(
            onClick = {locationPermission.launchPermissionRequest()}
        ) {
            Text("solicitar permiso")
        }
    }
}


@SuppressLint("MissingPermission")
@Composable
fun LastLocation (){
    val context = LocalContext.current
    var longitude by remember { mutableStateOf(0.0) }
    var latitude by remember { mutableStateOf(0.0) }
    val locationClient = LocationServices.getFusedLocationProviderClient(context)
    if(permissionGranted(context, android.Manifest.permission.ACCESS_FINE_LOCATION)){
        locationClient.lastLocation.addOnSuccessListener {location ->
            location?.let{
                latitude = location.latitude
                longitude = location.longitude
            }
        }
        }
    Column {
        Text("Latitude ${latitude}", )
        Text("Longitude ${longitude}", )
    }

}

fun permissionGranted(context: Context, permission: String): Boolean{
    return(ActivityCompat.checkSelfPermission(context,permission) == PackageManager.PERMISSION_GRANTED)
}
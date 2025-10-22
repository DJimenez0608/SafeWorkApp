package com.example.main

import Navigation.AppNavigation
import android.annotation.SuppressLint
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import com.google.firebase.auth.FirebaseAuth
import org.osmdroid.config.Configuration
val auth: FirebaseAuth = FirebaseAuth.getInstance()
val USERS = "users/"
class MainActivity : ComponentActivity(){
    @SuppressLint("ServiceCast")
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Configuration.getInstance().userAgentValue = "AndroidApp"

        setContent{

            AppNavigation()

        }
    }
}
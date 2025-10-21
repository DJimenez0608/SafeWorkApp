package com.example.main.ViewModel

import android.content.pm.PackageManager
import androidx.lifecycle.ViewModel
import com.example.main.Model.Location
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class LocationViewModel () : ViewModel(){
    val _locationViewModel = MutableStateFlow<Location>(Location())
    var location = _locationViewModel.asStateFlow()

    fun UpdateUserLocation (longitude : Double, latitude : Double ){
        _locationViewModel.update {
            it.copy(longitude,latitude)
        }
    }
}

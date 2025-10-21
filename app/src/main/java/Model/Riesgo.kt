package com.example.main.Model

import org.osmdroid.util.GeoPoint

data class Riesgo(
    val nombre : String = "",
    val descripcionRiesgo : String = "",
    val tipoRiesgo : List<String>? = listOf<String>(),
    val ubicacionriesgo : GeoPoint? = null,
    val fotoRiesgo : String = "",
)

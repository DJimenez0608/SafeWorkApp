package com.example.main.Model

import org.osmdroid.util.GeoPoint

data class ZonaConstruccion(
    val nombreZona : String,
    val codigoZona : String,
    val puntosDelimitadores : List<GeoPoint>,
    val trabajadores : List<String>,// ACA SOLO VA EL ID DE LOS TRABAJADORES QUE SE ENCUENTRAN EN LA ZONA
    val riesgosContruccion : List<Riesgo> = listOf(),

    )

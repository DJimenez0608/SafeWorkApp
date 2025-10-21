package com.example.main.Screens

import Navigation.AppScreens
import View.LogInScreen
import com.example.practica.R

import android.Manifest


import android.content.Context
import android.content.Context.SENSOR_SERVICE
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.media.Image
import android.media.MediaPlayer
import android.os.Looper
import android.util.Log
import androidx.annotation.RequiresPermission
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.Icons.Default
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.app.ActivityCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.main.CompReusable.CustomAlert
import com.example.main.CompReusable.ReusableButton
import com.example.main.ViewModel.LocationViewModel
import com.example.main.auth
import com.example.main.utils.theme.BoldOrange
import com.example.main.utils.theme.LightOrange
import com.example.main.utils.theme.Orange
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import kotlinx.coroutines.NonCancellable.key
import org.osmdroid.tileprovider.tilesource.OnlineTileSourceBase
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.tileprovider.tilesource.XYTileSource
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.Polygon

@RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen (navController: NavController, viewModel: LocationViewModel = viewModel ()) {
    val context = LocalContext.current
    val locationState by viewModel.location.collectAsState()

    //Map Theme
    var mapMode by remember{mutableStateOf<OnlineTileSourceBase>(TileSourceFactory.MAPNIK)}
    val darkTileSource = XYTileSource(
        "Carto Dark",
        0,
        19,
        256,
        ".png",
        arrayOf("https://basemaps.cartocdn.com/dark_all/")
    )
    val sheetColor : Color = if (mapMode == TileSourceFactory.MAPNIK){
             Color.White
        }else{
         Color.Black
    }



    // User current Location
    var mapSetCenter by remember { mutableStateOf(false) }
    val locationClient = LocationServices.getFusedLocationProviderClient(context)
    val locationRequest : LocationRequest = createLocationRequest()

    //callback location
    lateinit var locationCallback: LocationCallback
    fun createLocationCallBack() : LocationCallback{
        val locationCallback = object : LocationCallback(){
            override fun onLocationResult(result: LocationResult) {
                super.onLocationResult(result)
                result.lastLocation?.let {location->
                    viewModel.UpdateUserLocation(location.longitude,location.latitude)
                }
            }
        }
        return locationCallback
    }
    locationCallback = createLocationCallBack()

    //Empezar y parar actualizacion de localización
     fun startLocationUpdates() {
        if(ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED){
            locationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
        }
    }
     fun stopLocationUpdates(){
        locationClient.removeLocationUpdates(locationCallback)
    }

    // PELIGROSO
    var movimientoPeligroso by remember { mutableStateOf(false) }
    var temperaturaPeligrosa by remember { mutableStateOf(false) }

    //¿QUE PASA CUANDO EL SENSOR DE LUZ/TEMPERATURA/MOVIMIENTO DETECTAN UN CAMBIO
    val sensorListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent?) {
            if (event?.sensor?.type == Sensor.TYPE_LIGHT) {
                val lux = event.values[0]
                mapMode = if(lux<200) darkTileSource else TileSourceFactory.MAPNIK
            }
            if (event?.sensor?.type == Sensor.TYPE_AMBIENT_TEMPERATURE){
                val temp = event.values[0]

                if(temp > 42 || temp < -30){
                    //MANDAR UNA NOTIFICACION
                    Log.i("TEMPERATURE", "$temp")
                    temperaturaPeligrosa = true
                }else{
                    temperaturaPeligrosa = false
                }


            }
            if (event?.sensor?.type == Sensor.TYPE_ACCELEROMETER){
                val accelx = event.values[0]
                val accely = event.values[1]
                val accelz = event.values[2]

                //magnitude del movimiento
                val magnitude = Math.sqrt((accelx*accelx + accely*accely + accelz*accelz).toDouble())
                if(magnitude > 15){
                    //emitir sonido
                    notificarUsuario(context)
                    //mostrar pop up
                    movimientoPeligroso = true
                    Log.i("Alerta de movimiento", "$magnitude")
                }


            }

        }
        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
    }

    //AL ENTRAR AL COMPOSE SESUBSCRIBE A LOS SENSORES/ESTA ACTUALIZANDO LA UBICACION
    DisposableEffect(key) {
        startLocationUpdates()
        val sensorManager = context.getSystemService(SENSOR_SERVICE) as SensorManager
        val lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
        val temperature = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)
        val acelerometro = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        temperature?.let{
            sensorManager.registerListener(
                sensorListener,
                temperature,
                SensorManager.SENSOR_DELAY_NORMAL
            )
        }
        lightSensor?.let{
            sensorManager.registerListener(
                sensorListener,
                lightSensor,
                SensorManager.SENSOR_DELAY_NORMAL
            )
        }
        lightSensor?.let{
            sensorManager.registerListener(
                sensorListener,
                acelerometro,
                SensorManager.SENSOR_DELAY_NORMAL
            )
        }

        //AL SALIR SE DES-SUBSCRIBE Y PARA DE ACTUALIZAR

        onDispose {
            stopLocationUpdates()
            sensorManager.unregisterListener(sensorListener)
        }

    }




var mostrarMenu by remember { mutableStateOf(false) }

    BottomSheetScaffold(
        sheetContent = {SheetContent()},
        sheetContainerColor =sheetColor,
        sheetShape = RoundedCornerShape(5),
        sheetPeekHeight = 70.dp,
        sheetDragHandle = {
            Box(
                modifier = Modifier
                    .padding(top = 15.dp, bottom = 10.dp)
                    .size(width = 90.dp, height = 5.dp)
                    .background(
                        color = Color.Gray,
                        shape = RoundedCornerShape(4.dp)
                    )
            )
        },


    )  { innerPadding ->
        Box(
            modifier = Modifier.padding(innerPadding),
        ) {
            if (movimientoPeligroso){
                MovementAlertDialog(
                    onConfirm = {movimientoPeligroso = false},
                    image = painterResource(R.drawable.caida),
                    nombreRiesgo = "Caida peligrosa",
                    descripcionRiesgo = "Se detecto una movimiento inusual. Porfavor confirmar que todo esta bien"
                )
            }
            if (temperaturaPeligrosa){
                MovementAlertDialog(
                    onConfirm = {temperaturaPeligrosa = false},
                    image = painterResource(R.drawable.temperatura),
                    nombreRiesgo = "Temperatura peligrosa",
                    descripcionRiesgo = "Se detectaron temperaturas extremas. Porfavor confirmar que todo esta bien"
                )
            }
            if (mostrarMenu){
                MostrarMenu(onDismissRequest = {mostrarMenu = !mostrarMenu}, navController)
            }
            AndroidView(

                modifier = Modifier.fillMaxSize(),
                factory = {
                    val mapView = MapView(context)

                    mapView.setTileSource(mapMode)
                    mapView.setMultiTouchControls(true)
                    mapView.controller.setZoom(16.0)
                    mapView.controller.setCenter(GeoPoint(locationState.latitude,locationState.longitude))
                    mapView
                },
                update = {mapView ->
                    mapView.overlays.clear()
                    mapView.setTileSource(mapMode)
                    val startPoint = (GeoPoint(locationState.latitude,locationState.longitude))
                    mapView.controller.setCenter(startPoint)

                    //ZONA DELIMITADA
                    val poligono = Polygon(mapView)
                    poligono.title = "Zona de construcción"
                    poligono.points =  listOf<GeoPoint>(
                        GeoPoint(4.635378, -74.100595), // Punto A
                        GeoPoint(4.637388, -74.097719), // Punto B
                        GeoPoint(4.630915, -74.093184), // Punto C
                        GeoPoint(4.629631, -74.094901)
                    )
                    poligono.fillColor =  android.graphics.Color.argb(128, 255, 255, 0)
                    poligono.strokeColor = android.graphics.Color.RED
                    poligono.strokeWidth = 1f
                    mapView.overlays.add(poligono)


                    //MARKER USUARIO
                    val marker = Marker(mapView)
                    marker.position = startPoint
                    marker.title = "Bogota"
                    mapView.overlays.add(marker)


                    //RECENTRAR USUARIO
                    if(mapSetCenter == true){
                        mapView.controller.setCenter(startPoint)
                        mapSetCenter = false
                    }

                }
            )


            Column(
                modifier = Modifier.padding(10.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FloatingActionButton(
                    modifier = Modifier
                        .width(50.dp)
                        .height(50.dp),
                    shape = FloatingActionButtonDefaults.largeShape,
                    containerColor = BoldOrange,
                    onClick = {
                        //Mostrar el menu
                        mostrarMenu = !mostrarMenu

                    },
                ) {
                    Icon(
                        tint = Color.White,
                        imageVector = Default.Menu,
                        contentDescription = "boton de menu principal"
                    )
                }
                FloatingActionButton(
                    modifier = Modifier
                        .width(50.dp)
                        .height(50.dp),
                    shape = FloatingActionButtonDefaults.largeShape,
                    containerColor = BoldOrange,
                    onClick = {
                        //Aca la idea es que se re-ubique en la posicion en la que esta usuario actual
                   mapSetCenter =true

                    },
                ) {
                    Icon(
                        tint = Color.White,
                        imageVector = Default.LocationOn,
                        contentDescription = "boton de menu principal"
                    )
                }
            }



        }
    }
}

//MENU
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MostrarMenu(onDismissRequest : () -> Unit, navController: NavController){
    BasicAlertDialog(

        onDismissRequest = onDismissRequest,
        content = {
            Surface (
                shape = RoundedCornerShape(14.dp),
                color = Color.White,
                tonalElevation = 6.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ){
                Column(
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        "MENÚ",
                        textAlign = TextAlign.Center,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier
                            .padding(top = 25.dp, bottom = 15.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                    Spacer(Modifier.height(10.dp))
                   Column (
                       horizontalAlignment = Alignment.Start
                   ){
                       OpcionesMenu(
                           contentDescription = "Chats",
                           imageVector = Default.Email,
                           onClick = {navController.navigate(AppScreens.ChatScreen.name)}

                           )
                       OpcionesMenu(
                           contentDescription = "Perfil",
                           imageVector = Default.Person,
                           onClick = {navController.navigate(AppScreens.ProfileScreen.name)}

                           )
                       OpcionesMenu(
                           contentDescription = "Agregar advertencia",
                           imageVector = Default.Add,
                           onClick = {navController.navigate(AppScreens.AddZoneRisk.name)}

                           )
                       OpcionesMenu(
                           contentDescription = "Cerrar sesión",
                           imageVector = Default.ExitToApp,
                           onClick = { auth.signOut()
                               navController.navigate(AppScreens.LogInScreen.name){
                                   popUpTo(AppScreens.HomeScreen.name){
                                       inclusive = true
                                   }
                               }
                           }

                       )
                   }

                }
            }
        }
    )
}

//OPCIONES DE NAVEGACION
@Composable
fun OpcionesMenu (contentDescription: String, imageVector: ImageVector,  onClick: () -> Unit){

    Row (
        modifier = Modifier
            .padding(bottom = 15.dp, start = 15.dp, end = 15.dp)
            .clickable(onClick = onClick)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ){

        Icon(
            imageVector = imageVector,
            contentDescription = contentDescription
        )
        Spacer(Modifier.width(10.dp))
        Text(
            contentDescription,
        )

    }
}

//CONTENIDO DEL SHEET
@Composable
fun SheetContent (){

val trabajadoresList = listOf(
    "Juan Carlo Rodriguez Sarmiento",
    "Santiago Rojas Pacheco" ,
    "Luis Fernando Martínez Gómez",
    "Carlos Andrés Ramírez Salazar",
    "José Manuel Torres Cárdenas" ,
    "Juan Esteban Morales Ríos",
    )
    var peligrosList = listOf(
        "Caidas de altura",
        "Golpes por objetos (movimiento/caida)",
        "Atrapamiento (maquinaria/excavacion/estructuras)",
        "Sobreesfuerzo (cargar peso)",
        "Ruido excesivo",
        "Vibraciones",
        "Radicación solar (calor extremo)",
        "Inhalación de polvo (cemento)",
        "Exposición a solventes, pinturas o adhesivos",
        "Contacto con sustancias corrosivas",
        "Picaduras de insectos o mordeduras de animales en campo",
        "Moho o bacterias en ambientes cerrados y húmedos",
        "Contacto con líneas eléctricas aéreas o subterráneas",
        "Caída de materiales mal almacenados",
        "Mala iluminación en zonas de trabajo",
        "Lluvias en la obra"
    )

    var trabajadores by remember { mutableStateOf(true) }
    var peligros by remember { mutableStateOf(false) }
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .fillMaxHeight(0.6F)
    ){
        Row (
            Modifier.background(shape = RoundedCornerShape(15), color = Color.LightGray)
        ){
            var colorTrabajadores = if (trabajadores){Orange}else{Color.LightGray}
            var colorPeligros = if (peligros){Orange}else{Color.LightGray}
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .background(color = colorTrabajadores, shape = RoundedCornerShape(15))
                    .border(
                        border = BorderStroke(1.dp, colorTrabajadores),
                        shape = RoundedCornerShape(15)
                    )
                    .weight(1f)
                    .height(25.dp)
                    .clickable {
                        trabajadores = true;
                        peligros = false
                    }

            ){
                Text(
                    "Trabajdores",
                    textAlign = TextAlign.Center
                )
            }
            Spacer(Modifier.width(5.dp))
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .background(color = colorPeligros, shape = RoundedCornerShape(15))
                    .border(
                        border = BorderStroke(1.dp, colorPeligros),
                        shape = RoundedCornerShape(15)
                    )
                    .weight(1f)
                    .height(25.dp)
                    .clickable {
                        peligros = true;
                        trabajadores = false
                    }
            ){
                Text(
                    "Peligros",
                    textAlign = TextAlign.Center
                )
            }
        }
        Spacer(Modifier.height(10.dp
        ))
            LazyColumn  (
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                if(trabajadores && peligros == false){
                    items(trabajadoresList){trabajador->
                        Spacer(Modifier.height(15.dp))
                        Row (
                            Modifier
                                .border(
                                    BorderStroke(1.dp, LightOrange),
                                    shape = RoundedCornerShape(10.dp)
                                )
                                .background(
                                    color = Color.LightGray,
                                    shape = RoundedCornerShape(10.dp)
                                )

                                .padding(10.dp)
                                .height(40.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            verticalAlignment = Alignment.CenterVertically,

                            ){
                            Box(
                                Modifier
                                    .background(shape = CircleShape, color = Orange),

                                ){
                                Icon(
                                    imageVector = Icons.Default.Person,
                                    contentDescription = "",
                                    modifier = Modifier.padding(4.dp)
                                )
                            }
                            Column {
                                Text(trabajador)
                                Text("Operador", color =  Color.Gray)
                            }
                        }

                    }
                }else{
                    items(peligrosList){peligro->
                        Spacer(Modifier.height(10.dp))
                        Row (
                            Modifier
                                .border(
                                    BorderStroke(1.dp, Orange),
                                    shape = RoundedCornerShape(10.dp)
                                )
                                .background(
                                    color = Color.LightGray,
                                    shape = RoundedCornerShape(10.dp)
                                )

                                .padding(10.dp)
                                .height(40.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            verticalAlignment = Alignment.CenterVertically,

                            ){
                            Box(
                                Modifier
                                    .background(shape = CircleShape, color = Orange)
                                    .height(30.dp)
                                    .width(30.dp),


                                ){

                            }
                                Text(peligro)

                        }

                    }
                }
            }

    }
}

fun createLocationRequest() : LocationRequest{
    val locationRequest = LocationRequest.Builder(
        Priority.
        PRIORITY_HIGH_ACCURACY, 10000)
        .setWaitForAccurateLocation(true)
        .setMinUpdateIntervalMillis(5000)
        .build()
    return locationRequest
}

//REPRODUCIR SONIDO DE NOTIFICACION
fun notificarUsuario (context : Context){
    val sonido = MediaPlayer.create(context,R.raw.notificationsound)
    sonido.start()
}

//ALERTA DE MOVIMIENTO INUSUAL
@Composable
fun MovementAlertDialog(
    onConfirm: () -> Unit,
    nombreRiesgo : String,
    descripcionRiesgo : String,
    image: Painter
) {
    AlertDialog(
        containerColor = Color.White,
        onDismissRequest = {  },
        title = null,
        text = {
            CustomAlert(
                imagen = image,
                nombreRiesgo = nombreRiesgo,
                descripcionRiesgo = descripcionRiesgo
            )
        },
        confirmButton ={
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                ReusableButton(
                    label = "Cerrar",
                    onClick = onConfirm,
                    modifier = Modifier
                        .width(100.dp)
                        .height(35.dp)
                )
            }
        },
    )
}

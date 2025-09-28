package com.example.main.Screens

import Navigation.AppScreens
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.Icons.Default
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.main.utils.theme.BoldOrange
import com.example.main.utils.theme.LightOrange
import com.example.main.utils.theme.Orange
import com.example.practica.R
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen (navController: NavController) {

var mostrarMenu by remember { mutableStateOf(false) }
    BottomSheetScaffold(
        sheetContent = {
            SheetContent()
        },
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
            if (mostrarMenu){
                MostrarMenu(onDismissRequest = {mostrarMenu = !mostrarMenu}, navController)
            }
            Image(
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                painter = painterResource(R.drawable.mapa),
                contentDescription = "Imagen de mapa"
            )
            Column(
                modifier = Modifier.padding(10.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FloatingActionButton(
                    modifier = Modifier.width(50.dp).height(50.dp),
                    shape = FloatingActionButtonDefaults.largeShape,
                    containerColor = BoldOrange,
                    onClick = {
                        //Mostrar el menu
                        mostrarMenu = !mostrarMenu

                    },
                ) {
                    Icon(
                        tint = Color.White,
                        imageVector = Icons.Default.Menu,
                        contentDescription = "boton de menu principal"
                    )
                }
                FloatingActionButton(
                    modifier = Modifier.width(50.dp).height(50.dp),
                    shape = FloatingActionButtonDefaults.largeShape,
                    containerColor = BoldOrange,
                    onClick = {
                        //Aca la idea es que se re-ubique en la posicion en la que esta usuario actual
                    },
                ) {
                    Icon(
                        tint = Color.White,
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "boton de menu principal"
                    )
                }
            }



        }
    }
}


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
                        modifier = Modifier.padding(top = 25.dp, bottom = 15.dp).align(Alignment.CenterHorizontally)
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
                   }

                }
            }
        }
    )
}

@Composable
fun OpcionesMenu (contentDescription: String, imageVector: ImageVector,  onClick: () -> Unit){

    Row (
        modifier = Modifier.padding(bottom = 15.dp, start = 15.dp, end = 15.dp).clickable (onClick = onClick).fillMaxWidth(),
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
        modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp).fillMaxHeight(0.6F)
    ){
        Row (
            Modifier.background(shape = RoundedCornerShape(15), color = Color.LightGray)
        ){
            var colorTrabajadores = if (trabajadores){Orange}else{Color.LightGray}
            var colorPeligros = if (peligros){Orange}else{Color.LightGray}
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.background(color = colorTrabajadores, shape = RoundedCornerShape(15))
                    .border(border = BorderStroke(1.dp, colorTrabajadores ), shape = RoundedCornerShape(15))
                    .weight(1f)
                    .height(25.dp)
                    .clickable {
                        trabajadores = true;
                        peligros= false
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
                modifier = Modifier.background(color = colorPeligros, shape = RoundedCornerShape(15))
                    .border(border = BorderStroke(1.dp, colorPeligros ), shape = RoundedCornerShape(15))
                    .weight(1f)
                    .height(25.dp)
                    .clickable {
                        peligros = true;
                        trabajadores= false
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
                        Spacer(Modifier.height(10.dp))
                        Row (
                            Modifier
                                .border(
                                    BorderStroke(1.dp, LightOrange),
                                    shape = RoundedCornerShape(10.dp)
                                )
                                .background(
                                    color =  Color.LightGray,
                                    shape = RoundedCornerShape(10.dp)
                                )

                                .padding(10.dp)
                                .height(40.dp)
                                .width(300.dp),
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
                                .width(300.dp),
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            verticalAlignment = Alignment.CenterVertically,

                            ){
                            Box(
                                Modifier
                                    .background(shape = CircleShape, color = Orange).height(30.dp).width(30.dp),


                                ){

                            }
                                Text(peligro)

                        }

                    }
                }
            }

    }
}
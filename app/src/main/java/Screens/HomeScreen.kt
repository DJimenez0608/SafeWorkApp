package com.example.main.Screens

import Navigation.AppScreens
import android.annotation.SuppressLint
import android.service.autofill.OnClickAction
import android.view.GestureDetector
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.Icons.Default
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentRecomposeScope
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.navOptions
import com.example.main.CompReusable.ReusableTopAppBar
import com.example.main.utils.theme.BoldOrange

import com.example.practica.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen (navController: NavController) {

var mostrarMenu by remember { mutableStateOf(false) }
    Scaffold(

    ) { innerPadding ->
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
package View

import Navigation.AppScreens
import android.Manifest
import android.R
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.main.CompReusable.ReusableButton
import com.example.main.CompReusable.ReusableTextField
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale

@OptIn(ExperimentalPermissionsApi::class)
@Composable
//nav
fun RiskCodeScreen(controller: NavController){

    val locationPermissions = rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)

    var riskZone by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        locationPermissions.launchPermissionRequest()
    }

    Column (
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,

        modifier = Modifier.fillMaxWidth().background(color = Color.White).fillMaxHeight().padding(35.dp)
    ) {
        Text(
            "Ingrese el codigo de la zona de construcción",
            textAlign = TextAlign.Center,
            style = TextStyle(
                color = Color.Black,
                fontSize = 32.sp,
                fontWeight = FontWeight.Medium

            )
        )
        Spacer(Modifier.height(40.dp))

        ReusableTextField(
            value = riskZone,
            onValueChange = {riskZone = it},
            contenido = "Código (6 digitos)"
        )
        Spacer(Modifier.height(60.dp))

        ReusableButton(
            label = "Buscar Zona" ,
            modifier = Modifier.width(180.dp),
            onClick = {
                if (locationPermissions.status.isGranted){
                    controller.navigate(AppScreens.HomeScreen.name)
                }



            }
        )
        ReusableButton(
            label = "Cerrar sesion",
            onClick = {
                controller.navigate(AppScreens.LogInScreen.name)
            },
            modifier = Modifier.width(180.dp)
        )
        Spacer(Modifier.height(10.dp))
        if(locationPermissions.status.shouldShowRationale){
            Column (
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Text("Es importante dar permisos de ubicacion ya que es escenciale para eluso optimo de la aplcación", textAlign = TextAlign.Center)
                TextButton(
                    onClick = {
                        locationPermissions.launchPermissionRequest()
                    }
                ) {
                    Text(
                        "DAR PERMISO",
                        color = Color.Blue,
                        textDecoration = TextDecoration.Underline
                    )
                }
            }

        }
    }

}
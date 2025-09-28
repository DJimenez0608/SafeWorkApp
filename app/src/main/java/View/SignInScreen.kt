package View

import Navigation.AppScreens
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Divider
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.navigation.NavController
import com.composables.icons.lucide.Camera
import com.composables.icons.lucide.Hotel
import com.composables.icons.lucide.Lucide
import com.example.main.CompReusable.ReusableButton
import com.example.main.CompReusable.ReusableTextField
import com.example.main.CompReusable.ReusableTopAppBar
import com.example.main.Location_Actions.permissionGranted
import com.example.main.utils.theme.LightOrange
import com.example.main.utils.theme.Orange
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import kotlinx.serialization.internal.throwMissingFieldException
import java.io.File


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun SignInScreen( controller: NavController){
    //CAMARA
    var imageUriCamera by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current
    val cameraUri = FileProvider.getUriForFile(
        context,
        "${context.packageName}.fileprovider",
        File(context.filesDir, "cameraPic.jpc")
    )

    val camera = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicture()
    ) {it->
        if (it){
            imageUriCamera = cameraUri
        }
    }

    //CAMERA PERMISSION
    val cameraPermission = rememberPermissionState(android.Manifest.permission.CAMERA)

    //GALERIA
    var imageUriGaleria by remember { mutableStateOf<Uri?>(null) }
    val galeria = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) {it ->
        imageUriGaleria = it
    }

    var nombre by remember { mutableStateOf("") }
    var cargo by remember { mutableStateOf("") }
    var cedula by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var celular by remember { mutableStateOf("") }
    var usuario by remember { mutableStateOf("") }
    var contraseña by remember { mutableStateOf("") }
    var confirmacionContraseña by remember { mutableStateOf("") }
    var tipoDoc by remember { mutableStateOf(false) }
    val opciones = listOf("C.c", "Pasaporte")
    var rationalText by remember { mutableStateOf("") }
    var seleccionado by remember { mutableStateOf(opciones[0]) }
    var image by remember { mutableStateOf<Uri?>(null) }



    Scaffold (
        topBar = { ReusableTopAppBar(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Orange),
            icon = Icons.Default.ArrowBack,
            contentDescription = "Volver a inicio de sesión",
            onClick = {controller.navigate(AppScreens.LogInScreen.name)},
            title = "Registro de nuevo usuario"
        ) }
    ){ innerPaddig ->
        Column (
            modifier = Modifier
                .padding(innerPaddig)
                .padding(horizontal = 22.dp, vertical = 20.dp)
                ,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            ReusableTextField(
                contenido = "Nombre",
                value = nombre,
                onValueChange = {nombre = it}

            )
            ReusableTextField(
                contenido = "Cargo",
                value = cargo,
                onValueChange = {cargo = it}

            )
            Spacer(Modifier.height(10.dp))
            Row (
                verticalAlignment = Alignment.CenterVertically,
            ){
                Box(
                    

                    modifier = Modifier
                        .clickable { tipoDoc = true }
                        .border(
                            width = 1.dp, // grosor
                            color = Color.Black, // color del borde
                            shape = RoundedCornerShape(6.dp) // forma opcional
                        )
                        .height(56.dp)

                ){

                    Text(seleccionado, modifier = Modifier.padding(20.dp))
                    DropdownMenu(
                        modifier = Modifier.clip(
                            RoundedCornerShape(6.dp)
                        ),

                        expanded = tipoDoc,
                        onDismissRequest = {tipoDoc = false}
                    ) {
                        opciones.forEach{
                                opciones-> DropdownMenuItem(
                            text = { Text(opciones) },
                            onClick = {
                                seleccionado = opciones
                                tipoDoc = false
                            }
                        )
                        }
                    }
                }

                Spacer(Modifier.width(10.dp))
                ReusableTextField(
                    contenido = "Cédula",
                    value = cedula,
                    onValueChange = {cedula = it}

                )

            }

            ReusableTextField(
                contenido = "Cooreo electronico",
                value = email,
                onValueChange = {email = it}

            )
            ReusableTextField(
                contenido = "Celular",
                value = celular,
                onValueChange = {celular = it}

            )
            ReusableTextField(
                contenido = "Usuario",
                value = usuario,
                onValueChange = {usuario = it}

            )
            ReusableTextField(
                contenido = "Contraseña",
                value = contraseña,
                onValueChange = {contraseña = it}

            )
        ReusableTextField(
            contenido = "Confirmar contraseña",
            value = confirmacionContraseña,
            onValueChange = {confirmacionContraseña = it}

        )
            Spacer(Modifier.height(20.dp))
            Box(
               modifier =  Modifier.border(
                    width = 1.dp,
                    shape = RoundedCornerShape(6.dp),
                    color = Color.Black,
                ).background(color= LightOrange)
            ){
                Column (
                    modifier = Modifier.padding(3.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    if(image!= null){
                        //imagen perfil-Escogida desde galeria
                    }
                    else {
                        Text("Foto de perfil", Modifier.padding(3.dp))
                        Divider(Modifier.width(100.dp))
                        Row (
                            horizontalArrangement = Arrangement.SpaceAround
                        ){
                            IconButton(
                                onClick = {

                                    if(cameraPermission.status.isGranted){
                                        camera.launch(cameraUri)
                                    }
                                    cameraPermission.launchPermissionRequest()

                                }
                            ) {
                                Icon(
                                    imageVector = Lucide.Camera,
                                    contentDescription = "Tomarse una foto de perfil"
                                )

                            }
                            VerticalDivider()
                            IconButton(
                                onClick = {
                                    galeria.launch("image/*")


                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Person ,
                                    contentDescription = "Tomarse una foto de perfil"
                                )

                            }
                        }

                    }
                }
            }
            if(cameraPermission.status.isGranted){
                rationalText = ""
            }
            else if (cameraPermission.status.shouldShowRationale){

                    rationalText = "Es importante dar permiso de camara para poder tomar foto de su rostro y poder identifiacerlo dentrod e la obra "


            }
            Text(rationalText, textAlign = TextAlign.Center)
            Spacer(Modifier.height(40.dp))
            ReusableButton(
                label = "Registrarme",
                onClick = {
                    controller.navigate(AppScreens.RiskCodeScreen.name)
                }

            )
        }

    }

}


@Composable
fun VerticalDivider(
    modifier: Modifier = Modifier,
    color: Color = Color.Gray,
    thickness: Dp = 1.dp
) {
    Box(
        modifier
            .height(50.dp)
            .width(thickness)
            .background(color = color)
    )
}






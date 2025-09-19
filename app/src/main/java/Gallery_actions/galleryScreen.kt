package com.example.main.Gallery_actions

import android.app.Activity
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.FileProvider
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import java.io.File

@OptIn(ExperimentalPermissionsApi::class)
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun GalleyScreen(navController: NavController){
    //CAMARA
    var imageUriCam by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current
    val cameraUri = FileProvider.getUriForFile(
        context,
        "${context.packageName}.fileprovider",
        File(context.filesDir, "cameraPic.jpg")
    )

    val camera = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicture()
    ) {it->
        if (it){
            imageUriCam = cameraUri
        }
    }

    val cameraermission = rememberPermissionState(android.Manifest.permission.CAMERA)
    //NO NECESITA PERMISOS YA QUE EL USUARIO ESTA ELIGIENDO QUE ES LO QUE QUIERE CONMPARTIR CONMIGO DESDE SU GALERIA
    //val gallñeryPermission = rememberPermissionState(android.Manifest.permission.READ_MEDIA_IMAGES)

    //GALERIA
    var imageUriGal by remember { mutableStateOf<Uri?>(null) }
    val gallery = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()) {it->
        imageUriGal = it
    }

    if(cameraermission.status.isGranted){

        Column (
            Modifier.fillMaxSize()
        ){
            Text("Camera permission Granted")
            Card {
                //LA FOTO Q UE SE TOMO
                LoadImag(imageUriCam)
            }
            Button(
                onClick = {
                    //SE ABRE LA CAMARA Y DE TOMA UNA FOTO
                    camera.launch(cameraUri)
                }
            ) {
                Text("Tomar foto")
            }
            Card {
                //LA FOTO Q UE SE TOMO
                LoadImag(imageUriGal)
            }
            Button(
                onClick = {
                    //SE ABRE LA CAMARA Y DE TOMA UNA FOTO
                    gallery.launch("image/*")
                }
            ) {
                Text("Tomar foto")
            }
        }

    }else
    {
        Column {
            val textToShow = if(cameraermission.status.shouldShowRationale){
                "The camera is important for this app. Please grant the permission."
            }else{
                "Camera permission required for this feature to be available. " +
                        "Please grant the permission"

            }
            Text(textToShow)
            Button(onClick = {cameraermission.launchPermissionRequest()}) {
                Text("requestpermition")
            }
        }
    }
}


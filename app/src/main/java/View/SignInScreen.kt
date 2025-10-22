package View

import Navigation.AppScreens
import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.composables.icons.lucide.Camera
import com.composables.icons.lucide.Lucide
import com.example.main.CompReusable.ReusableButton
import com.example.main.CompReusable.ReusableTextField
import com.example.main.CompReusable.ReusableTopAppBar
import com.example.main.ViewModel.UserAuthViewModel
import com.example.main.ViewModel.UserViewModel
import com.example.main.auth
import com.example.main.utils.theme.LightOrange
import com.example.main.utils.theme.Orange
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import java.io.File


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun SignInScreen( controller: NavController, viewModelDB : UserViewModel = viewModel (),
                  viewModelAuth : UserAuthViewModel = viewModel ()){

    val userDB by viewModelDB.userDBState.collectAsState()
    val userAuth by viewModelDB.userDBState.collectAsState()

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
    var contraseña by remember { mutableStateOf("") }
    var confirmacionContraseña by remember { mutableStateOf("") }
    var tipoDoc by remember { mutableStateOf(false) }
    val opciones = listOf("C.c", "Pasaporte")
    var rationalText by remember { mutableStateOf("") }
    var seleccionado by remember { mutableStateOf(opciones[0]) }



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
                onValueChange = {viewModelDB.updateNombre(it); nombre = it}

            )
            ReusableTextField(
                contenido = "Cargo",
                value = cargo,
                onValueChange = {viewModelDB.updateCargo(it);cargo = it}

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
                    onValueChange = {viewModelDB.updateCedula(it.toInt());cedula = it},
                    keyOption = KeyboardOptions(keyboardType = KeyboardType.Number)

                )

            }

            ReusableTextField(
                contenido = "Coreo electronico",
                value = email,
                onValueChange = {viewModelAuth.updatePassError(it); email = it},
                keyOption = KeyboardOptions(keyboardType = KeyboardType.Email)

            )
            ReusableTextField(
                contenido = "Celular",
                value = celular,
                onValueChange = {viewModelDB.updateCelular(it.toInt()) ; celular = it},
                keyOption = KeyboardOptions(keyboardType = KeyboardType.Number)

            )
            ReusableTextField(
                contenido = "Contraseña",
                visualTransformationi = PasswordVisualTransformation(),
                value = contraseña,
                onValueChange = {contraseña = it},
                keyOption = KeyboardOptions(keyboardType = KeyboardType.Password)

            )
        ReusableTextField(
            contenido = "Confirmar contraseña",
            visualTransformationi = PasswordVisualTransformation(),
            value = confirmacionContraseña,
            onValueChange = {
                if (contraseña == confirmacionContraseña){
                    viewModelAuth.updatePassClass(it)
                }
                    confirmacionContraseña = it},
            keyOption = KeyboardOptions(keyboardType = KeyboardType.Password)

        )
            Spacer(Modifier.height(20.dp))
            Box(
               modifier =  Modifier
                   .border(
                       width = 1.dp,
                       shape = RoundedCornerShape(6.dp),
                       color = Color.Black,
                   )
                   .background(color = LightOrange)
                   .height(100.dp)
                   .width(100.dp)
            ){
                Column (
                    modifier = Modifier.padding(3.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    if (imageUriCamera != null || imageUriGaleria != null) {
                        if (imageUriGaleria != null) {
                            LoadImage(imageUriGaleria)
                        } else {
                            LoadImage(imageUriCamera)
                        }
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
                    val valido =validateForm(viewModelAuth,email,contraseña)
                    if(valido){
                        RegistrarUser(email, contraseña,context)
                        controller.navigate(AppScreens.LogInScreen.name)
                    }


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


@Composable
fun LoadImage(imageUri: Uri?) {
    if (imageUri != null) {
        Image(
            painter = rememberAsyncImagePainter(model = imageUri),
            contentDescription = "Foto seleccionada",
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(6.dp))
                .border(1.dp, Color.Gray, RoundedCornerShape(6.dp)),
            contentScale = ContentScale.Crop
        )
    } else {
        Text("Cargando imagen...")
    }
}


fun RegistrarUser (email:String, password:String, context:Context){
    auth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Usuario creado correctamente
                val user = auth.currentUser
                Toast.makeText(context, "Usuario registrado", Toast.LENGTH_SHORT).show()

            } else {
                // Error al registrar
                Toast.makeText(
                    context,
                    "Error: ${task.exception?.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

}




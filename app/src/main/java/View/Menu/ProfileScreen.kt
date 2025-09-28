package com.example.main.Screens.Menu

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.main.CompReusable.ReusableButton
import com.example.main.CompReusable.ReusableTextField
import com.example.main.CompReusable.ReusableTopAppBar
import com.example.main.Gallery_actions.LoadImag

@Composable
fun ProfileScreen (navController: NavController){

    //GALERIA

    var imageUriGaleria by remember { mutableStateOf<Uri?>(null) }
    val galeria  = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) {it->
        imageUriGaleria = it
    }
    var celular by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var editar by remember{ mutableStateOf(false) }
    Scaffold (
        topBar = {
            ReusableTopAppBar(
                contentDescription = "Volver a pagina principal " ,
                title = "Perfil",
                onClick = {
                    navController.popBackStack()
                },
                icon = Icons.Default.ArrowBack,
                actions = true,
                actionsOnClickAction = {
                    editar = !editar
                }

                )
        }
    ){ innerPadding ->
        Column (
            modifier = Modifier.padding(innerPadding)
                .padding(vertical = 30.dp, horizontal = 20.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ){
            Box(
                contentAlignment = Alignment.BottomEnd,
                modifier = Modifier.size(180.dp)
            ) {

                Box(
                    modifier = Modifier
                        .size(180.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFDDDDDD)),
                    contentAlignment = Alignment.Center
                ) {
                    if (imageUriGaleria != null) {
                        Image(
                            painter = rememberAsyncImagePainter(imageUriGaleria),
                            contentDescription = "Usuario",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Usuario",
                            tint = Color.Black,
                            modifier = Modifier.size(100.dp)
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .offset(x = 5.dp, y = 5.dp)
                        .size(42.dp)
                        .background(Color(0xFFFF9800), CircleShape)
                        .border(1.dp, Color.White, CircleShape)
                        .clickable {
                            galeria.launch("image/*")

                        },
                    contentAlignment = Alignment.Center,

                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Agregar",
                        tint = Color.White,
                        modifier = Modifier.size(25.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))


            Text(
                text = "Juan Andres Rodriguez Salazar",
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Administrador de obra",
                fontSize = 14.sp,
                color = Color.Gray
            )
            Spacer(Modifier.height(60.dp))
            Column (horizontalAlignment = Alignment.Start){
                Text(
                    "Celular nuevo")
                ReusableTextField(
                    value = celular,
                    onValueChange = {celular = it},
                    contenido = "",
                    enable = editar
                )
                Spacer(Modifier.height(20.dp))
                Text(
                    "Correo electronico nuevo")
                ReusableTextField(
                    value = email,
                    onValueChange = {email = it},
                    contenido = "",
                    enable = editar
                )
            }
            Spacer(Modifier.height(40.dp))
            ReusableButton(
                label = "Actualizar",
                onClick = {}
            )
        }

    }

}
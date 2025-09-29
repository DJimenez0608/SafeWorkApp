package com.example.main.CompReusable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun CustomAlert (nombreRiesgo : String, descripcionRiesgo: String, imagen: ImageVector) {
    Box(

        modifier = Modifier.background(color = Color.White, shape = RoundedCornerShape(10.dp))


    ){
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier.width(180.dp),
            ){
                Text(
                    "Zona de riesgo detectada!",
                    fontWeight = FontWeight.Bold,
                    fontSize = 23.sp,
                    textAlign = TextAlign.Center

                    )
            }
            Spacer(Modifier.height(5.dp))
            //MIENTRAS TANTO, ACA IRIA UNA IMAGEN
            Icon(
                modifier = Modifier.size(100.dp),
                imageVector = imagen,
                contentDescription = "",
                tint = Color.Black


            )
            Spacer(Modifier.height(5.dp))
            Text(
                nombreRiesgo,
                fontWeight = FontWeight.SemiBold,
                fontSize = 17.sp,

                )
            Text(
                descripcionRiesgo
            )
            Spacer(Modifier.height(15.dp))
            Row (
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ){
                ReusableButton(
                    label = "Cerrar",
                    onClick = {},
                    modifier = Modifier.width(100.dp).height(35.dp)
                )

                ReusableButton(
                    label = "Detalles",
                    onClick = {},
                    modifier = Modifier.width(100.dp).height(35.dp)
                )
            }
        }
    }


}
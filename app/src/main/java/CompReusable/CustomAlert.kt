package com.example.main.CompReusable

import androidx.compose.foundation.Image
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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun CustomAlert (nombreRiesgo : String, descripcionRiesgo: String, imagen: Painter) {
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

            Image(
                contentDescription = "",
                painter = imagen,
                modifier = Modifier.size(100.dp),

            )
            Spacer(Modifier.height(10.dp))
            Text(
                nombreRiesgo,
                fontWeight = FontWeight.SemiBold,
                fontSize = 17.sp,
                textAlign = TextAlign.Center

                )
            Spacer(Modifier.height(10.dp))
            Text(
                descripcionRiesgo,
                textAlign = TextAlign.Center,
                fontSize = 12.sp,
            )

        }
    }


}
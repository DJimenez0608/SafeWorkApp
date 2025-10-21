package com.example.main.View

import Navigation.AppScreens
import com.example.main.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.main.utils.theme.LightOrange
import com.example.main.utils.theme.Orange
import kotlinx.coroutines.delay
import com.example.practica.R

@Composable
fun SplashScreen(navController: NavController) {
    LaunchedEffect(Unit) {
        delay(2500)
        if(auth.currentUser != null) {
            navController.navigate(AppScreens.HomeScreen.name)
        }else{
            navController.navigate(AppScreens.LogInScreen.name)
        }

    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    startY = 0.9F,
                    colors = listOf(
                        Orange, // Amarillo
                        Color.White        // Blanco
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo SafeWork",
                modifier = Modifier
                    .size(150.dp)
                    .padding(bottom = 20.dp)
            )

            // Nombre de la app
            Text(
                text = "SafeWork",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF212121)
            )
        }
    }
}

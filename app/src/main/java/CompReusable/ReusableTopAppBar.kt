package com.example.main.CompReusable

import android.service.autofill.OnClickAction
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.main.utils.theme.Orange

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReusableTopAppBar(actionsOnClickAction: () -> Unit = {}  ,actions: Boolean = false,textStyle: TextStyle = LocalTextStyle.current, textAlign: TextAlign? = null, modifier: Modifier = Modifier.background(color = Orange).fillMaxWidth(), title: String, contentDescription: String, icon: ImageVector, onClick: () -> Unit){
    Box(
        modifier = modifier,
    ){
        TopAppBar(
            actions = {
                if(actions) {
                    TextButton(
                        onClick = actionsOnClickAction
                    ) {
                        Text(
                            "Editar",
                            color = Color.White
                        )
                    }
                }

            },

            title = {
                Text(
                    title,
                    color = Color.White,
                    textAlign = textAlign,
                    style = textStyle,
                    fontSize = 22.sp
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Transparent,
            ),

            navigationIcon = {
                IconButton(
                    onClick = onClick
                ) {
                    Icon(
                        icon,
                        tint = Color.White,
                        contentDescription = contentDescription
                    )
                }
            },


        )
    }

}
package com.example.main.CompReusable

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ReusableTextField(enable : Boolean = true,contenido: String, value: String, onValueChange: (String) -> Unit, modifier: Modifier = Modifier.fillMaxWidth()) {
    OutlinedTextField(
        modifier = modifier,
        label = { Text(contenido) },
        value = value,
        onValueChange = onValueChange,
        enabled = enable
    )
}
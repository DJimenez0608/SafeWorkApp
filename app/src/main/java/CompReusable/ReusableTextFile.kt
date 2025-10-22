package com.example.main.CompReusable

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun ReusableTextField(visualTransformationi: VisualTransformation = VisualTransformation.None, keyOption: KeyboardOptions = KeyboardOptions.Default, enable : Boolean = true, contenido: String, value: String, onValueChange: (String) -> Unit, modifier: Modifier = Modifier.fillMaxWidth()) {
    OutlinedTextField(
        modifier = modifier,
        label = { Text(contenido) },
        value = value,
        onValueChange = onValueChange,
        enabled = enable,
        keyboardOptions = keyOption,
        visualTransformation = visualTransformationi,

    )
}
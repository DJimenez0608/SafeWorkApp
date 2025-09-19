package com.example.main.Gallery_actions

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import coil.compose.rememberAsyncImagePainter

@Composable
fun LoadImag (imageUri : Uri?){
    if(imageUri!=null){
        Image(
            painter = rememberAsyncImagePainter(imageUri),
            contentDescription = "Image selected"
        )
    }else{
        Text("Loading image...")
    }
}
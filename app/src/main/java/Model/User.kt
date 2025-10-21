package com.example.main.Model

data class UserAuth(
    val email : String = "",
    val password : String = "",
    val emailError:String = "",
    val passError : String = ""
)


data class UserDB(
    val nombre : String = "",
    val apellido : String = "",
    val authID : String = "",
    val cargo : String = "",
    val email : String = "",
    val celular : Int = 0,
    val fotoPerfil: String = "",
    val cedula : Int = 0,

)
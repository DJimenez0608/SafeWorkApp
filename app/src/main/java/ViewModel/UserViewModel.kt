package com.example.main.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.main.Model.UserAuth
import com.example.main.Model.UserDB
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class UserAuthViewModel : ViewModel() {
    val _user = MutableStateFlow<UserAuth>(UserAuth())
    val user = _user.asStateFlow()
    fun updateEmailClass(newEmail: String) {
        _user.value = _user.value.copy(email = newEmail)
    }

    fun updatePassClass(newPass: String) {
        _user.value = _user.value.copy(password = newPass)
    }

    fun updateEmailError(error: String) {
        _user.value = _user.value.copy(emailError = error)
    }

    fun updatePassError(error: String) {
        _user.value = _user.value.copy(passError = error)
    }
}

class UserViewModel : ViewModel() {
    val _userDB = MutableStateFlow<UserDB>(UserDB())
    val userDBState = _userDB.asStateFlow()

    // Actualizar el usuario completo
    fun setUser(user: UserDB) {
        _userDB.value = user
    }

    // Actualizar solo algunos campos
    fun updateNombre(nombre: String) {
        _userDB.value = _userDB.value.copy(nombre = nombre)
    }



    fun updateCargo(cargo: String) {
        _userDB.value = _userDB.value.copy(cargo = cargo)
    }

    fun updateCelular(celular: Int) {
        _userDB.value = _userDB.value.copy(celular = celular)
    }

    fun updateFotoPerfil(fotoPerfil: String) {
        _userDB.value = _userDB.value.copy(fotoPerfil = fotoPerfil)
    }

    fun updateCedula(cedula: Int) {
        _userDB.value = _userDB.value.copy(cedula = cedula)
    }

    fun updateAuthID(authID: String) {
        _userDB.value = _userDB.value.copy(authID = authID)
    }

    // Limpiar datos del usuario (por ejemplo, al cerrar sesión)
    fun clearUser() {
        _userDB.value = UserDB()
    }



}
package com.example.main.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.main.Model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

data class  UserState (
    val user : User?= null,
    val isLoading : Boolean = false,
    val error : String? = null,
)


class UserViewModel : ViewModel() {
    private val _uiUserState = MutableStateFlow(UserState())

    val uiUserState : StateFlow<UserState> = _uiUserState

    init {
        _uiUserState.value = UserState(
            user = User(
                nombre = "Nombre no registrado",
                id = 0,
                foto = ""
            )
        )
    }

    //ACTUALIZAR INFORMACION
    fun actualizarNombre(nuevoNombre : String){
        _uiUserState.update { currentState->
            currentState.copy(
                user = currentState.user?.copy(
                    nombre = nuevoNombre
                )
            )
        }
    }

    fun actualizarFotoPerfil (nuevaFoto : String){
        _uiUserState.update { currentState->
            currentState.copy(
                user = currentState.user?.copy(
                    foto = nuevaFoto
                )
            )
        }
    }
}
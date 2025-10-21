package com.example.main.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.main.Model.UserAuth
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
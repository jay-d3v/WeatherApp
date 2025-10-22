package com.example.weatherapp.presentation.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.local.UserEntity
import com.example.weatherapp.data.repository.UserRepository
import com.example.weatherapp.data.session.SessionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _loginState = MutableStateFlow<Boolean?>(null)
    val loginState: StateFlow<Boolean?> = _loginState

    private val _registerState = MutableStateFlow<Boolean?>(null)

    fun registerUser(name: String, email: String, password: String) {
        viewModelScope.launch {
            try {
                val user = UserEntity(username = name, email = email, password = password)
                userRepository.registerUser(user)
                _registerState.value = true
            } catch (e: Exception) {
                e.printStackTrace()
                _registerState.value = false
            }
        }
    }

    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            try {
                val loggedInUser = userRepository.loginUser(email, password)

                if (loggedInUser != null) {
                    SessionManager.currentUser = loggedInUser
                    _loginState.value = true
                } else {
                    _loginState.value = false
                }

            } catch (e: Exception) {
                e.printStackTrace()
                _loginState.value = false
            }
        }
    }
}
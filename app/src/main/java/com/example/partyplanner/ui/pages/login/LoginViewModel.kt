package com.example.partyplanner.ui.pages.login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.partyplanner.data.account.AccountService
import com.example.partyplanner.ui.elements.SendEmail
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException

/**
 * The login pages has been inspired by this tutorial: https://firebase.blog/posts/2022/05/adding-firebase-auth-to-jetpack-compose-app
 *
 * */
class LoginViewModel(
    private val loginService: AccountService,
    private val onSuccessfulLogin : () -> Unit
    ) : ViewModel() {
    var uiState = mutableStateOf(LoginUiState())
        private set

    fun onUsernameChange(newValue : String) {
        uiState.value = uiState.value.copy(email = newValue)
    }

    fun onPasswordChange(newValue : String) {
        uiState.value = uiState.value.copy(password = newValue)
    }
    fun login() {
        loginService.authenticate("hans@bilstrupandersen.dk", "123123") {
            var errorValue =""
            when (it) {
                null -> {
                    onSuccessfulLogin()
                }
                is IllegalArgumentException -> {
                    errorValue = "Indtast et brugernavn og en adgangskode"
                }
                is FirebaseAuthInvalidCredentialsException -> {
                    errorValue = "Forkert brugernavn eller adgangskode"
                }
            }

            uiState.value= uiState.value.copy(error = errorValue)
        }
    }

    fun createNewUser() {
    }
}
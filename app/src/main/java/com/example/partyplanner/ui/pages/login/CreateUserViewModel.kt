package com.example.partyplanner.ui.pages.login

import androidx.compose.runtime.mutableStateOf

class CreateUserViewModel(
    private val onCreateNewUser : () -> Unit
) {

    var uiState = mutableStateOf(LoginUiState())
        private set


    fun createNewUser() {
        onCreateNewUser()
    }

    fun onUsernameChange(newValue : String) {
        uiState.value = uiState.value.copy(email = newValue)
    }

    fun onPasswordChange(newValue : String) {
        uiState.value = uiState.value.copy(password = newValue)
    }

    fun onRepeatedPassword(newValue : String){
        uiState.value = uiState.value.copy(repeatedpassword = newValue)
    }


    fun onFullNameChange(newValue : String){
        uiState.value = uiState.value.copy(fullname = newValue)
    }

}


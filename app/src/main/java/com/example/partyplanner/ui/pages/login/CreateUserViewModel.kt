package com.example.partyplanner.ui.pages.login

import androidx.compose.runtime.mutableStateOf
import com.example.partyplanner.data.account.AccountService

class CreateUserViewModel(
    private val accountService : AccountService,
    private val onCreateNewUser : () -> Unit
) {

    var uiState = mutableStateOf(SignUpUiState())
        private set


    fun createNewUser() {
        val state = uiState.value
        // state.repeatedpassword.length < 8 is kept even though it is unnessecary since leaving it out
        // could confuse the unaware reader
        if(state.password != state.repeatedpassword ||
            state.email.isEmpty() ||
            state.fullname.isEmpty() ||
            state.password.length < 8 ||
            state.repeatedpassword.length < 8)  {
            toggleError()
            return
        }
        accountService.addNewUser(state.email, state.password) {
            if(it == null) {
                onCreateNewUser()
            } else {
                toggleSignUpError()
            }
        }
    }

    fun toggleError() {
        uiState.value = uiState.value.copy(error = !uiState.value.error)
    }

    fun toggleSignUpError() {
        uiState.value = uiState.value.copy(error = !uiState.value.signUpError)
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


package com.example.partyplanner.ui.pages.login

data class LoginUiState(
    val email : String = "",
    val password : String = "",
    val error : String = "",
)


data class SignUpUiState(
    val email : String = "",
    val password : String = "",
    val repeatedpassword : String = "",
    val fullname : String = "",
    val error : Boolean = false,
    val signUpError : Boolean = false
)
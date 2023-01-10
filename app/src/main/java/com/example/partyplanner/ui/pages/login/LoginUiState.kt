package com.example.partyplanner.ui.pages.login

data class LoginUiState(
    val email : String = "",
    val password : String = "",
    val repeatedpassword : String = "",
    val error : String = "",
    val fullname : String = ""
)

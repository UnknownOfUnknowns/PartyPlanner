package com.example.partyplanner.data.account

import kotlinx.coroutines.flow.Flow

interface AccountService {

    val currentUser: Flow<User>

    fun createAnonymousAccount(onResult: (Throwable?) -> Unit)
    fun authenticate(email :String, password : String, onResult: (Throwable?) -> Unit)
    fun linkAccount(email :String, password : String, onResult: (Throwable?) -> Unit)
}
package com.example.partyplanner.data.account

import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class AccountServiceImpl : AccountService {

    override val currentUser: Flow<User>
        get() = callbackFlow {
            val listener = FirebaseAuth.AuthStateListener { auth ->
                this.trySend(Firebase.auth.currentUser?.let { User(it.uid) } ?: User())
            }
            Firebase.auth.addAuthStateListener(listener)
            awaitClose {Firebase.auth.removeAuthStateListener(listener)}
        }



    override fun createAnonymousAccount(onResult: (Throwable?) -> Unit) {
        Firebase.auth.signInAnonymously().addOnCompleteListener {
            onResult(it.exception)
        }
    }

    override fun authenticate(email: String, password: String, onResult: (Throwable?) -> Unit) {
        if(email.isEmpty() || password.isEmpty()) {
            onResult(IllegalArgumentException())
            return
        }
        Firebase.auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { onResult(it.exception) }
    }

    override fun linkAccount(email: String, password: String, onResult: (Throwable?) -> Unit) {
        val credential = EmailAuthProvider.getCredential(email, password)
        Firebase.auth.currentUser!!.linkWithCredential(credential)
            .addOnCompleteListener { onResult(it.exception) }
    }

    override fun addNewUser(email: String, password: String, onResult: (Throwable?) -> Unit) {
        Firebase.auth.createUserWithEmailAndPassword(email,password).addOnSuccessListener {
            onResult(null)
        }.addOnFailureListener {
            onResult(Exception())
        }
    }
}
package com.example.partyplanner.data.party

import com.example.partyplanner.data.Guest
import com.example.partyplanner.data.HOST_VARIABLE
import com.example.partyplanner.data.PARTIES_COLLECTION
import com.example.partyplanner.data.account.AccountService
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.snapshots
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PartyServiceImpl(private val account : AccountService) : PartyService {
    override val parties: Flow<List<Party>>
        get() = currentCollection()
            .whereEqualTo(HOST_VARIABLE, Firebase.auth.uid)
            .snapshots()
            .map { snapshot -> snapshot.toObjects() }

    override suspend fun update(party: Party) {
        TODO("Not yet implemented")
    }

    override suspend fun addParty(party: Party, onResult: (Throwable?) -> Unit) {
        currentCollection().add(party).addOnSuccessListener { onResult(null) }
            .addOnFailureListener { onResult(Exception()) }
    }

    private fun currentCollection() : CollectionReference =
        Firebase.firestore.collection(PARTIES_COLLECTION)

}

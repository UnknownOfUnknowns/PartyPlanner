package com.example.partyplanner.data.party

import com.example.partyplanner.data.account.AccountService
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
            .whereEqualTo("host", "7zfOGdHuy4Xo3abJzVhUnJhFC8P2")
            .snapshots().map { snapshot -> snapshot.toObjects() }

    override suspend fun update(party: Party) {
        TODO("Not yet implemented")
    }

    fun currentCollection() : CollectionReference =
        Firebase.firestore.collection(PARTIES_COLLECTION)

    companion object {
        private const val PARTIES_COLLECTION = "parties"
    }
}
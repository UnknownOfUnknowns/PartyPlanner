package com.example.partyplanner.data.party

import com.example.partyplanner.data.GUESTS_COLLECTION
import com.example.partyplanner.data.HOST_VARIABLE
import com.example.partyplanner.data.PARTIES_COLLECTION
import com.example.partyplanner.data.WISH_LIST_NAME_VARIABLE
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
    override val hostParties: Flow<List<Party>>
        get() = partiesCollection()
            .whereEqualTo(HOST_VARIABLE, Firebase.auth.uid)
            .snapshots()
            .map { snapshot ->
                println(snapshot)
                snapshot.toObjects()
            }
    override val guestParties: Flow<List<Party>>
        get() = partiesCollection()
            .whereArrayContains("guests", Firebase.auth.uid ?: "")
            .snapshots()
            .map { snapshot ->
                println(snapshot)
                snapshot.toObjects()
            }




    override suspend fun update(party: Party) {
        TODO("Not yet implemented")
    }

    override suspend fun addParty(party: Party, onResult: (Throwable?) -> Unit) {
        Firebase.firestore.runTransaction {
            partiesCollection().add(party).addOnSuccessListener {
                partiesCollection().document(it.id).update(WISH_LIST_NAME_VARIABLE, party.partyName + " Ønskeliste")
            }
        }.addOnSuccessListener {
            onResult(null)
        }.addOnFailureListener {
            onResult(Exception())
        }
    }

    private fun partiesCollection() : CollectionReference =
        Firebase.firestore.collection(PARTIES_COLLECTION)


    private fun guestsCollection () : CollectionReference =
        Firebase.firestore.collection(GUESTS_COLLECTION)

}

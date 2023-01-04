package com.example.partyplanner.data.wish

import com.example.partyplanner.data.PARTIES_COLLECTION
import com.example.partyplanner.data.WISH_COLLECTION
import com.example.partyplanner.data.party.Party
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.snapshots
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class WishServiceImpl(private val firestore: FirebaseFirestore, @DocumentId private val partyId : String) : WishService {
    override val wishes: Flow<List<Wish>>
        get() = currentCollection()
            .snapshots().map { snapshot -> snapshot.toObjects() }


    override suspend fun update(wish: Wish) {
        TODO("Not Yet Implemented")
    }

    override suspend fun getWishes() : List<Wish> {
        return listOf(Wish())
    }

    override suspend fun addWish(wish: Wish, onResult: (Throwable?) -> Unit) {
        currentCollection()
            .add(wish)
            .addOnSuccessListener { onResult(null) }
            .addOnFailureListener { onResult(Exception()) }
    }

    override suspend fun deleteWish(wish: Wish, onResult: (Throwable?) -> Unit) {
        TODO("Not Yet implemented")
    }

    private fun currentCollection() : CollectionReference =
        firestore.collection(PARTIES_COLLECTION)
            .document(partyId)
            .collection(WISH_COLLECTION)
}


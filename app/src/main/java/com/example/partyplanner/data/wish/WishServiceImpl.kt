package com.example.partyplanner.data.wish

import com.example.partyplanner.data.PARTIES_COLLECTION
import com.example.partyplanner.data.WISH_COLLECTION
import com.example.partyplanner.data.WISH_LIST_NAME_VARIABLE
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.snapshots
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await

class WishServiceImpl(private val firestore: FirebaseFirestore,
                      @DocumentId private val partyId : String) : WishService {
    override val wishes: Flow<List<Wish>>
        get() = wishCollection()
            .snapshots().
            map { snapshot ->
                snapshot.toObjects()
            }


    override suspend fun update(wish: Wish) {
        TODO("Not Yet Implemented")
    }

    override suspend fun getWishes() : List<Wish> {
        return listOf(Wish())
    }

    override suspend fun addWish(wish: Wish, onResult: (Throwable?) -> Unit) {
        wishCollection()
            .add(wish)
            .addOnSuccessListener { ref ->
                if(wish.newImage != null) {

                }
            }
            .addOnFailureListener { onResult(Exception()) }
    }

    override suspend fun deleteWish(wish: Wish, onResult: (Throwable?) -> Unit) {
        TODO("Not Yet implemented")
    }

    override suspend fun getWishListName() : String{
        val snapshot = partiesCollection().document(partyId).get().await()
        return snapshot.data?.get(WISH_LIST_NAME_VARIABLE)?.toString() ?: ""
    }

    override suspend fun getWish(id: String, onResult: (Wish?) -> Unit) {
        wishDocument(id).get().addOnSuccessListener {
            onResult(it.toObject())
        }.addOnFailureListener {
            onResult(null)
        }
    }

    private fun wishCollection() : CollectionReference =
        firestore.collection(PARTIES_COLLECTION)
            .document(partyId)
            .collection(WISH_COLLECTION)

    private fun partiesCollection() : CollectionReference = firestore.collection(PARTIES_COLLECTION)

    private fun wishDocument(id : String) : DocumentReference = wishCollection().document(id)

}


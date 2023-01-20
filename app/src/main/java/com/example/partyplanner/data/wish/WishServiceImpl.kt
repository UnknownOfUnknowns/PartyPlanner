package com.example.partyplanner.data.wish

import android.graphics.Bitmap
import com.example.partyplanner.data.*
import com.example.partyplanner.data.imageLoader.ImageService
import com.example.partyplanner.data.imageLoader.ImageServiceImpl
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
                      @DocumentId private val partyId : String,
                        private val imageService : ImageService = ImageServiceImpl()) : WishService {
    override val wishes: Flow<List<Wish>>
        get() = wishCollection()
            .snapshots().
            map { snapshot ->
                try {
                    snapshot.toObjects()
                } catch (e : Exception) {
                    listOf()
                }
            }


    override suspend fun getWishes() : List<Wish> {
        return listOf(Wish())
    }

    override suspend fun addWish(wish: Wish, image : Bitmap?, onResult: (Throwable?) -> Unit) {
        var imageUrl = ""
        if (image != null) {
            imageService.saveImage(image, path = WISH_IMAGE_PATH) {
                imageUrl = it ?: ""
                wishCollection()
                    .add(wish.copy(image = imageUrl))
                    .addOnSuccessListener { onResult(null)}
                    .addOnFailureListener { onResult(Exception()) }
            }
        } else {
            wishCollection()
                .add(wish.copy(image = imageUrl))
                .addOnSuccessListener { onResult(null)}
                .addOnFailureListener { onResult(Exception()) }
        }

    }

    override suspend fun deleteWish(wish: Wish, onResult: (Throwable?) -> Unit) {
        wishCollection().document(wish.id).delete().addOnSuccessListener {
            onResult(null)
        }.addOnFailureListener {
            onResult(Exception())
        }
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

    override suspend fun changeReservationState(wish: Wish, onResult: (Throwable?) -> Unit) {
        wishCollection().document(wish.id).update(IS_WISH_RESERVED, !wish.reserved)
            .addOnSuccessListener {
                onResult(null)
            }
            .addOnFailureListener{
                onResult(Exception())
            }
    }

    private fun wishCollection() : CollectionReference =
        firestore.collection(PARTIES_COLLECTION)
            .document(partyId)
            .collection(WISH_COLLECTION)

    private fun partiesCollection() : CollectionReference = firestore.collection(PARTIES_COLLECTION)

    private fun wishDocument(id : String) : DocumentReference = wishCollection().document(id)

}


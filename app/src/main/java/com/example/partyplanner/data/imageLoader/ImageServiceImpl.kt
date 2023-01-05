package com.example.partyplanner.data.imageLoader

import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class ImageServiceImpl : ImageService {
    val storage = Firebase.storage

    override suspend fun loadImage(path: String, onResult: (ByteArray?) -> Unit) {
        val spaceRef = storage.reference.child(path)
        spaceRef.getBytes(Long.MAX_VALUE).addOnSuccessListener {
            onResult(it)
        }.addOnFailureListener {
            onResult(null)
        }
    }
}
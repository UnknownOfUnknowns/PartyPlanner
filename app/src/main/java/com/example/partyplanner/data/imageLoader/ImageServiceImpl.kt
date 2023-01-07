package com.example.partyplanner.data.imageLoader

import android.graphics.Bitmap
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
    private fun generateNewId(path : String) : String {
        return ""
    }
    override suspend fun saveImage(imageBitmap: Bitmap, path : String, onResult: (String?) -> Unit) {
        val spaceRef = storage.reference.child(path)

    }


}
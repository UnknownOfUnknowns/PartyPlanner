package com.example.partyplanner.data.imageLoader

import android.graphics.Bitmap
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.tasks.await
import java.io.ByteArrayOutputStream
import kotlin.random.Random

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

    /**
     * The funtion creates a new id from 0 to 1000000 this is sustainable for now but if the app
     * shall scale later this must be enhanced
     */
    private suspend fun generateNewId(path : String) : String {
        val ids = storage.reference.child(path).listAll().await().items.map { it.path }
        println(ids)
        var newId = ""
        do {
            newId = Random.nextInt(1000000).toString()
        } while (ids.contains("/$path/$newId.png"))


        return newId
    }
    override suspend fun saveImage(imageBitmap: Bitmap, path : String, onResult: (String?) -> Unit) {

        val id = generateNewId(path)
        val stream = ByteArrayOutputStream()
        imageBitmap.compress(Bitmap.CompressFormat.PNG, 90, stream)
        val spaceRef = storage.reference.child("$path/$id.png")

        spaceRef.putBytes(stream.toByteArray()).await()
        spaceRef.downloadUrl.addOnSuccessListener {
            onResult(it.toString())
        }.addOnFailureListener {
            onResult("")
        }

    }


}
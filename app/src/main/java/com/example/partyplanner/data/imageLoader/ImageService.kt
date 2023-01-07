package com.example.partyplanner.data.imageLoader

import android.graphics.Bitmap

interface ImageService {
    suspend fun loadImage(path : String, onResult: (ByteArray?) ->Unit)

    suspend fun saveImage(imageBitmap : Bitmap, path: String, onResult: (String?) -> Unit)
}
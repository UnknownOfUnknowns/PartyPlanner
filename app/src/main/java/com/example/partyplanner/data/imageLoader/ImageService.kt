package com.example.partyplanner.data.imageLoader

interface ImageService {
    suspend fun loadImage(path : String, onResult: (ByteArray?) ->Unit);
}
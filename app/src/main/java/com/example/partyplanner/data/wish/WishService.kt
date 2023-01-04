package com.example.partyplanner.data.wish

import kotlinx.coroutines.flow.Flow


interface WishService {
    val wishes: Flow<List<Wish>>

    suspend fun update(wish: Wish)
    suspend fun getWishes() : List<Wish>
    suspend fun addWish(wish: Wish, onResult: (Throwable?) -> Unit)

    suspend fun deleteWish(wish: Wish, onResult: (Throwable?) -> Unit)

    suspend fun getWishListName() : String
}
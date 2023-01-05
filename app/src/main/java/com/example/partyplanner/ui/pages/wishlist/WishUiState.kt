package com.example.partyplanner.ui.pages.wishlist

import android.graphics.Bitmap

data class WishUiState(
    val wishName: String = "",
    val price: Int = 0,
    val description : String = "",
    val link : String = "",
    val isReserved : Boolean = false,
    val img : Bitmap? = null
)


data class WishListUiState(
    val wishes : List<WishUiState> = listOf(),
    val wishListName : String = "",
    val newWish : WishUiState = WishUiState(),
    val addWish : Boolean = false,
    val isGuest : Boolean = false,
)
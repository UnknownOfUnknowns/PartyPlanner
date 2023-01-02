package com.example.partyplanner.ui.pages.wishlist

import androidx.annotation.DrawableRes

data class WishUiState(
    @DrawableRes val image: Int = 0,
    val wishName: String = "",
    val price: Int = 0,
    val description : String = "",
    val link : String = "",
    val isReserved : Boolean = false,
    val isGuest : Boolean = false
)


data class WishListUiState(
    val wishes : List<WishUiState> = listOf(),
    val wishListName : String = ""
)
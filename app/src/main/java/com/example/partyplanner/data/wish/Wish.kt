package com.example.partyplanner.data.wish

import androidx.annotation.DrawableRes
import com.example.partyplanner.ui.pages.wishlist.WishUiState
import com.google.firebase.firestore.DocumentId

data class Wish(
    @DocumentId val id : String = "",
    @DrawableRes val image: Int = 0,
    val wishName: String = "",
    val price: Int = 0,
    val description : String = "",
    val link : String = "",
    val isReserved : Boolean = false,
)

fun Wish.toUiState() : WishUiState = WishUiState(
    image = image,
    wishName= wishName,
    price = price,
    description = description,
    link = link,
    isReserved = isReserved
)


fun Wish.getFromUiState(state: WishUiState) : Wish = Wish(
    id = "",
    image = state.image,
    wishName= state.wishName,
    price = state.price,
    description = state.description,
    link = state.link,
    isReserved = state.isReserved
)
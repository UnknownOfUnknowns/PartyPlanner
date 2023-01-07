package com.example.partyplanner.data.wish

import android.graphics.Bitmap
import com.example.partyplanner.ui.pages.wishlist.WishUiState
import com.google.firebase.firestore.DocumentId

data class Wish(
    @DocumentId val id : String = "",
    val image: String = "",
    val wishName: String = "",
    val price: Int = 0,
    val description : String = "",
    val link : String = "",
    val isReserved : Boolean = false,
    val newImage : Bitmap? = null
)

fun Wish.toUiState() : WishUiState = WishUiState(
    wishName= wishName,
    price = price,
    description = description,
    link = link,
    isReserved = isReserved,
    imageLink = image,
    newImage = newImage
)


fun Wish.getFromUiState(state: WishUiState) : Wish = Wish(
    id = "",
    wishName= state.wishName,
    price = state.price,
    description = state.description,
    link = state.link,
    isReserved = state.isReserved,
    image = state.imageLink,
    newImage = state.newImage
)
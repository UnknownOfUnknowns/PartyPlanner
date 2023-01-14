package com.example.partyplanner.data.wish

import com.example.partyplanner.ui.pages.wishlist.WishUiState
import com.google.firebase.firestore.DocumentId

data class Wish(
    @DocumentId val id : String = "",
    val image: String = "",
    val wishName: String = "",
    val price: Int = 0,
    val description : String = "",
    val link : String = "",
    val reserved : Boolean = false
)

fun Wish.toUiState() : WishUiState = WishUiState(
    id = id,
    wishName= wishName,
    price = price,
    description = description,
    link = link,
    isReserved = reserved,
    imageLink = image
)


fun Wish.getFromUiState(state: WishUiState) : Wish = Wish(
    id = state.id,
    wishName= state.wishName,
    price = state.price,
    description = state.description,
    link = state.link,
    reserved = state.isReserved,
    image = state.imageLink
)
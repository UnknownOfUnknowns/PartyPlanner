package com.example.partyplanner.data.wish

import androidx.annotation.DrawableRes

data class Wish(
    @DrawableRes val image: Int = 0,
    val wishName: String = "",
    val price: Int = 0,
    val description : String = "",
    val link : String = "",
    val isReserved : Boolean = false,
    val isGuest : Boolean = false
)

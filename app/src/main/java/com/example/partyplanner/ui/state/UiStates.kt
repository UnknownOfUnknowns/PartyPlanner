package com.example.partyplanner.ui.state

import androidx.annotation.DrawableRes
import java.time.LocalDateTime

data class PartiesUiState(
    val parties: List<PartyUiState> = listOf(),
    var currentParty: PartyUiState = PartyUiState()
)

data class PartyUiState(
    val coreInfo: PartyCoreInfoUiState = PartyCoreInfoUiState()
)
enum class PartyTypes {
    NONE,
    WEDDING,
    BIRTHDAY,
    CONFIRMATION,
    BAPTISM,
    OTHER
}

data class PartyCoreInfoUiState(
    val name: String = "",
    val address: String = "",
    val zip: String = "",
    val city: String = "",
    val date: LocalDateTime = LocalDateTime.MAX,
    val partyType: PartyTypes = PartyTypes.NONE
)


enum class AttendanceState{
    ATTENDS,
    AWAITING,
    NOT_ATTENDING
}


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
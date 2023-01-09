package com.example.partyplanner.ui.state

import com.google.firebase.Timestamp

data class PartiesUiState(
    val parties: List<PartyUiState> = listOf(),
    var currentParty: PartyUiState = PartyUiState()
)

data class PartyUiState(
    val coreInfo: PartyCoreInfoUiState = PartyCoreInfoUiState()
)
enum class PartyType {
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
    val date: Timestamp = Timestamp.now(),
    val partyType: PartyType = PartyType.NONE,
    val partyHost: String = ""
)


enum class AttendanceState{
    ATTENDS,
    AWAITING,
    NOT_ATTENDING
}




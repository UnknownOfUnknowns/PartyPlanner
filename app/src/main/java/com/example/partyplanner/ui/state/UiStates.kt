package com.example.partyplanner.ui.state

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


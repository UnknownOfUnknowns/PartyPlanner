package com.example.partyplanner.ui.state

import java.time.LocalDateTime

data class PartiesUiState(
    val parties: List<PartyUiState> = listOf()
)

data class PartyUiState(
    val coreInfo: PartyCoreInfoUiState
)

data class PartyCoreInfoUiState(
    val name: String = "",
    val address: String = "",
    val zip: String = "",
    val city: String = "",
    val date: LocalDateTime
)
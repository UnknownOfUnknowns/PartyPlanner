package com.example.partyplanner.data.party

import com.example.partyplanner.ui.state.PartyCoreInfoUiState
import com.example.partyplanner.ui.state.PartyType
import com.google.firebase.firestore.DocumentId
import java.time.LocalDateTime

data class Party (
    @DocumentId val id : String = "",
    val host : String = "",
    val partyName : String = "",
    val name: String = "",
    val address: String = "",
    val zip: String = "",
    val city: String = "",
    val date: LocalDateTime = LocalDateTime.MAX,
    val partyType: PartyType = PartyType.NONE
)

fun Party.ToUiState() : PartyCoreInfoUiState = PartyCoreInfoUiState(
    name = name,
    address = address,
    zip = zip,
    date = date,
    partyType = partyType,
    city = city,
)

fun Party.getFromUiState(state: PartyCoreInfoUiState) : Party = Party(
    name = state.name,
    address = state.address,
    zip = state.zip,
    date = state.date,
    partyType = state.partyType,
    city = state.city
)
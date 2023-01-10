package com.example.partyplanner.data.party

import com.example.partyplanner.ui.state.PartyCoreInfoUiState
import com.example.partyplanner.ui.state.PartyType
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId

data class Party (
    @DocumentId val id : String = "",
    val host : String = "",
    val partyName : String = "",
    val wishListName : String = "",
    val name: String = "",
    val address: String = "",
    val zip: String = "",
    val city: String = "",
    val date: Timestamp = Timestamp.now(),
    val partyType: PartyType = PartyType.NONE
)

fun Party.toUiState() : PartyCoreInfoUiState = PartyCoreInfoUiState(
    name = name,
    address = address,
    zip = zip,
    date = date.toDate() ,
    partyType = partyType,
    city = city,
)

fun Party.getFromUiState(state: PartyCoreInfoUiState) : Party = Party(
    name = state.name,
    address = state.address,
    zip = state.zip,
    date = Timestamp(state.date),
    partyType = state.partyType,
    city = state.city
)
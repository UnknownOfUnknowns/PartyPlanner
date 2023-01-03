package com.example.partyplanner.data.party

import com.example.partyplanner.ui.state.PartyType
import java.time.LocalDateTime

data class Party (
    val partyName : String = "",
    val name: String = "",
    val address: String = "",
    val zip: String = "",
    val city: String = "",
    val date: LocalDateTime = LocalDateTime.MAX,
    val partyType: PartyType = PartyType.NONE
)
package com.example.partyplanner.ui.pages.partiesList

import androidx.lifecycle.ViewModel
import com.example.partyplanner.data.party.PartyService

class NewPartyViewModel(private val repository : PartyService) : ViewModel() {
    val parties = repository.parties

    fun createParty() {

    }
}
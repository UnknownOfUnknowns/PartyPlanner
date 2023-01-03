package com.example.partyplanner.ui.pages.partiesList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.partyplanner.data.party.Party
import com.example.partyplanner.data.party.PartyService
import com.example.partyplanner.ui.state.PartyCoreInfoUiState
import com.example.partyplanner.ui.state.PartyType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NewPartyViewModel(private val repository : PartyService) : ViewModel() {
    val parties = repository.parties
    private val _coreInfoUiState = MutableStateFlow(PartyCoreInfoUiState())
    val uiState: StateFlow<PartyCoreInfoUiState> = _coreInfoUiState.asStateFlow()

    fun createParty() {
        viewModelScope.launch {
            var party = Party(name = _coreInfoUiState.value.name)
            repository.addParty(party) {

            }
        }
    }

    fun updatePartyType(newPartyType: String){
        _coreInfoUiState.update { currentState ->
            currentState.copy(
                partyType = when(newPartyType) {
                    "Fødselsdag" -> PartyType.BIRTHDAY
                    "Bryllup" -> PartyType.WEDDING
                    "Konfirmation" -> PartyType.CONFIRMATION
                    "Dåb" -> PartyType.BAPTISM
                    else -> PartyType.OTHER
                }
            )
        }
    }
}
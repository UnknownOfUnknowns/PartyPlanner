package com.example.partyplanner.ui.pages.partiesList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.partyplanner.data.party.Party
import com.example.partyplanner.data.party.PartyService
import com.example.partyplanner.data.party.getFromUiState
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
            var party = Party().getFromUiState(_coreInfoUiState.value)
            party = party.copy(host = "7zfOGdHuy4Xo3abJzVhUnJhFC8P2")
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

    fun changeHostName(newHost: String){
        _coreInfoUiState.update { currentState ->
            currentState.copy(
                partyHost = newHost
            ) }
    }

    fun updateAddress(newAddress: String){
        _coreInfoUiState.update { currentState ->
            currentState.copy(
                address = newAddress
        ) }
    }

    fun updateZip(newZip: String){
        _coreInfoUiState.update { currentState ->
            currentState.copy(
                zip = newZip
        )}
    }

    fun updateName(newName: String){
        _coreInfoUiState.update { currentState ->
            currentState.copy(
                name = newName
            )
        }
    }

    fun updateCity(newCity: String){
        _coreInfoUiState.update { currentState ->
            currentState.copy(
                city = newCity
            )
        }
    }
}
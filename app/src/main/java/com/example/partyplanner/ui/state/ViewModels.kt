package com.example.partyplanner.ui.state

import androidx.lifecycle.ViewModel
import com.example.partyplanner.data.PartiesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class PartyViewModel(private val repository: PartiesRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(PartiesUiState())
    val uiState: StateFlow<PartiesUiState> = _uiState.asStateFlow()

    //for now it is just a dummy function that initializes data
    fun fetchParties() {
        _uiState.update {
            val newParties = repository.getParties()
            it.copy(parties = newParties)
        }
    }

    fun newParty() {
        val newParty = PartyUiState()
        repository.addParty(newParty)
        fetchParties()
        _uiState.value.currentParty = newParty
    }

    fun updateAddress(address: String){
        _uiState.update { currentState ->
            currentState.copy(
                parties = currentState.parties,
                currentParty = PartyUiState(
                    currentState.currentParty.coreInfo.copy(
                        name = currentState.currentParty.coreInfo.name,
                        address = address,
                        zip = currentState.currentParty.coreInfo.zip,
                        city = currentState.currentParty.coreInfo.city,
                        date = currentState.currentParty.coreInfo.date,
                        partyType = currentState.currentParty.coreInfo.partyType
                    )
                )
            )
        }
    }

    fun updateZip(zip: String) {
        _uiState.update { currentState ->
            currentState.copy(
                parties = currentState.parties,
                currentParty = PartyUiState(
                    currentState.currentParty.coreInfo.copy(
                        name = currentState.currentParty.coreInfo.name,
                        address = currentState.currentParty.coreInfo.address,
                        zip = zip,
                        city = currentState.currentParty.coreInfo.city,
                        date = currentState.currentParty.coreInfo.date,
                        partyType = currentState.currentParty.coreInfo.partyType
                    )
                )
            )
        }
    }

    fun updateCity(city: String) {
        _uiState.update { currentState ->
            currentState.copy(
                parties = currentState.parties,
                currentParty = PartyUiState(
                    currentState.currentParty.coreInfo.copy(
                        name = currentState.currentParty.coreInfo.name,
                        address = currentState.currentParty.coreInfo.address,
                        zip = currentState.currentParty.coreInfo.zip,
                        city = city,
                        date = currentState.currentParty.coreInfo.date,
                        partyType = currentState.currentParty.coreInfo.partyType
                    )
                )
            )
        }
    }

    fun updateName(name: String) {
        _uiState.update { currentState ->
            currentState.copy(
                parties = currentState.parties,
                currentParty = PartyUiState(
                    currentState.currentParty.coreInfo.copy(
                        name = name,
                        address = currentState.currentParty.coreInfo.address,
                        zip = currentState.currentParty.coreInfo.zip,
                        city = currentState.currentParty.coreInfo.city,
                        date = currentState.currentParty.coreInfo.date,
                        partyType = currentState.currentParty.coreInfo.partyType
                    )
                )
            )
        }
    }
}
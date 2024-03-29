package com.example.partyplanner.ui.pages.partiesList

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.partyplanner.data.party.Party
import com.example.partyplanner.data.party.PartyService
import com.example.partyplanner.data.party.getFromUiState
import com.example.partyplanner.ui.state.PartyCoreInfoUiState
import com.example.partyplanner.ui.state.PartyType
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.*

class NewPartyViewModel(private val repository : PartyService) : ViewModel() {
    val parties = repository.hostParties
    val guestParties = repository.guestParties
    val openInviteDialogOn = mutableStateOf(false)
    val partyToBeDeleted = mutableStateOf<Party?>(null)
    val guestId = mutableStateOf("")
    private val _coreInfoUiState = MutableStateFlow(PartyCoreInfoUiState())
    val uiState: StateFlow<PartyCoreInfoUiState> = _coreInfoUiState.asStateFlow()
init {
    viewModelScope.launch {
        repository.get()
    }
}
    fun createParty() {
        viewModelScope.launch {
            var party = Party().getFromUiState(_coreInfoUiState.value)
            party = party.copy(host = Firebase.auth.uid ?: "")
            repository.addParty(party) {

            }
        }
    }

    fun confirmDeletion() {
        viewModelScope.launch {
            val deletionParty = partyToBeDeleted.value
            if(deletionParty != null)
                repository.deleteParty(deletionParty) {
                    if(it == null){
                        toggleDelete(null)
                    }
                }
        }
    }

    fun updateGuestId(newId : String) {
        guestId.value = newId
    }

    fun connectUserToParty() {
        viewModelScope.launch {
            repository.relateGuestToParty(guestId.value) {
                if(it== null) {
                    toggleInviteOn()
                }
            }
        }
    }
    fun toggleDelete(newPartyToBeDeleted : Party?) {
        partyToBeDeleted.value = newPartyToBeDeleted
    }
    fun toggleInviteOn() {
        openInviteDialogOn.value = !openInviteDialogOn.value
        guestId.value = ""
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

    fun updateDate(newDate: Date) {
        _coreInfoUiState.update { currentState ->
            currentState.copy(date = newDate)
        }
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
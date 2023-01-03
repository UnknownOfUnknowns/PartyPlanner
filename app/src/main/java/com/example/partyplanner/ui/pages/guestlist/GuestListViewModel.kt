package com.example.partyplanner.ui.pages.guestlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.partyplanner.data.Guest
import com.example.partyplanner.data.GuestService
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class GuestListViewModel(private val repository: GuestService) : ViewModel() {
    private val _invitationUiState = MutableStateFlow(SendInvitationUiState())
    private val _guestToBeDeleted = MutableStateFlow(Guest())
    val uiState = combine(
        repository.guests,
        _invitationUiState,
        _guestToBeDeleted
    ) {
        guests, invitations, toBeDeleted ->
        GuestListUiState(
            guests = guests,
            invitationState = invitations,
            guestToBeDeleted = toBeDeleted
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = GuestListUiState()
    )

    init {
        viewModelScope.launch {
            repository.getGuests()
        }
    }


    fun setGuestToBeDeleted(guest : Guest) {
        _guestToBeDeleted.update {
            guest
        }
    }


    fun changeSendingMethod(newMethod: SendingMethod) {
        _invitationUiState.update { currentState ->
            currentState.copy(
                    sendingMethod = newMethod
            )
        }
    }

    fun changeSendingAddress(newAddress: String) {
        _invitationUiState.update { currentState ->
            currentState.copy(
                address = newAddress
            )
        }
    }
    fun changeGuestName(newName: String) {
        _invitationUiState.update { currentState ->
            currentState.copy(
                guest = newName
            )
        }
    }

    fun sendInvitation() {
        viewModelScope.launch {
        }
    }

    fun deleteGuest() {
        viewModelScope.launch {
            repository.deleteGuest(_guestToBeDeleted.value){
                _guestToBeDeleted.value = Guest()
            }
        }
    }


    fun addGuest() {
        viewModelScope.launch {
            var guest = Guest(name = _invitationUiState.value.guest)
            repository.addGuest(guest)
        }
    }
}
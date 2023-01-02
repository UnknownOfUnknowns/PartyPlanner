package com.example.partyplanner.ui.pages.guestlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.partyplanner.data.Guest
import com.example.partyplanner.data.GuestRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class GuestListViewModel(private val repository: GuestRepository) : ViewModel() {
    private val _invitationUiState = MutableStateFlow(SendInvitationUiState())
    val uiState = combine(
        repository.guests,
        _invitationUiState
    ) {
        guests, invitations ->
        GuestListUiState(
            guests = guests,
            invitationState = invitations
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
    fun addGuest() {
        viewModelScope.launch {
            var guest = Guest(name = _invitationUiState.value.guest)
            repository.addGuest(guest)
        }
    }
}
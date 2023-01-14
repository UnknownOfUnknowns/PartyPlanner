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
            guests = guests.sortedBy { it.attendanceState.ordinal },
            invitationState = invitations,
            guestToBeDeleted = toBeDeleted
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = GuestListUiState()
    )


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

    fun changeInviteOn(newStatus: Boolean) {

        _invitationUiState.update {
            it.copy(
                inviteOn = newStatus
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
            val state = _invitationUiState.value
            repository.addGuest(Guest(
                name = state.guest,
                contactAddress = state.address,
                sendingMethod = state.sendingMethod
            )) {
                if(it == null) {
                    changeGuestName("")
                    changeInviteOn(false)
                }
            }
        }
    }
}
package com.example.partyplanner.ui.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.partyplanner.data.Guest
import com.example.partyplanner.data.GuestRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GuestListViewModel(private val repository: GuestRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(GuestListUiState())
    val uiState : StateFlow<GuestListUiState> = _uiState.asStateFlow()
    val guests = repository.guests
    init {
        fetchGuests()
        viewModelScope.launch {
            repository.getGuests()
        }
    }

    fun fetchGuests(){
        _uiState.update { currentState ->
            currentState.copy(
                guests = listOf(GuestUiState("H.K.H Seier", AttendanceState.ATTENDS),
                                GuestUiState("H.K.H Hans Andersen", AttendanceState.ATTENDS),
                                GuestUiState("Kjeldgaard", AttendanceState.ATTENDS),
                                GuestUiState("Martin", AttendanceState.AWAITING),
                                GuestUiState("Thorbjørn", AttendanceState.AWAITING),
                                GuestUiState("Lucia", AttendanceState.NOT_ATTENDING),
                    )
            )
        }
    }

    fun changeSendingMethod(newMethod: SendingMethod) {
        _uiState.update { currentState ->
            val invitationState = currentState.invitationState
            currentState.copy(
                invitationState = SendInvitationUiState(
                    guest = invitationState.guest,
                    address = invitationState.address,
                    sendingMethod = newMethod
                )
            )
        }
    }

    fun changeSendingAddress(newAddress: String) {
        _uiState.update { currentState ->
            val invitationState = currentState.invitationState
            currentState.copy(
                invitationState = SendInvitationUiState(
                    guest = invitationState.guest,
                    address = newAddress,
                    sendingMethod = invitationState.sendingMethod
                )
            )
        }
    }
    fun changeGuestName(newName: String) {
        _uiState.update { currentState ->
            val invitationState = currentState.invitationState
            currentState.copy(
                invitationState = SendInvitationUiState(
                    guest = newName,
                    address = invitationState.address,
                    sendingMethod = invitationState.sendingMethod
                )
            )
        }
    }

    fun sendInvitation() {
        viewModelScope.launch {
            val invitationState = _uiState.value.invitationState
            repository.addGuest(Guest(id = "1233333", name = invitationState.guest, attendanceState = AttendanceState.ATTENDS))
        }
    }
}
package com.example.partyplanner.ui.state

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class GuestListViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(GuestListUiState())
    val uiState : StateFlow<GuestListUiState> = _uiState.asStateFlow()

    init {
        fetchGuests()
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

    }
}
package com.example.partyplanner.ui.guestpages

import androidx.lifecycle.ViewModel
import com.example.partyplanner.data.Guest
import com.example.partyplanner.ui.state.AttendanceState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class GuestMenuViewModel() : ViewModel() {

    private val _currentStateOfInvitation = MutableStateFlow(GuestMenuUiState())
    val uiState: StateFlow<GuestMenuUiState> = _currentStateOfInvitation.asStateFlow()

    fun attendanceState(newAttendingState: AttendanceState){
        _currentStateOfInvitation.update { currentState -> currentState.copy(attendingState = newAttendingState) }
    }

    fun changePartyDescription(){

    }


}
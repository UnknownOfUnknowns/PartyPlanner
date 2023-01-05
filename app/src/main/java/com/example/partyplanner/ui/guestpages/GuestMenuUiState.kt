package com.example.partyplanner.ui.guestpages

import com.example.partyplanner.ui.state.AttendanceState

data class GuestMenuUiState(
    val attendingState: AttendanceState = AttendanceState.AWAITING,
    val eventName: String = "",
    val eventDescription: String = ""
)
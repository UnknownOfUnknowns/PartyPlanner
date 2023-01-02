package com.example.partyplanner.ui.pages.guestlist

import com.example.partyplanner.ui.state.AttendanceState

data class GuestListUiState(
    val guests: List<GuestUiState> = listOf(),
    val invitationState : SendInvitationUiState = SendInvitationUiState()
)

val GuestListUiState.totalInvites: Int get() = guests.size

data class GuestUiState(
    val name :String = "",
    val attendanceState: AttendanceState = AttendanceState.AWAITING,
)

enum class SendingMethod{
    SMS,
    E_BOKS,
    EMAIL
}

data class SendInvitationUiState(
    val guest : String = "",
    val sendingMethod : SendingMethod = SendingMethod.SMS,
    val address: String = ""
)


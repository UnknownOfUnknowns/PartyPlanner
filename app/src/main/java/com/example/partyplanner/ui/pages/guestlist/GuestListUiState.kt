package com.example.partyplanner.ui.pages.guestlist

import com.example.partyplanner.data.Guest

data class GuestListUiState(
    val guests: List<Guest> = listOf(),
    val invitationState : SendInvitationUiState = SendInvitationUiState()
)

val GuestListUiState.totalInvites: Int get() = guests.size

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


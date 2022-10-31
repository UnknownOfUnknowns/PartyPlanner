package com.example.partyplanner.ui.state

import java.time.LocalDateTime

data class PartiesUiState(
    val parties: List<PartyUiState> = listOf(),
    var currentParty: PartyUiState = PartyUiState()
)

data class PartyUiState(
    val coreInfo: PartyCoreInfoUiState = PartyCoreInfoUiState()
)
enum class PartyTypes {
    NONE,
    WEDDING,
    BIRTHDAY,
    CONFIRMATION,
    BAPTISM,
    OTHER
}

data class PartyCoreInfoUiState(
    val name: String = "",
    val address: String = "",
    val zip: String = "",
    val city: String = "",
    val date: LocalDateTime = LocalDateTime.MAX,
    val partyType: PartyTypes = PartyTypes.NONE
)


enum class AttendanceState{
    ATTENDS,
    AWAITING,
    NOT_ATTENDING
}
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


data class WishUiState(
    val image: Int = 0,
    val wishName: String = "",
    val price: Int = 0,
    val description : String = "",
    val link : String = ""
)


data class WishListUiState(
    val wishes : List<WishUiState> = listOf(),
    val wishListName : String = ""
)
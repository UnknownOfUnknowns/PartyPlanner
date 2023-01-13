package com.example.partyplanner.guestlisttest

import com.example.partyplanner.data.Guest
import com.example.partyplanner.data.GuestService
import com.example.partyplanner.ui.state.AttendanceState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow

class GuestRepoStub : GuestService {
    private val stubGuests = mutableListOf(
        Guest(
            name = "Hans",
            attendanceState = AttendanceState.ATTENDS,
        ),
        Guest(
            name = "Seier",
            attendanceState = AttendanceState.AWAITING,
        )
    )
    override val guests: Flow<List<Guest>>
        get() = listOf(stubGuests).asFlow()
    override suspend fun getGuests(): List<Guest> {
        return stubGuests
    }

    override suspend fun addGuest(guest: Guest, onResult: (Throwable?) -> Unit) {
        onResult(null)
    }

    override suspend fun deleteGuest(guest: Guest, onResult: (Throwable?) -> Unit) {
        stubGuests.remove(guest)
        onResult(null)
    }
}
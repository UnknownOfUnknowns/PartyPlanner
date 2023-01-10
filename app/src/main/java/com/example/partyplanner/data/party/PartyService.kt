package com.example.partyplanner.data.party

import com.example.partyplanner.ui.state.AttendanceState
import kotlinx.coroutines.flow.Flow

interface PartyService {

    val hostParties : Flow<List<Party>>

    val guestParties : Flow<List<Party>>

    suspend fun get()

    suspend fun update(party: Party)

    suspend fun addParty(party: Party, onResult: (Throwable?) -> Unit)

    suspend fun changeAttendanceState(partyId : String, newState : AttendanceState, onResult: (Throwable?) -> Unit)

    suspend fun getGuestPartyInfo(partyId: String, onResult: (GuestPartyInfo) -> Unit)

}

package com.example.partyplanner.data.party

import kotlinx.coroutines.flow.Flow

interface PartyService {

    val hostParties : Flow<List<Party>>

    val guestParties : Flow<List<Party>>

    suspend fun update(party: Party)

    suspend fun addParty(party: Party, onResult: (Throwable?) -> Unit)

}

package com.example.partyplanner.data

import android.content.ContentValues.TAG
import android.util.Log
import com.example.partyplanner.ui.state.AttendanceState
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.snapshots
import com.google.firebase.firestore.ktx.toObjects
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

data class Guest(
    @DocumentId val id : String = "",
    val name: String = "",
    val attendanceState: AttendanceState = AttendanceState.AWAITING
)

interface GuestService{
    val guests: Flow<List<Guest>>
    suspend fun getGuests() : List<Guest>
    suspend fun addGuest(guest: Guest, onResult: (Throwable?) -> Unit)
    suspend fun deleteGuest(guest: Guest, onResult: (Throwable?) ->Unit)
}



class GuestRepository(private val firestore: FirebaseFirestore, @DocumentId private val partyId: String) : GuestService {

    override val guests: Flow<List<Guest>>
        get() = currentCollection().snapshots().map { snapshot -> snapshot.toObjects() }



    override suspend fun getGuests(): List<Guest> {
        return listOf(Guest())
    }



    override suspend fun addGuest(guest: Guest, onResult: (Throwable?) -> Unit) {
        currentCollection().add(guest).addOnSuccessListener { onResult(null) }
            .addOnFailureListener { onResult(Exception()) }
    }

    override suspend fun deleteGuest(guest: Guest, onResult: (Throwable?) ->Unit) {
        currentCollection().document(guest.id).delete()
            .addOnSuccessListener { onResult(null) }
            .addOnFailureListener { onResult(Exception()) }
    }

    private fun currentCollection(): CollectionReference =
        firestore.collection(PARTIES_COLLECTION)
            .document(partyId)
            .collection(GUESTS_COLLECTION)


}
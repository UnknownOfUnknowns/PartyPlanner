package com.example.partyplanner.data

import com.example.partyplanner.data.invitations.InvitationSender
import com.example.partyplanner.ui.pages.guestlist.SendingMethod
import com.example.partyplanner.ui.state.AttendanceState
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.snapshots
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

data class Guest(
    @DocumentId val id : String = "",
    val name: String = "",
    val partyRef : String = "",
    val guestId : String = "",
    val attendanceState: AttendanceState = AttendanceState.AWAITING,
    val contactAddress : String = "",
    val sendingMethod: SendingMethod = SendingMethod.EMAIL
)

interface GuestService{
    val guests: Flow<List<Guest>>
    suspend fun getGuests() : List<Guest>
    suspend fun addGuest(guest: Guest, onResult: (Throwable?) -> Unit)
    suspend fun deleteGuest(guest: Guest, onResult: (Throwable?) ->Unit)
}



class GuestServiceImpl(private val firestore: FirebaseFirestore, @DocumentId private val partyId: String) : GuestService {

    override val guests: Flow<List<Guest>>
        get() = currentCollection()
            .whereEqualTo("partyRef", partyId)
                .snapshots()
                .map { snapshot ->
                    println(snapshot)
                    snapshot.toObjects()
                }



    override suspend fun getGuests(): List<Guest> {
        return listOf(Guest())
    }



    override suspend fun addGuest(guest: Guest, onResult: (Throwable?) -> Unit) {
        currentCollection().add(guest.copy(partyRef = partyId))
            .addOnSuccessListener {
                InvitationSender().send(guest.copy(id = it.id))
                onResult(null)
            }
            .addOnFailureListener {
                onResult(Exception())
            }

    }

    override suspend fun deleteGuest(guest: Guest, onResult: (Throwable?) ->Unit) {
        currentCollection().document(guest.id).delete()
            .addOnSuccessListener {
                firestore.collection(PARTIES_COLLECTION).document(guest.partyRef).update(
                    GUESTS_FIELD, FieldValue.arrayRemove(Firebase.auth.uid))
                onResult(null)
            }
            .addOnFailureListener { onResult(Exception()) }
    }



    private fun currentCollection(): CollectionReference =
        firestore.collection(GUESTS_COLLECTION)



}
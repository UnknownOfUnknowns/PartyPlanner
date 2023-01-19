package com.example.partyplanner.data.party

import com.example.partyplanner.data.*
import com.example.partyplanner.ui.state.AttendanceState
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.snapshots
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import java.util.*

class PartyServiceImpl : PartyService {
    override val hostParties: Flow<List<Party>>
        get() = partiesCollection()
            .whereEqualTo(HOST_VARIABLE, Firebase.auth.uid)
            .snapshots()
            .map { snapshot ->
                println(snapshot)
                snapshot.toObjects()
            }
    override val guestParties: Flow<List<Party>>
        get() = partiesCollection()
            .whereArrayContains("guests", Firebase.auth.uid ?: "")
            .snapshots()
            .map { snapshot ->
                println(snapshot)
                snapshot.toObjects()
            }





    override suspend fun get() {
        Firebase.firestore.collectionGroup("guests").get().addOnSuccessListener { tt ->
            println(tt)
            tt.forEach {
                println(it)
            }
        }
    }

    override suspend fun addParty(party: Party, onResult: (Throwable?) -> Unit) {
        Firebase.firestore.runTransaction {
            partiesCollection().add(party).addOnSuccessListener {
                partiesCollection().document(it.id).update(WISH_LIST_NAME_VARIABLE, party.partyName + " Ønskeliste")
            }
        }.addOnSuccessListener {
            onResult(null)
        }.addOnFailureListener {
            onResult(Exception())
        }
    }

    override suspend fun changeAttendanceState(partyId: String, newState : AttendanceState, onResult: (Throwable?) -> Unit) {
        guestsCollection()
            .whereEqualTo("partyRef", partyId)
            .whereEqualTo("guestId", Firebase.auth.uid).get().addOnSuccessListener { snapshot ->
                if(snapshot.size() > 0) {
                    snapshot.forEach {
                        guestsCollection().document(it.id).update("attendanceState", newState).addOnSuccessListener {
                            onResult(null)
                        }
                    }
                }
            }
    }

    override suspend fun getGuestPartyInfo(partyId: String, onResult: (GuestPartyInfo) -> Unit){
        val guest = guestsCollection().whereEqualTo("partyRef", partyId)
            .whereEqualTo("guestId", Firebase.auth.uid).get().await().toObjects<Guest>()
        val attendanceState = guest.getOrNull(0)?.attendanceState ?: AttendanceState.AWAITING
        partiesCollection().document(partyId).get().addOnSuccessListener {

            val partyObject = it.toObject<Party>()
            var resultObject = it.toObject<GuestPartyInfo>() ?: GuestPartyInfo()
            if (partyObject != null) {
                resultObject = resultObject.copy(eventDescription = createInviteDescription(partyObject), attendanceState = attendanceState)
            }
            onResult(resultObject)
        }.addOnFailureListener {
            onResult(GuestPartyInfo())
        }

    }
    private fun deleteGuestsFromParty(id : String) {
        guestsCollection().whereEqualTo(PARTY_REFERENCE, id).get().addOnSuccessListener { snapshot ->
            snapshot.forEach {
                guestsCollection().document(it.id).delete()
            }
        }
    }
    override suspend fun deleteParty(party: Party, onResult: (Throwable?) -> Unit) {
        partiesCollection().document(party.id).delete().addOnSuccessListener {
            onResult(null)
            deleteGuestsFromParty(party.id)
        }.addOnFailureListener{
            onResult(Exception())
        }
    }

    override suspend fun relateGuestToParty(
        guestDocumentId: String,
        onResult: (Throwable?) -> Unit
    ) {
        val guestDocument = guestsCollection().document(guestDocumentId)
        guestDocument.update(GUEST_ID, Firebase.auth.uid)
        guestDocument.get().addOnSuccessListener {
            val party = it.data?.get(PARTY_REFERENCE)
            if(party != null && party is String){
                Firebase.firestore.collection(PARTIES_COLLECTION).document(party).update(GUESTS_FIELD, FieldValue.arrayUnion(Firebase.auth.uid))
                    .addOnSuccessListener {
                        onResult(null)
                    }
                    .addOnFailureListener{
                        onResult(Exception())
                    }
            } else {
                onResult(Exception())
            }
        }
    }

    private fun partiesCollection() : CollectionReference =
        Firebase.firestore.collection(PARTIES_COLLECTION)


    private fun guestsCollection () : CollectionReference =
        Firebase.firestore.collection(GUESTS_COLLECTION)


    companion object InviteDescriptionGenerator {
        fun createInviteDescription(party : Party) : String {
            val date = party.date.toDate()
            return "Du er hermed inviteret til ${party.name}\nFesten afholdes på ${party.address} ${party.zip} ${party.city} den ${formatDate(date)}"
        }

        private fun formatDate(date : Date) : String{
            val calendar = Calendar.getInstance()
            calendar.time = date
            return "${calendar[Calendar.DAY_OF_MONTH]}/${calendar[Calendar.MONTH]+1}/${calendar[Calendar.YEAR]}"
        }
    }

}

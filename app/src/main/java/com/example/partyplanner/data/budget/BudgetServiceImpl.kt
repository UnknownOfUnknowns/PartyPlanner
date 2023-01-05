package com.example.partyplanner.data.budget

import com.example.partyplanner.data.BUDGET_COLLECTION
import com.example.partyplanner.data.PARTIES_COLLECTION
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.snapshots
import com.google.firebase.firestore.ktx.toObjects
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class BudgetServiceImpl(private val firestore: FirebaseFirestore, @DocumentId private  val partyId : String) : BudgetService {
    override val budgets: Flow<List<Budget>>
        get() = budgetCollection()
            .snapshots().map { snapshot -> snapshot.toObjects() }

    override suspend fun addBudgetItem(budget: Budget, onResult: (Throwable?) -> Unit) {
        budgetCollection()
            .add(budget)
            .addOnSuccessListener { onResult(null) }
            .addOnFailureListener { onResult(Exception()) }
    }

    private fun budgetCollection() : CollectionReference =
        firestore.collection(PARTIES_COLLECTION)
            .document(partyId)
            .collection(BUDGET_COLLECTION)

}

// Her skal der tilføjes current budget collection og så videre, så kan jeg
// fortsætte med at lave på budget-page efterfølgende.
package com.example.partyplanner.data.budget

import com.example.partyplanner.data.*
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.snapshots
import com.google.firebase.firestore.ktx.toObjects
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class BudgetServiceImpl(private val firestore: FirebaseFirestore, @DocumentId private  val partyId : String) : BudgetService {
    override val budgets: Flow<List<Budget>>
        get() = budgetCollection()
            .snapshots().map { snapshot ->
                try {
                    snapshot.toObjects()
                } catch (e : Exception) {
                    listOf()
                }
            }

    override suspend fun addBudgetItem(budget: Budget, onResult: (Throwable?) -> Unit) {
        budgetCollection()
            .add(budget)
            .addOnSuccessListener { onResult(null) }
            .addOnFailureListener { onResult(Exception()) }
    }

    override suspend fun setBudgetMax(newMax : Int, onResult: (Throwable?) -> Unit) {
        currentPartyDocument()
            .update(MAX_BUDGET, newMax)
            .addOnSuccessListener { onResult(null) }
            .addOnFailureListener { onResult(Exception()) }
    }

    override suspend fun getBudgetMax(onResult: (Int) -> Unit){
        currentPartyDocument().get().addOnSuccessListener { snapshot ->
            val data = snapshot.data
            val maxBudget = data?.get(MAX_BUDGET)
            when(maxBudget) {
                is Long -> onResult(maxBudget.toInt())
                is Int -> onResult(maxBudget)
                else -> onResult(0)
            }
        }.addOnFailureListener {
            onResult(0)
        }

    }

    override suspend fun deleteBudget(budget: Budget, onResult: (Throwable?) -> Unit) {
        budgetCollection().document(budget.id).delete().addOnSuccessListener {
            onResult(null)
        }.addOnFailureListener {
            onResult(Exception())
        }
    }

    override suspend fun update(newNote : String, newPrice : Int, budget: Budget, onResult: (Throwable?) -> Unit) {
        budgetCollection().whereEqualTo(BUDGET_NAME, budget.budgetName).get().addOnSuccessListener {
            it.forEach { doc ->
                budgetDocument(doc.id).update(
                    mapOf(
                        BUDGET_NOTE to newNote,
                        BUDGET_PRICE to newPrice
                    )
                )
                    .addOnSuccessListener {
                        onResult(null)
                    }
                    .addOnFailureListener{
                        onResult(Exception())
                    }
            }
        }


    }

    private fun budgetCollection() : CollectionReference =
        firestore.collection(PARTIES_COLLECTION)
            .document(partyId)
            .collection(BUDGET_COLLECTION)

    private fun currentPartyDocument() : DocumentReference =
        firestore.collection(PARTIES_COLLECTION)
            .document(partyId)

    private fun budgetDocument(id : String) : DocumentReference =
        budgetCollection().document(id)
}


// Her skal der tilføjes current budget collection og så videre, så kan jeg
// fortsætte med at lave på budget-page efterfølgende.
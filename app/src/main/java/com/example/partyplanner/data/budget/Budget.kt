package com.example.partyplanner.data.budget

import com.example.partyplanner.ui.pages.budget.BudgetElementUiState
import com.google.firebase.firestore.DocumentId

data class Budget(
    @DocumentId val id : String = "",
    val budgetName: String = "",
    val budgetPrice: Int = 0,
    val budgetNote: String = "",
)

fun Budget.toUiState() : BudgetElementUiState = BudgetElementUiState(
    id = id,
    budgetName = budgetName,
    budgetPrice = budgetPrice,
    budgetNote = budgetNote,
)


fun Budget.getFromUiState(state: BudgetElementUiState) : Budget = Budget(
    id = state.id,
    budgetName = state.budgetName,
    budgetPrice = state.budgetPrice,
    budgetNote = state.budgetNote
)
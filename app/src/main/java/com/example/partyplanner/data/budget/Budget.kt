package com.example.partyplanner.data.budget

import com.example.partyplanner.ui.pages.budget.BudgetElementUiState

data class Budget(
    val budgetName: String = "",
    val budgetPrice: Int = 0,
    val budgetNote: String = "",
)

fun Budget.toUiState() : BudgetElementUiState = BudgetElementUiState(
    budgetName = budgetName,
    budgetPrice = budgetPrice,
    budgetNote = budgetNote,
)


fun Budget.getFromUiState(state: BudgetElementUiState) : Budget = Budget(
    budgetName = state.budgetName,
    budgetPrice = state.budgetPrice,
    budgetNote = state.budgetNote
)
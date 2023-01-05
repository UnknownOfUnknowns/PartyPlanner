package com.example.partyplanner.data.budget

import com.example.partyplanner.ui.pages.budget.BudgetElementUiState

data class Budget(
    val budgetName: String = "",
    val budgetPrice: Int = 0
)

fun Budget.toUiState() : BudgetElementUiState = BudgetElementUiState(
    budgetName = budgetName,
    budgetPrice = budgetPrice,
)


fun Budget.getFromUiState(state: BudgetElementUiState) : Budget = Budget(
    budgetName = state.budgetName,
    budgetPrice = state.budgetPrice,
)
package com.example.partyplanner.data.budget

import com.example.partyplanner.ui.pages.budget.BudgetUiState

data class Budget(
    val budgetName: String = "",
    val budgetPrice: Int = 0,
    val budgetMax: Int = 0,
)

fun Budget.toUiState() : BudgetUiState = BudgetUiState(
    budgetName = budgetName,
    budgetPrice = budgetPrice,
    budgetMaxPrice = budgetMax
)


fun Budget.getFromUiState(state: BudgetUiState) : Budget = Budget(
    budgetName = state.budgetName,
    budgetPrice = state.budgetPrice,
    budgetMax = state.budgetMaxPrice,

)
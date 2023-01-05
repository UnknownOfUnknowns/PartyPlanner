package com.example.partyplanner.ui.pages.budget


data class BudgetUiState(
    val budgetName: String = "",
    val budgetPrice: Int = 0,
    val budgetMaxPrice: Int = 0,

)

data class BudgetListUiState(
    val budgets : List<BudgetUiState> = listOf(),
    val newBudget : BudgetUiState = BudgetUiState(),
    val addBudgetStatus: Boolean = false,
    val addTotalBudgetStatus: Boolean = false,

    )
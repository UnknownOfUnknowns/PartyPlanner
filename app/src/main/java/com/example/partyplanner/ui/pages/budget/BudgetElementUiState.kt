package com.example.partyplanner.ui.pages.budget


data class BudgetElementUiState(
    val budgetName: String = "",
    val budgetPrice: Int = 0,

)

data class BudgetListUiState(
    val budgetElements : List<BudgetElementUiState> = listOf(),
    val newBudgetElement : BudgetElementUiState = BudgetElementUiState(),
    val addBudgetStatus: Boolean = false,
    val addTotalBudgetStatus: Boolean = false,
    val budgetMax: Int = 0,
    val newBudgetMax : Int = 0
)
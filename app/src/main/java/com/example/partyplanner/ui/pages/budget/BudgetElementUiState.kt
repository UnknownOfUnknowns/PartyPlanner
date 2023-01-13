package com.example.partyplanner.ui.pages.budget


data class BudgetElementUiState(
    val budgetName: String = "",
    val budgetPrice: Int = 0,
    val budgetNote: String = "",


)

data class ChangeNoteUiState(
    val element : BudgetElementUiState = BudgetElementUiState(),
    val newValue : String = "",
    val newPrice : Int = 0,
)

data class BudgetListUiState(
    val budgetElements : List<BudgetElementUiState> = listOf(),
    val newBudgetElement : BudgetElementUiState = BudgetElementUiState(),
    val changeNoteState : ChangeNoteUiState = ChangeNoteUiState(),
    val addBudgetStatus: Boolean = false,
    val addTotalBudgetStatus: Boolean = false,
    val setBudgetNote : Boolean = false,
    val budgetMax: Int = 0,
    val newBudgetMax : Int = 0,
    val budgetSpent : Int = 0,
    val budgetLeft : Int = 0
)


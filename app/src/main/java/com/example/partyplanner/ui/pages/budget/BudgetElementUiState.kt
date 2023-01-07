package com.example.partyplanner.ui.pages.budget


data class BudgetElementUiState(
    val budgetName: String = "",
    val budgetPrice: Int = 0,
    val budgetNote: String = "en lang historie fra de varme lande som ingen husker",


)

data class ChangeNoteUiState(
    val element : BudgetElementUiState = BudgetElementUiState(),
    val newValue : String = ""
)

data class BudgetListUiState(
    val budgetElements : List<BudgetElementUiState> = listOf(),
    val newBudgetElement : BudgetElementUiState = BudgetElementUiState(),
    val changeNoteState : ChangeNoteUiState = ChangeNoteUiState(),
    val addBudgetStatus: Boolean = false,
    val addTotalBudgetStatus: Boolean = false,
    val setBudgetNote : Boolean = false,
    val budgetMax: Int = 0,
    val newBudgetMax : Int = 0

)


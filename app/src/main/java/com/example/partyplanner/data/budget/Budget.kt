package com.example.partyplanner.data.budget

import com.example.partyplanner.data.wish.Wish
import com.example.partyplanner.ui.pages.budget.BudgetUiState
import com.example.partyplanner.ui.pages.wishlist.WishUiState

data class Budget(
    val budgetName: String = "",
    val budgetPrice: Int = 0,
)

fun Budget.toUiState() : BudgetUiState = BudgetUiState(
    budgetName = budgetName,
    budgetPrice = budgetPrice
)


fun Budget.getFromUiState(state: BudgetUiState) : Budget = Budget(
    budgetName = state.budgetName,
    budgetPrice = state.budgetPrice
)
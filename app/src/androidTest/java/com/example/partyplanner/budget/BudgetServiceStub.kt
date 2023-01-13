package com.example.partyplanner.budget

import com.example.partyplanner.data.budget.Budget
import com.example.partyplanner.data.budget.BudgetService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow

class BudgetServiceStub : BudgetService {
    private var budgetMax = 100

    private val stubBudgets = mutableListOf(
        Budget(
            id = "123",
            budgetName = "Test name",
            budgetPrice = 1,
            budgetNote = "Test note"
        )
    )

    override val budgets: Flow<List<Budget>>
        get() = listOf(stubBudgets).asFlow()

    override suspend fun addBudgetItem(budget: Budget, onResult: (Throwable?) -> Unit) {
        stubBudgets.add(budget)
        onResult(null)
    }

    override suspend fun setBudgetMax(newMax: Int, onResult: (Throwable?) -> Unit) {
        budgetMax = newMax
        onResult(null)
    }

    override suspend fun getBudgetMax(onResult: (Int) -> Unit) {
        onResult(budgetMax)
    }

    override suspend fun update(
        newNote: String,
        newPrice : Int,
        budget: Budget,
        onResult: (Throwable?) -> Unit
    ) {

    }
}
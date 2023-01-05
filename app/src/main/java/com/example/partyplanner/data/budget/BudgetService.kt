package com.example.partyplanner.data.budget

import kotlinx.coroutines.flow.Flow

interface BudgetService {
    val budgets: Flow<List<Budget>>

    suspend fun addBudgetItem(budget: Budget, onResult: (Throwable?) -> Unit)

    suspend fun setBudgetMax(newMax : Int, onResult: (Throwable?) -> Unit)

    suspend fun getBudgetMax(onResult: (Int) -> Unit)
}
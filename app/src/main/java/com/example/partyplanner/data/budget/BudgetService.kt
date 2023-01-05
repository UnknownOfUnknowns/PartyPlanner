package com.example.partyplanner.data.budget

import kotlinx.coroutines.flow.Flow

interface BudgetService {
    val budgets: Flow<List<Budget>>

    suspend fun addBudgetItem(budget: Budget, onResult: (Throwable?) -> Unit)

    suspend fun setBudgetMax(budget: Budget, onResult: (Throwable?) -> Unit)
}
package com.example.partyplanner.ui.pages.budget

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.partyplanner.data.budget.Budget
import com.example.partyplanner.data.budget.BudgetService
import com.example.partyplanner.data.budget.getFromUiState
import com.example.partyplanner.data.budget.toUiState
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class BudgetViewModel (private val repository : BudgetService) : ViewModel() {
    private val _internalState = MutableStateFlow(BudgetListUiState())
    val uiState = combine(
        _internalState,
        repository.budgets
    ) {
        _internalState, budgets ->
        val elements = budgets.map { it.toUiState() }
        val spent = elements.sumOf { it.budgetPrice }
        _internalState.copy(
            budgetElements = elements,
            budgetSpent =spent,
            budgetLeft = _internalState.budgetMax-spent)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = BudgetListUiState()
    )

    init {
        viewModelScope.launch {
            repository.getBudgetMax{ max ->
                _internalState.update {
                    it.copy(budgetMax = max, newBudgetMax = max)
                }
            }
        }

    }



    fun changeTotalBudget(newMaxPrice: Int) {
        _internalState.update { currentState ->
            currentState.copy(newBudgetMax = newMaxPrice)
        }
    }

    fun addBudget() {
        viewModelScope.launch {
            val state = _internalState.value
            repository.addBudgetItem(Budget().getFromUiState(state.newBudgetElement)){
                if (it == null) {
                    _internalState.update { currentState ->
                        currentState.copy(newBudgetElement = BudgetElementUiState(), addBudgetStatus = false)
                    }
                }
            }
        }
    }

    fun deleteWish() {
        viewModelScope.launch {
            val budget = _internalState.value.budgetToBeDeleted
            if(budget != null) {
                repository.deleteBudget(Budget().getFromUiState(budget)) {

                }
                toggleDelete()
            }
        }
    }

    fun toggleDelete(budget: BudgetElementUiState? = null) {
        val newDeleteBudget = if(_internalState.value.budgetToBeDeleted == null) budget else null
        _internalState.update {
            it.copy(budgetToBeDeleted = newDeleteBudget)
        }
    }

    fun startUpdate(budget: BudgetElementUiState) {
        _internalState.update {
            it.copy(
                setBudgetNote = true,
                changeNoteState = ChangeNoteUiState(
                    element = budget,
                    newValue = budget.budgetNote,
                    newPrice = budget.budgetPrice
                )
            )
        }
    }
    fun endUpdate(updateInDatabase : Boolean) {
        val changeState = _internalState.value.changeNoteState
        if(updateInDatabase){
            viewModelScope.launch {
                repository.update(changeState.newValue, changeState.newPrice, Budget().getFromUiState(changeState.element)) {

                }
            }
        }

        _internalState.update {
            it.copy(setBudgetNote = false)
        }
    }

    fun updateNoteValue(newValue : String) {
        _internalState.update {
            it.copy(changeNoteState = it.changeNoteState.copy(newValue = newValue))
        }
    }

    fun updatePrice(newPrice : Int) {
        _internalState.update {
            it.copy(changeNoteState = it.changeNoteState.copy(newPrice = newPrice))
        }
    }





    fun setMaxBudget() {
        viewModelScope.launch {
            repository.setBudgetMax(_internalState.value.newBudgetMax) {
                if(it == null) {
                    _internalState.update { currentState ->
                        currentState.copy(budgetMax = _internalState.value.newBudgetMax,
                            addTotalBudgetStatus = false)
                    }
                }
            }
        }
    }
    fun changeBudgetName(newName: String) {
        _internalState.update { currentState ->
            currentState.copy(newBudgetElement = currentState.newBudgetElement.copy(budgetName = newName))
        }
    }

    fun changeBudgetMax(newStatus: Boolean) {
        _internalState.update { currentState ->
            currentState.copy(addTotalBudgetStatus = newStatus)
        }
    }

    fun addBudgetStatus(newStatus: Boolean) {
        _internalState.update { currentState ->
            currentState.copy(addBudgetStatus = newStatus)
        }
    }

    fun changeBudgetPrice(newPrice: Int) {
        _internalState.update { currentState ->
            currentState.copy(newBudgetElement = currentState.newBudgetElement.copy(budgetPrice = newPrice))
        }
    }

}
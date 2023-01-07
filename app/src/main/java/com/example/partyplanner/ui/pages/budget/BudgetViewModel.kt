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
        _internalState.copy(budgetElements = budgets.map { it.toUiState() })
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

    fun startNoteUpdate(budget: BudgetElementUiState) {
        _internalState.update {
            it.copy(
                setBudgetNote = true,
                changeNoteState = ChangeNoteUiState(
                    element = budget,
                    newValue = budget.budgetNote
                )
            )
        }
    }
    fun endNoteUpdate(updateInDatabase : Boolean) {
        val changeState = _internalState.value.changeNoteState
        if(updateInDatabase){
            viewModelScope.launch {
                repository.setNewNote(changeState.newValue, Budget().getFromUiState(changeState.element)) {

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
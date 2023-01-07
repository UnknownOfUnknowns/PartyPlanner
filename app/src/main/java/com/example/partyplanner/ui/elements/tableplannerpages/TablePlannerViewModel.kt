package com.example.partyplanner.ui.elements.tableplannerpages

import com.example.partyplanner.ui.state.PartyCoreInfoUiState
import com.example.partyplanner.ui.state.PartyType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class TablePlannerViewModel {

    private val _uiState = MutableStateFlow(TablePlannerUiState())
    val uiState: StateFlow<TablePlannerUiState> = _uiState.asStateFlow()

    fun updateTableType(newTableType: String){
        _uiState.update { currentState ->
            currentState.copy(
                tableType = when(newTableType) {
                    "Kvadratisk bord" -> TableType.SQUARETABLE
                    "Rektangulært bord" -> TableType.REKTTABLE
                    else -> TableType.ROUNDTABLE
                }
            )
        }
    }

    fun updateTableSeats(newTableSeat: Int){
        _uiState.update { currentState -> currentState.copy(
            tableSeats = newTableSeat
        )}
    }
}
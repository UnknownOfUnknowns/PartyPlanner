package com.example.partyplanner.ui.elements.tableplannerpages

enum class TableType {
    SQUARETABLE,
    REKTTABLE,
    ROUNDTABLE
}



data class TablePlannerUiState (
    val tableType: TableType = TableType.SQUARETABLE,
    val tableSeats: Int = 4
        )


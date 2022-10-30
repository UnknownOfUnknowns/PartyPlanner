package com.example.partyplanner.ui.state

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class GuestListViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(GuestListUiState())
    val uiState : StateFlow<GuestListUiState> = _uiState.asStateFlow()

}
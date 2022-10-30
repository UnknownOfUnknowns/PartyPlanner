package com.example.partyplanner.ui.elements

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.example.partyplanner.ui.state.GuestListViewModel

@Composable
fun GuestListPage(viewModel: GuestListViewModel) {
    val uiState = viewModel.uiState.collectAsState()


}
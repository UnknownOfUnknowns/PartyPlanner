package com.example.partyplanner.ui.guestpages

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.partyplanner.data.party.GuestPartyInfo
import com.example.partyplanner.data.party.PartyService
import com.example.partyplanner.ui.state.AttendanceState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GuestMenuViewModel(private val repository: PartyService,
                         private val partyId: String
                         ) : ViewModel() {
    private val _uiState = MutableStateFlow(GuestPartyInfo())
    val uiState = _uiState.asStateFlow()

    init {
        getPartyInfo()
    }
    fun updateAttendanceState(newAttendingState: AttendanceState){
        viewModelScope.launch {
            repository.changeAttendanceState(partyId, newAttendingState) {
                if(it == null) {
                   getPartyInfo()
                }
            }
        }
    }

    private fun getPartyInfo() {
        viewModelScope.launch {
            repository.getGuestPartyInfo(partyId) { newInfo ->
                _uiState.update {
                    newInfo
                }
            }
        }
    }
}
package com.example.partyplanner.ui.state

import androidx.lifecycle.ViewModel
import com.example.partyplanner.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class WishListViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(WishListUiState())
    val uiState: StateFlow<WishListUiState> = _uiState.asStateFlow()


    init {
        fetchWishes()
    }
    // Dummy function should get data from repository
    fun fetchWishes() {
        _uiState.update {
            it.copy(wishes = listOf(
                WishUiState(image = R.drawable._nske2, wishName = "Ting"),
                WishUiState(image = R.drawable.coffee_machine, wishName = "Ting"),
                WishUiState(image = R.drawable._nske3, wishName = "Ting"),
                WishUiState(image = R.drawable._nske2, wishName = "Ting"),
                WishUiState(image = R.drawable.coffee_machine, wishName = "Ting", price = 1000),
                WishUiState(image = R.drawable._nske3, wishName = "Ting", isReserved = true),
            ),
                wishListName = "Christians' Ønskeliste"
            )
        }
    }
}
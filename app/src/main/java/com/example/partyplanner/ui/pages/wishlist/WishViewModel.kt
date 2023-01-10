package com.example.partyplanner.ui.pages.wishlist

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.partyplanner.data.wish.WishServiceImpl
import com.example.partyplanner.data.wish.toUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class WishViewModel(private val repository : WishServiceImpl, private val wishId : String) : ViewModel() {
    private val _uiState = MutableStateFlow(WishUiState())
    val uiState = _uiState.asStateFlow()
    val wishListName = mutableStateOf("")
    init {
        fetchWish()
        viewModelScope.launch {

            val name = repository.getWishListName()
            wishListName.value = name
        }
    }

    private fun fetchWish() {
        viewModelScope.launch {
            repository.getWish(wishId) { wish ->
                if(wish != null) {
                    _uiState.update {
                       wish.toUiState()
                    }
                }
            }
        }
    }
}
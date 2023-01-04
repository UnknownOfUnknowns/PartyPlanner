package com.example.partyplanner.ui.pages.wishlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.partyplanner.data.wish.Wish
import com.example.partyplanner.data.wish.WishService
import com.example.partyplanner.data.wish.getFromUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class WishListViewModel (private val repository : WishService) : ViewModel() {
    val wishes = repository.wishes
    private val _uiState = MutableStateFlow(WishListUiState())
    val uiState: StateFlow<WishListUiState> = _uiState.asStateFlow()


    init {
        viewModelScope.launch {
            val name = repository.getWishListName()
            _uiState.update {
                it.copy(wishListName = name)
            }
        }
    }

    fun changeWishOn(newStatus: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(
                addWish = newStatus
            )
        }
    }
    fun changeWishName(newName: String) {
        _uiState.update { currentState ->
            currentState.copy(
                newWish = currentState.newWish.copy(wishName = newName)
            )
        }
    }
    fun changeLinkName(newLink: String) {
        _uiState.update { currentState ->
            currentState.copy(
                newWish = currentState.newWish.copy(link = newLink)
            )

        }
    }
    fun changeDescription(newDescript: String) {
        _uiState.update { currentState ->
            currentState.copy(
                newWish = currentState.newWish.copy(description = newDescript)
            )
        }
    }
    fun changePrice(newPrice: Int) {
        _uiState.update { currentState ->
            currentState.copy(
                newWish = currentState.newWish.copy(price = newPrice)
            )

        }
    }

    fun addWish() {
        viewModelScope.launch {
            val state = _uiState.value
            repository.addWish(Wish().getFromUiState(state.newWish)){
                if(it==null) {
                    _uiState.update { currentState ->
                        currentState.copy(newWish = WishUiState(), addWish = false)
                    }
                }
            }
        }
    }
}
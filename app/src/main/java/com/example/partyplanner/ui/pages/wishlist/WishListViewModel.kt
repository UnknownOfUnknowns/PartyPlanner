package com.example.partyplanner.ui.pages.wishlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.partyplanner.data.imageLoader.ImageService
import com.example.partyplanner.data.imageLoader.ImageServiceImpl
import com.example.partyplanner.data.wish.Wish
import com.example.partyplanner.data.wish.WishService
import com.example.partyplanner.data.wish.getFromUiState
import com.example.partyplanner.data.wish.toUiState
import com.example.partyplanner.domain.ImagePicker
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class WishListViewModel (private val repository : WishService,
                         private val imageService: ImageService = ImageServiceImpl()) : ViewModel() {
    private val _internalState = MutableStateFlow(WishListUiState())
    val uiState = combine(
        _internalState,
        repository.wishes
    ) {
        _internalState, wishes ->
        _internalState.copy(wishes = wishes.map { wish ->
            wish.toUiState()
        })
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = WishListUiState()
    )


    init {
        viewModelScope.launch {
            val name = repository.getWishListName()
            _internalState.update {
                it.copy(wishListName = name)
            }
        }

    }

    fun changeWishOn(newStatus: Boolean) {
        _internalState.update { currentState ->
            currentState.copy(
                addWish = newStatus
            )
        }
    }
    fun changeWishName(newName: String) {
        _internalState.update { currentState ->
            currentState.copy(
                newWish = currentState.newWish.copy(wishName = newName)
            )
        }
    }
    fun changeLinkName(newLink: String) {
        _internalState.update { currentState ->
            currentState.copy(
                newWish = currentState.newWish.copy(link = newLink)
            )

        }
    }
    fun changeDescription(newDescript: String) {
        _internalState.update { currentState ->
            currentState.copy(
                newWish = currentState.newWish.copy(description = newDescript)
            )
        }
    }
    fun changePrice(newPrice: Int) {
        _internalState.update { currentState ->
            currentState.copy(
                newWish = currentState.newWish.copy(price = newPrice)
            )

        }
    }

    fun addWish() {
        viewModelScope.launch {
            val state = _internalState.value
            repository.addWish(Wish().getFromUiState(state.newWish)){
                if(it==null) {
                    _internalState.update { currentState ->
                        currentState.copy(newWish = WishUiState(), addWish = false)
                    }
                }
            }
        }

    }

    fun chooseWishImage() {
        ImagePicker.getImage { bitmap ->
            _internalState.update {
                it.copy(newWish = it.newWish.copy(newImage = bitmap))
            }
        }
    }

}
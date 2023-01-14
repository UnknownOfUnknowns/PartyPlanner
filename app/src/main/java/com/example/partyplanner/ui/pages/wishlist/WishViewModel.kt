package com.example.partyplanner.ui.pages.wishlist

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.partyplanner.data.wish.Wish
import com.example.partyplanner.data.wish.WishService
import com.example.partyplanner.data.wish.getFromUiState
import com.example.partyplanner.data.wish.toUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class WishViewModel(private val repository : WishService, private val wishId : String) : ViewModel() {
    private val _uiState = MutableStateFlow(WishUiState())
    val uiState = _uiState.asStateFlow()
    val wishListName = mutableStateOf("")
    val linkError = mutableStateOf(false)
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


    fun reserveWish() {
        viewModelScope.launch {
            repository.changeReservationState(Wish().getFromUiState(_uiState.value)) {

            }
        }
    }

    // this function takes the context of the composable and opens the link
    // this action is lifted out of the composable due to the complexity which should not reside in the UI
    fun openLink(context : Context) {
        try {
            var url = _uiState.value.link
            if(!url.startsWith("https://") || !url.startsWith("http://")) {
                url = "https://"+_uiState.value.link
            }
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            context.startActivity(intent)
        } catch (e : Exception) {
            linkError.value = true
        }
    }
}
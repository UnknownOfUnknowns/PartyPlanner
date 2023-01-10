package com.example.partyplanner.ui.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.partyplanner.data.wish.WishServiceImpl
import com.example.partyplanner.ui.pages.wishlist.WishListViewModel
import com.example.partyplanner.ui.pages.wishlist.WishUiState
import com.example.partyplanner.ui.theme.Background
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun WishListGuestPage(viewModel: WishListViewModel, navigateToProduct: (WishUiState) -> Unit) {
    val uiState = viewModel.uiState.collectAsState()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
    ) {
        Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            NameCardWishList(name = uiState.value.wishListName, modifier = Modifier.size(width = 350.dp, height = 120.dp ))
            Spacer(modifier = Modifier.height(17.dp))
            WishList(uiState.value.wishes, showTopBar = true, onImageClick = { navigateToProduct(it) })
        }
        ShareFAB(modifier = Modifier
            .align(Alignment.BottomEnd), onClick = {})
    }
}


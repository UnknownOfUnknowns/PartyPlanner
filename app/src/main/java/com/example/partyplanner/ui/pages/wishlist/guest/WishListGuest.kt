package com.example.partyplanner.ui.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.partyplanner.ui.state.WishListViewModel
import com.example.partyplanner.ui.theme.Background

@Composable
fun WishListGuestPage(viewModel: WishListViewModel) {
    val uiState = viewModel.uiState.collectAsState()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
    ) {
        Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            NameCardWishList(name = uiState.value.wishListName, modifier = Modifier.size(width = 350.dp, height = 120.dp ))
            Spacer(modifier = Modifier.height(17.dp))
            WishList(uiState.value, showTopBar = true)
        }
        ShareFAB(modifier = Modifier
            .align(Alignment.BottomEnd), onClick = {})

    }

}

@Composable
@Preview(showBackground = true)
fun WishListGuestPreview() {
    WishListGuestPage(viewModel = WishListViewModel())
}
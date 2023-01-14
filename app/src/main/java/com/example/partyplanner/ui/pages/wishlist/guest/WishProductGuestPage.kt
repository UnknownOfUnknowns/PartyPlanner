package com.example.partyplanner.ui.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.partyplanner.ui.pages.wishlist.WishViewModel

@Composable
fun WishProductGuestPage(viewModel: WishViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    Box(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background)
    ){
        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            NameCardWishList(name = viewModel.wishListName.value,
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .padding(start = 11.dp, end = 11.dp, bottom = 17.dp)
            )
            CardWithProduct(
                wishUiState = uiState,
                isGuest = true,
                onReserveClick = viewModel::reserveWish,
                onLinkClick = viewModel::openLink,
                linkError = viewModel.linkError.value
            )
        }
    }
}
/* Box(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background)
    ){
        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            NameCardWishList(name = wishUiState.wishName, modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .padding(start = 11.dp, end = 11.dp, bottom = 17.dp))
            CardWithProduct(wishUiState = wishUiState)
        }
        DeleteFAB(onClick = {}, modifier = Modifier
            .align(Alignment.BottomStart))
        EditFAB(onClick = {}, modifier = Modifier
            .align(Alignment.BottomEnd))
    }

} */








@Preview(showBackground = true)
@Composable
fun WishGuestProductPreview() {

}
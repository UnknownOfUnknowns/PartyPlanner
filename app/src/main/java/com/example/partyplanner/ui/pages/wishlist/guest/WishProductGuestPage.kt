package com.example.partyplanner.ui.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun WishProductGuestPage() {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background)
    ){
        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
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
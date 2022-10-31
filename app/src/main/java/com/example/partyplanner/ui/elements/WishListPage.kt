package com.example.partyplanner.ui.elements

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.partyplanner.ui.state.WishListUiState
import com.example.partyplanner.ui.state.WishListViewModel
import com.example.partyplanner.ui.theme.Primary


@Composable
fun NameCardWishList(modifier: Modifier = Modifier, name: String) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(15),
        colors = CardDefaults.cardColors(Primary),


    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = name,
                color = Color.White,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold)

        }
    }
}
@Composable
fun Wish(modifier: Modifier = Modifier, image: Painter, giftName: String) {
    Card(
        modifier = modifier
            .size(width = 150.dp, height = 150.dp),
        shape = RoundedCornerShape(10),
        colors = CardDefaults.cardColors(Primary)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Primary),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(painter = image,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.8f),
                contentScale = ContentScale.Crop)
            Text(
                modifier = Modifier.weight(0.2f),
              text = giftName,
              color = Color.White,
              fontSize = 15.sp,
              fontWeight = FontWeight.Bold
            )

        }
    }

}


@Composable
fun WishList(wishes : WishListUiState) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ){
        items(wishes.wishes){ wish ->
            Wish(image = painterResource(id = wish.image), giftName = wish.wishName)

        }
    }

}

@Composable
fun WishListPage(viewModel: WishListViewModel){
    val uiState = viewModel.uiState.collectAsState()
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        NameCardWishList(name = uiState.value.wishListName, modifier = Modifier.size(width = 350.dp, height = 120.dp ))
        WishList(uiState.value)
    }
}


@Preview(showBackground = true)
@Composable
fun WishListPreview() {

    Column(
        modifier = Modifier.
        fillMaxSize()
    ) {
        //NameCardWishList(modifier = Modifier.size(width = 350.dp, height = 120.dp ), "Hans")
        //Spacer(modifier = Modifier.height(30.dp))
        WishListPage(viewModel = WishListViewModel())
    }
}
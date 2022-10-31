package com.example.partyplanner.ui.elements

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
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
import com.example.partyplanner.ui.theme.AttendingInfoColor
import com.example.partyplanner.ui.theme.Background
import com.example.partyplanner.ui.theme.Primary
import com.example.partyplanner.ui.theme.SecondaryContainer


@Composable
fun NameCardWishList(modifier: Modifier = Modifier, name: String) {
    Card(
        modifier = modifier
            .padding(top = 10.dp),
        shape = RoundedCornerShape(15),
        colors = CardDefaults.cardColors(AttendingInfoColor),


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
        colors = CardDefaults.cardColors(Primary),
        border = BorderStroke(1.dp, Color.Black)
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
    Card(
        modifier = Modifier.padding(start = 11.dp, end = 11.dp, bottom = 30.dp),
        shape = RoundedCornerShape(15.dp),
        colors = CardDefaults.cardColors(SecondaryContainer)
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .padding(vertical = 32.dp, horizontal = 28.dp)
                .fillMaxSize(),
        ){
            items(wishes.wishes){ wish ->
                Wish(modifier = Modifier.padding(8.dp), image = painterResource(id = wish.image), giftName = wish.wishName)

            }
        }
    }


}

@Composable
fun WishListPage(viewModel: WishListViewModel){
    val uiState = viewModel.uiState.collectAsState()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
    ) {
        Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            NameCardWishList(name = uiState.value.wishListName, modifier = Modifier.size(width = 350.dp, height = 120.dp ))
            Spacer(modifier = Modifier.height(17.dp))
            WishList(uiState.value)
        }
        DefaultFAB(modifier = Modifier
            .align(Alignment.BottomEnd), onClick = {})
        ShareFAB(modifier = Modifier
            .align(Alignment.BottomStart), onClick = {})

    }

}
@Composable
fun ShareFAB(modifier: Modifier = Modifier, onClick: () -> Unit) {
    FloatingActionButton(onClick = onClick,
        modifier = modifier
            .padding(start = 10.dp, bottom = 10.dp)
            .size(60.dp),
        shape = FloatingActionButtonDefaults.largeShape,
        containerColor = Primary,
        contentColor = Color.White
    ) {
        Icon(imageVector = Icons.Default.Share, contentDescription = null, modifier = Modifier
            .fillMaxSize()
            .padding(8.dp, end = 10.dp))
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
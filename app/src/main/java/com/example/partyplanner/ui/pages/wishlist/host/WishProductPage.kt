package com.example.partyplanner.ui.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.BottomEnd
import androidx.compose.ui.Alignment.Companion.BottomStart
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.partyplanner.R
import com.example.partyplanner.ui.pages.wishlist.WishUiState
import com.example.partyplanner.ui.theme.Primary
/*
@Composable
fun WishDescription(wishUiState: WishUiState, showButton: Boolean = false) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = wishUiState.wishName + "  " + "(" + wishUiState.price + "kr)",
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            textDecoration = TextDecoration.Underline
        )
        Spacer(modifier = Modifier.height(30.dp))
        Wish(wishUiState = wishUiState)
        Spacer(modifier = Modifier.height(5.dp))
        Text(text = wishUiState.link,
            fontWeight = FontWeight.Bold,
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(text = "Beskrivelse:",
            fontWeight = FontWeight.Bold
        )
        Text(text = wishUiState.description)
        if(showButton) {
            Button(onClick = {}, modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(start = 50.dp, end = 50.dp)
            ) {
                Text(text = "LINK")
            }
        }
    }
}*/
@Composable
fun CardWithProduct(modifier: Modifier = Modifier, wishUiState: WishUiState) {
    Card(
        modifier = modifier.padding(start = 11.dp, end = 11.dp, bottom = 30.dp),
        shape = RoundedCornerShape(15.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.secondaryContainer)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = wishUiState.wishName + "  " + "(" + wishUiState.price + "kr)",
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                textDecoration = TextDecoration.Underline
            )

            Spacer(modifier = Modifier.height(30.dp))

            Wish(wishUiState = wishUiState, modifier = Modifier.size(height = 200.dp, width = 150.dp))

            Spacer(modifier = Modifier.height(5.dp))

            Text(text = wishUiState.link,
                fontWeight = FontWeight.Bold,
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(text = "Beskrivelse:",
                fontWeight = FontWeight.Bold
            )

            Text(text = wishUiState.description)

            if(wishUiState.isGuest) {
                Column {
                    GuestButton(buttonName = "Link")
                    Spacer(modifier = Modifier.height(10.dp))
                    GuestButton(buttonName = "Reservér")
                }
            }
        }
    }
}
@Composable
fun GuestButton(buttonName: String) {
    Button(onClick = {}, modifier = Modifier
        .fillMaxWidth()
        .height(50.dp)
        .padding(start = 50.dp, end = 50.dp),
        shape = RoundedCornerShape(10)
    ) {
        Text(text = buttonName)
    }
}

@Composable
fun EditFAB(onClick: () -> Unit, modifier: Modifier = Modifier) {
    FloatingActionButton(onClick = onClick,
        modifier = modifier
            .padding(start = 10.dp, bottom = 10.dp)
            .size(60.dp),
        shape = FloatingActionButtonDefaults.largeShape,
        containerColor = Primary,
        contentColor = Color.White
    ) {
        Icon(imageVector = Icons.Default.Edit, contentDescription = null, modifier = Modifier
            .fillMaxSize()
            .padding(8.dp, end = 10.dp))
    }
}

@Composable
fun DeleteFAB(onClick: () -> Unit, modifier: Modifier = Modifier) {
    FloatingActionButton(onClick = onClick,
        modifier = modifier
            .padding(start = 10.dp, bottom = 10.dp)
            .size(60.dp),
        shape = FloatingActionButtonDefaults.largeShape,
        containerColor = Primary,
        contentColor = Color.Red
    ) {
        Icon(imageVector = Icons.Default.Close, contentDescription = null, modifier = Modifier
            .fillMaxSize()
            .padding(8.dp, end = 10.dp))
    }
}

@Composable
fun WishProductPage(wishUiState: WishUiState) {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background)
    ){
        Column(
        modifier = Modifier,
            horizontalAlignment = CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            NameCardWishList(name = wishUiState.wishName, modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .padding(start = 11.dp, end = 11.dp, bottom = 17.dp))
            CardWithProduct(wishUiState = wishUiState)
        }
        if(!wishUiState.isGuest) {
            DeleteFAB(
                onClick = {}, modifier = Modifier
                    .align(BottomStart)
            )
            EditFAB(
                onClick = {}, modifier = Modifier
                    .align(BottomEnd)
            )
        }
    }
}


@Composable
@Preview(showBackground = true)
fun WishProductPreview() {
    //WishDescription(wishUiState = WishUiState(image = R.drawable._nske2, wishName = "Ting"), showButton = true)
    WishProductPage(wishUiState = WishUiState(image = R.drawable._nske2,
        wishName = "ROLEX",
        price = 15000,
        description = "FED KAFFEMASKINE!",
        link = "www.elgigantos.dk", isGuest = true))
}
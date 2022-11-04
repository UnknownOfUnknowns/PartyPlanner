package com.example.partyplanner.ui.elements

import android.text.style.UnderlineSpan
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomEnd
import androidx.compose.ui.Alignment.Companion.BottomStart
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.partyplanner.R
import com.example.partyplanner.ui.state.WishListUiState
import com.example.partyplanner.ui.state.WishUiState
import com.example.partyplanner.ui.theme.Background
import com.example.partyplanner.ui.theme.Primary
import com.example.partyplanner.ui.theme.SecondaryContainer


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
            Wish(image = painterResource(wishUiState.image), giftName = wishUiState.wishName, wishUiState = wishUiState)
            Spacer(modifier = Modifier.height(5.dp))
            Text(text = wishUiState.link,
                fontWeight = FontWeight.Bold,
                )
            Spacer(modifier = Modifier.height(5.dp))
            Text(text = "Beskrivelse:",
                fontWeight = FontWeight.Bold
                )
            Text(text = wishUiState.description)
        }
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
        DeleteFAB(onClick = {}, modifier = Modifier
            .align(BottomStart))
        EditFAB(onClick = {}, modifier = Modifier
            .align(BottomEnd))
    }

}


@Composable
@Preview(showBackground = true)
fun WishProductPreview() {
    WishProductPage(wishUiState = WishUiState(image = R.drawable._nske2,
        wishName = "ROLEX",
        price = 15000,
        description = "FED KAFFEMASKINE!",
        link = "www.elgigantos.dk"))

}
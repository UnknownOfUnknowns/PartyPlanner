package com.example.partyplanner.ui.elements

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.BottomEnd
import androidx.compose.ui.Alignment.Companion.BottomStart
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.partyplanner.R
import com.example.partyplanner.ui.pages.wishlist.WishUiState
import com.example.partyplanner.ui.pages.wishlist.WishViewModel
import com.example.partyplanner.ui.theme.Background
import com.example.partyplanner.ui.theme.Primary

@Composable
fun WishProductPage(viewModel: WishViewModel, isGuest: Boolean) {
    val uiState = viewModel.uiState.collectAsState()

    var showDeleteDialog by remember {
        mutableStateOf(false)
    }
    Box(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background)
    ){
        Column(
            modifier = Modifier,
            horizontalAlignment = CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            NameCardWishList(name = viewModel.wishListName.value, modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .padding(start = 11.dp, end = 11.dp, bottom = 17.dp))
            CardWithProduct(wishUiState = uiState.value, isGuest = isGuest)
        }
        if(showDeleteDialog) {
            DeleteDialog(
                text = "Vil du slette dette ønske?",
                onDismiss = {showDeleteDialog = false},
                onDelete = {showDeleteDialog = false; viewModel.deleteWish()}
            )
        }
        if(!isGuest) {
            DeleteFAB(
                onClick = {showDeleteDialog = true}, modifier = Modifier
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
fun DeleteDialog(text : String, onDismiss: () -> Unit, onDelete : () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Card(modifier = Modifier,
            shape = RoundedCornerShape(10),
            colors = CardDefaults.cardColors(Background)) {
            Column(Modifier.padding(15.dp),
                horizontalAlignment = CenterHorizontally) {
                Text(text = stringResource(R.string.warning), fontSize = 20.sp)
                Text(text = text)
                Row {
                    TextButton(onClick = onDismiss) {
                        Text(text = "Afbryd")
                    }
                    TextButton(onClick = onDelete) {
                        Text(text = "Ja", fontWeight = FontWeight.Bold)
                    }
                }

            }
        }
        
    }
}

@Composable
fun CardWithProduct(modifier: Modifier = Modifier,
                    wishUiState: WishUiState,
                    isGuest : Boolean,
                    linkError : Boolean = false,
                    onLinkClick : (Context) -> Unit = {},
                    onReserveClick : () -> Unit = {}) {
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

            Text(text = stringResource(id = R.string.budgetDialogDescriptionText),
                fontWeight = FontWeight.Bold
            )

            Text(text = wishUiState.description)
            if(isGuest) {
                val context = LocalContext.current
                Column {
                    if(linkError) {
                        Text(text = stringResource(R.string.linkCouldNotBeOpened), modifier = Modifier.align(CenterHorizontally),
                            color = Color.Red)
                    }
                    GuestButton(buttonName = stringResource(R.string.link), {onLinkClick(context)})
                    Spacer(modifier = Modifier.height(10.dp))
                    GuestButton(buttonName = if(wishUiState.isReserved) stringResource(R.string.undoReservation) else stringResource(R.string.reserve),
                        onReserveClick)
                }
            }
        }
    }
}
@Composable
fun GuestButton(buttonName: String, onClick: () -> Unit) {
    Button(onClick = onClick, modifier = Modifier
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





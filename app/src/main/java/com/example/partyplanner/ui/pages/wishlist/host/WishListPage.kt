package com.example.partyplanner.ui.elements

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.text.isDigitsOnly
import com.example.partyplanner.R
import com.example.partyplanner.data.wish.WishServiceImpl
import com.example.partyplanner.ui.pages.wishlist.WishListViewModel
import com.example.partyplanner.ui.pages.wishlist.WishUiState
import com.example.partyplanner.ui.theme.AttendingInfoColor
import com.example.partyplanner.ui.theme.Background
import com.example.partyplanner.ui.theme.Primary
import com.example.partyplanner.ui.theme.SecondaryContainer
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun WishListPage(viewModel: WishListViewModel){
    val uiState = viewModel.uiState.collectAsState()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
    ) {
        Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = CenterHorizontally) {
            NameCardWishList(name = uiState.value.wishListName, modifier = Modifier.size(width = 350.dp, height = 120.dp ))
            Spacer(modifier = Modifier.height(17.dp))
            WishList(uiState.value.wishes)
        }
        if(uiState.value.addWish){
            AddWishDialog(
                onDismiss = { viewModel.changeWishOn(false) },
                onAddWish = {viewModel.addWish()},
                wishUiState = uiState.value.newWish,
                onWishNameChange = { viewModel.changeWishName(it)},
                onLinkChange = { viewModel.changeLinkName(it)},
                onPriceChange = { viewModel.changePrice(it)},
                onDescriptionChange = { viewModel.changeDescription(it)},
                onChooseImage = viewModel::chooseWishImage
            )
        }
        if(!uiState.value.addWish) {
            DefaultFAB(modifier = Modifier
                .align(Alignment.BottomEnd), onClick = { viewModel.changeWishOn(true) })
            ShareFAB(modifier = Modifier
                .align(Alignment.BottomStart), onClick = {})
        }
    }

}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddWishDialog(
    onDismiss: () -> Unit,
    onAddWish: () -> Unit,
    wishUiState: WishUiState,
    onWishNameChange: (String) -> Unit,
    onLinkChange: (String) -> Unit,
    onPriceChange: (Int) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onChooseImage: () -> Unit

    ) {
            // Det skal være muligt at indtaste æ,ø,å i add wish
    Dialog(onDismissRequest = onDismiss) {
        Card(modifier = Modifier
            .padding(all = 15.dp),
            shape = RoundedCornerShape(10)
        ) {
            Column(
                horizontalAlignment = CenterHorizontally,
                modifier = Modifier
                    .padding(horizontal = 10.dp)
            ) {
                Text(
                    text = "Tilføj Ønske",
                    modifier = Modifier
                        .align(CenterHorizontally)
                        .padding(vertical = 10.dp),
                    fontSize = 30.sp
                )
                OutlinedTextField(
                    value = wishUiState.wishName, onValueChange = onWishNameChange,
                    modifier = Modifier
                        .align(CenterHorizontally)
                        .padding(vertical = 10.dp),
                    label = { Text(text = "Indtast navn på ønske") },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        containerColor = Color.White
                    ),
                    shape = RoundedCornerShape(10)
                )
                OutlinedTextField(
                    value = wishUiState.link, onValueChange = onLinkChange,
                    modifier = Modifier
                        .align(CenterHorizontally)
                        .padding(vertical = 10.dp),
                    label = { Text(text = "Evt. Link til ønsket") },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        containerColor = Color.White
                    ),
                    shape = RoundedCornerShape(10)
                )
                OutlinedTextField(
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    value = wishUiState.price.toString(),
                    onValueChange = {
                        if (it.isDigitsOnly()) {
                            onPriceChange(it.toInt())
                        }
                    },
                    modifier = Modifier
                        .align(CenterHorizontally)
                        .padding(vertical = 10.dp),
                    label = { Text(text = "Pris på ønsket") },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        containerColor = Color.White
                    ),
                    shape = RoundedCornerShape(10)
                )
                OutlinedTextField(
                    modifier = Modifier
                        .padding(vertical = 10.dp)
                        .align(CenterHorizontally),
                    value = wishUiState.description,
                    onValueChange = onDescriptionChange,
                    label = { Text(text = "Tilføj en beskrivelse") },
                    colors = TextFieldDefaults.outlinedTextFieldColors(containerColor = Color.White),
                    minLines = 3,
                    shape = RoundedCornerShape(10)
                )



                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
                ) {
                    //Her skal billednavnet stå/importeres. 
                    Text(text = "HER SKAL BILLEDNAVN STÅ!")
                    Divider(Modifier.width(4.dp))
                    Icon(imageVector = Icons.Default.AddAPhoto,
                        contentDescription = "add photo",
                        modifier = Modifier.clickable {
                           onChooseImage()
                        }
                    )
                }
                val painter = if(wishUiState.img != null) {
                    BitmapPainter(wishUiState.img.asImageBitmap())
                } else {
                    painterResource(id = R.drawable.coffee_machine)
                }
                Image(painter = painter, contentDescription = null)
                // Opret skal add wish, og føre tilbage til wishpage
                // Afbryg skal føre tilbage til wishpage(Dissmiss-Request)
                Row() {
                    TextButton(onClick = onDismiss) {
                        Text(text = "Afbryd")
                    }
                    TextButton(onClick = onAddWish) {
                        Text(text = "Opret", fontWeight = FontWeight.ExtraBold)
                    }
                }
            }
        }
        
    }
}

@Composable
fun ApproveFAB(onClick: () -> Unit, modifier: Modifier = Modifier) {
    FloatingActionButton(onClick = onClick,
        modifier = modifier
            .padding(start = 10.dp, bottom = 10.dp)
            .size(60.dp),
        shape = FloatingActionButtonDefaults.largeShape,
        containerColor = Primary,
        contentColor = Color.Green
    ) {
        Icon(imageVector = Icons.Default.Done, contentDescription = null, modifier = Modifier
            .fillMaxSize()
            .padding(8.dp, end = 10.dp))
    }
}

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
fun WishTopBar(isReserved : Boolean, price : Int){
    val color = if (isReserved) Color.Red else MaterialTheme.colorScheme.secondary
    val text = if (isReserved) "Reserveret" else price.toString()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(35.dp),
        shape = RoundedCornerShape(10),
        colors = CardDefaults.cardColors(color)
    ) {
        Text(
            text = text,
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }

}
@Composable
fun Wish(modifier: Modifier = Modifier, wishUiState: WishUiState, showTopBar: Boolean = false) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(10),
        colors = CardDefaults.cardColors(Primary),
        border = BorderStroke(1.dp, Color.Black)
    ) {
        Box(modifier = Modifier) {
            Column(
            modifier = Modifier
                .background(Primary),
            horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (showTopBar) {
                    WishTopBar(isReserved = wishUiState.isReserved, price = wishUiState.price)
                }
                // TODO("Insert fetched Image and check if it is loadable")
                val painter = if(wishUiState.img != null) {
                    BitmapPainter(wishUiState.img.asImageBitmap())
                } else {
                    painterResource(id = R.drawable.coffee_machine)
                }
                Image(
                    painter = painter,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.8f),
                    contentScale = ContentScale.Crop
                )
                Text(
                    modifier = Modifier.weight(0.2f),
                    text = wishUiState.wishName,
                    color = Color.White,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
            }

        }
    }
}


@Composable
fun WishList(wishes : List<WishUiState>, showTopBar: Boolean = false) {
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
            items(wishes){ wish ->
                Wish(modifier = Modifier
                    .size(width = 150.dp, height = 150.dp)
                    .padding(8.dp),
                    wishUiState = wish,
                    showTopBar = showTopBar)

            }
        }
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
        WishListPage(viewModel = WishListViewModel(WishServiceImpl(firestore = FirebaseFirestore.getInstance(),"7v3WIdoU8FmJFnb3fvA7")))
       //Wish(wishUiState = WishUiState(image = R.drawable._nske2, wishName = "Ting"))
    }
}
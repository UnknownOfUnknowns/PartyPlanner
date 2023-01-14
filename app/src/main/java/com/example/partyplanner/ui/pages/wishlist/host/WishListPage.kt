package com.example.partyplanner.ui.elements

import androidx.compose.foundation.*
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
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.text.isDigitsOnly
import coil.compose.AsyncImage
import com.example.partyplanner.R
import com.example.partyplanner.data.wish.WishServiceImpl
import com.example.partyplanner.ui.pages.wishlist.WishListViewModel
import com.example.partyplanner.ui.pages.wishlist.WishUiState
import com.example.partyplanner.ui.theme.AttendingInfoColor
import com.example.partyplanner.ui.theme.Background
import com.example.partyplanner.ui.theme.Primary
import com.example.partyplanner.ui.theme.SecondaryContainer
import com.google.firebase.firestore.FirebaseFirestore

// Her skal der indsættes routing ved onImageClick hentil WishProductPage
@Composable
fun WishListPage(viewModel: WishListViewModel, navigateToProduct: (WishUiState) -> Unit){
    val uiState = viewModel.uiState.collectAsState()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
    ) {
        Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = CenterHorizontally) {
            NameCardWishList(name = uiState.value.wishListName, modifier = Modifier.size(width = 350.dp, height = 120.dp ))
            Spacer(modifier = Modifier.height(17.dp))
            WishList(uiState.value.wishes, onImageClick = { navigateToProduct(it) })
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
fun GenericOutlineTextField(
    modifier: Modifier,
    keyOption: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
    value: String,
    onValueChange: (String) -> Unit,
    labelText: String,
    color: Color = Color.White,
    shape: Shape = RoundedCornerShape(10),
    isSingleline: Boolean = false,
    minLines: Int = 1,
    maxLines: Int = 1,
) {
    OutlinedTextField(
        keyboardOptions = keyOption,
        value = value, onValueChange = onValueChange,
        modifier = modifier,
        label = { Text(text = labelText) },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            containerColor = color
        ),
        shape = shape,
        singleLine = isSingleline,
        minLines = minLines,
        maxLines = maxLines
    )
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
                val stdModifier = Modifier
                    .align(CenterHorizontally)
                    .padding(vertical = 10.dp)
                Text(
                    text = stringResource(R.string.addWish),
                    modifier = stdModifier,
                    fontSize = 30.sp
                )
                GenericOutlineTextField(modifier = stdModifier,
                    value = wishUiState.wishName,
                    onValueChange = onWishNameChange,
                    labelText = stringResource(id = R.string.wishName)
                )
                GenericOutlineTextField(modifier = stdModifier,
                    value = wishUiState.link,
                    onValueChange = onLinkChange,
                    labelText = stringResource(id = R.string.wishLink))

                GenericOutlineTextField(modifier = stdModifier,
                    keyOption = KeyboardOptions(keyboardType = KeyboardType.Number),
                    value = wishUiState.price.toString(),
                    onValueChange = {
                        if (it.isDigitsOnly()) {
                            onPriceChange(it.toInt())
                        }
                    },
                    labelText = stringResource(id = R.string.wishPrice))

                GenericOutlineTextField(
                    modifier = stdModifier
                        .verticalScroll(rememberScrollState()),
                    value = wishUiState.description,
                    onValueChange = onDescriptionChange,
                    labelText = stringResource(id = R.string.addDesricption),
                    minLines = 3,
                    maxLines = 3
                )

                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
                ) {
                    Text(text = "Vælg Billede:")
                    Divider(Modifier.width(4.dp))
                    Icon(imageVector = Icons.Default.AddAPhoto,
                        contentDescription = stringResource(R.string.addPhoto),
                        modifier = Modifier.clickable {
                           onChooseImage()
                        }
                    )
                }
                val painter = if(wishUiState.newImage != null) {
                    BitmapPainter(wishUiState.newImage.asImageBitmap())
                } else {
                    painterResource(id = R.drawable.no_image_placeholder_svg)
                }
                Image(painter = painter, contentDescription = null, Modifier.size(100.dp))
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
            horizontalAlignment = CenterHorizontally
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
                .align(CenterHorizontally),
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }

}
@Composable
fun Wish(modifier: Modifier = Modifier,
         wishUiState: WishUiState,
         showTopBar: Boolean = false,
         onImageClick: (WishUiState) -> Unit,

) {
    Card(
        modifier = modifier.clickable {onImageClick(wishUiState)},
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
                if(wishUiState.imageLink.isNotEmpty()){
                    AsyncImage(model = wishUiState.imageLink,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(0.8f),
                        contentScale = ContentScale.Crop,
                        placeholder = painterResource(id = R.drawable.coffee_machine))

                } else {
                    Image(
                        painter = painterResource(id = R.drawable.no_image_placeholder_svg),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(0.8f),
                        contentScale = ContentScale.Crop)
                }
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

//Her skal alle ønskerne måske laves clickable så de kan føre til næste side
@Composable
fun WishList(wishes : List<WishUiState>, showTopBar: Boolean = false, onImageClick: (WishUiState) -> Unit) {
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
                    showTopBar = showTopBar,
                    onImageClick = onImageClick
                )

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
        WishListPage(viewModel = WishListViewModel(WishServiceImpl(firestore = FirebaseFirestore.getInstance(),"7v3WIdoU8FmJFnb3fvA7")), navigateToProduct = { })
       //Wish(wishUiState = WishUiState(image = R.drawable._nske2, wishName = "Ting"))
    }
}
package com.example.partyplanner.ui.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.text.isDigitsOnly
import com.example.partyplanner.data.budget.BudgetServiceImpl
import com.example.partyplanner.ui.pages.budget.BudgetListUiState
import com.example.partyplanner.ui.pages.budget.BudgetUiState
import com.example.partyplanner.ui.pages.budget.BudgetViewModel
import com.example.partyplanner.ui.theme.*
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun BudgetPage(viewModel: BudgetViewModel) {
    val uiState = viewModel.uiState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
    ) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Her skal uistate.value.wishlistname indtastes evt fra den allerede
            // kendte i wishlistpage (Samme ui state)
            NameCardWishList(name = "UISTATE WISHLISTNAME",
                modifier = Modifier
                    .size(width = 360.dp, height = 50.dp)
            )
            // Noget galt med denne funktion. Jeg kan ikke "ændr" til true, så der kommer
            // Dialog frem
            Spacer(modifier = Modifier.height(10.dp))
            BudgetInfoTopScreen(budgetListUiState = BudgetListUiState(),
                onMaxBudgetChange = { viewModel.changeBudgetMax(true) }
            )
            if (uiState.value.addTotalBudgetStatus) {
                addMaxBudgetDialog(
                    onDismiss = { viewModel.changeBudgetMax(false) },
                    budgetUiState = uiState.value.newBudget,
                    onAddNewBudgetMax = { viewModel.addBudget() },
                    onNewMaxChange = {viewModel.changeBudgetPrice(it)}
                )
            }

            Spacer(modifier = Modifier.height(10.dp))
            IndividualBudgetList(budgets = uiState.value.budgets)
        }
        if(uiState.value.addBudgetStatus) {
            addBudgetDialog(
                onDismiss = { viewModel.addBudgetStatus(false) },
                budgetUiState = uiState.value.newBudget,
                onNameChange = {viewModel.changeBudgetName(it)},
                onPriceChange = {viewModel.changeBudgetPrice(it)},
                onAddBudgetItem = {viewModel.addBudget()}
            )
        }

        if (!uiState.value.addBudgetStatus) {
            DefaultFAB(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 30.dp), onClick = { viewModel.addBudgetStatus(true)}
            )
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun addMaxBudgetDialog(
    onDismiss: () -> Unit,
    budgetUiState: BudgetUiState,
    onAddNewBudgetMax: () -> Unit,
    onNewMaxChange: (Int) -> Unit,

) {
    Dialog(onDismissRequest = onDismiss) {
        Column(

        ) {
            Text(text = "Tilføj forventet total budget",
                modifier = Modifier
                    .align(CenterHorizontally)
                    .padding(vertical = 10.dp),
                fontSize = 30.sp
            )
            OutlinedTextField(
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                value = budgetUiState.budgetPrice.toString(),
                onValueChange = {
                    if (it.isDigitsOnly()) {
                        onNewMaxChange(it.toInt())
                    }
                },
                modifier = Modifier
                    .align(CenterHorizontally)
                    .padding(vertical = 10.dp),
                label = { Text(text = "Budget for festen") },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    containerColor = Color.White
                ),
                shape = RoundedCornerShape(10)
            )

            Row() {
                TextButton(onClick = onDismiss) {
                    Text(text = "Afbryd")
                }
                TextButton(onClick = onAddNewBudgetMax) {
                    Text(text = "Opret", fontWeight = FontWeight.ExtraBold)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun addBudgetDialog(
    onDismiss: () -> Unit,
    budgetUiState: BudgetUiState,
    onNameChange: (String) -> Unit,
    onPriceChange: (Int) -> Unit,
    onAddBudgetItem: () -> Unit,



) {
    Dialog(onDismissRequest = onDismiss) {
        Card(modifier = Modifier
            .padding(all = 15.dp),
            shape = RoundedCornerShape(10),
            colors = CardDefaults.cardColors(Background)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(horizontal = 10.dp)
            ) {
                Text(text = "Tilføj en udgiftspost",
                modifier = Modifier
                    .align(CenterHorizontally)
                    .padding(vertical = 10.dp),
                fontSize = 30.sp
                )
                OutlinedTextField(value = budgetUiState.budgetName, onValueChange = onNameChange,
                modifier = Modifier
                    .align(CenterHorizontally)
                    .padding(vertical = 10.dp),
                label = { Text(text = "Indtast navn på udgiftsposten")},
                colors = TextFieldDefaults.outlinedTextFieldColors(containerColor = Color.White),
                shape = RoundedCornerShape(10)
                )
                OutlinedTextField(
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    value = budgetUiState.budgetPrice.toString(),
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
                Row() {
                    TextButton(onClick = onDismiss) {
                        Text(text = "Afbryd")
                    }
                    TextButton(onClick = onAddBudgetItem) {
                        Text(text = "Opret", fontWeight = FontWeight.ExtraBold)
                    }
                }
            }
        }
    }
}


@Composable
fun BudgetInfoTopScreen(
    budgetListUiState: BudgetListUiState,
    onMaxBudgetChange: () -> Unit,


){

    Row(
        modifier = Modifier
            .background(PrimaryContainer, shape = RoundedCornerShape(10.dp))
            .fillMaxWidth()
            .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(10.dp))
            .padding(start = 10.dp, end = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically,
    ) {

        Column(

            ) {
            Spacer(modifier = Modifier.height(5.dp))
            Text("Budget: ", color = OnSecondaryContainer)
            Spacer(modifier = Modifier.height(5.dp))
            Text("Budget brugt: ", color = OnSecondaryContainer)
            Spacer(modifier = Modifier.height(5.dp))
            Text("Resterende budget: ", color = OnSecondaryContainer)
            Spacer(modifier = Modifier.height(5.dp))

        }

        Column() {
            // Her skal input fra de rigtige tal i budgettet ind
            Spacer(modifier = Modifier.height(5.dp))
            Text ("1kr", color = OnSecondaryContainer)
            Spacer(modifier = Modifier.height(5.dp))
            Text ("2kr", color = OnSecondaryContainer)
            Spacer(modifier = Modifier.height(5.dp))
            Text ("3kr", color = OnSecondaryContainer)
            Spacer(modifier = Modifier.height(5.dp))
        }
        //Denne knap skal lave en dialog, hvor der kan ændres budget maksimum
        OutlinedButton(
            onClick = { onMaxBudgetChange },
            colors = ButtonDefaults.buttonColors(PrimaryContainer)
        ) {
            Icon(imageVector = Icons.Outlined.Settings, contentDescription = null, modifier = Modifier.size(24.dp), tint = Color.Black)
            Spacer(modifier = Modifier.width(10.dp))
            Text("Ændr", color = Color.Black)

        }
    }
}

@Composable
fun BudgetInformationIndividual(budgetUiState: BudgetUiState){
    Row(
        modifier = Modifier
            .background(PrimaryContainer, shape = RoundedCornerShape(10.dp))
            .fillMaxWidth()
            .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(10.dp))
            .padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        //Her skal vi indtaste navnet oprettet på budgettet
        Text(budgetUiState.budgetName + ": " + budgetUiState.budgetPrice.toString() + "KR",
            color = OnPrimaryContainer,
            fontSize = 25.sp
        )
        Spacer (modifier = Modifier.width(10.dp))
        Icon(imageVector = Icons.Outlined.ArrowDropDown, contentDescription = null, modifier = Modifier.size(30.dp))
    }
}
// Her er Items tilføjet til at kunne gøre igennem listen af forskellige budgets
@Composable
fun IndividualBudgetList(budgets : List<BudgetUiState>){
    LazyColumn(
        modifier = Modifier
            .background(Background, shape = RoundedCornerShape(10.dp))
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp),

    ){
        items(budgets) { budgetInformationIndividual ->
            BudgetInformationIndividual(budgetUiState = budgetInformationIndividual)
        }

        }
        
    }

@Preview(showBackground = true)
@Composable
fun BudgetPagePreview() {
    BudgetPage(viewModel = BudgetViewModel(BudgetServiceImpl(firestore = FirebaseFirestore.getInstance(),"7v3WIdoU8FmJFnb3fvA7")))
}
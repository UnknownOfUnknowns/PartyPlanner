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
import androidx.compose.material.icons.outlined.ExpandLess
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.text.isDigitsOnly
import com.example.partyplanner.R
import com.example.partyplanner.data.budget.BudgetServiceImpl
import com.example.partyplanner.ui.pages.budget.BudgetElementUiState
import com.example.partyplanner.ui.pages.budget.BudgetListUiState
import com.example.partyplanner.ui.pages.budget.BudgetViewModel
import com.example.partyplanner.ui.theme.Background
import com.example.partyplanner.ui.theme.OnPrimaryContainer
import com.example.partyplanner.ui.theme.OnSecondaryContainer
import com.example.partyplanner.ui.theme.PrimaryContainer
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
            horizontalAlignment = CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(10.dp))
            BudgetInfoTopScreen(budgetListUiState = uiState.value,
                onMaxBudgetChange = { viewModel.changeBudgetMax(true) }
            )
            if (uiState.value.addTotalBudgetStatus) {
                SetMaxBudgetDialog(
                    onDismiss = { viewModel.changeBudgetMax(false) },
                    budgetElementUiState = uiState.value,
                    onAddNewBudgetMax = { viewModel.setMaxBudget() },
                    onNewMaxChange = {viewModel.changeTotalBudget(it)}
                )
            }

            Spacer(modifier = Modifier.height(10.dp))
            IndividualBudgetList(budgets = uiState.value.budgetElements,
                onChangeNote = {viewModel.startUpdate(it)},
                onDelete = {viewModel.toggleDelete(it)}
            )
        }
        if(uiState.value.budgetToBeDeleted != null) {
            DeleteDialog(text = "Vil du slette denne omkostning",
                onDismiss = {viewModel.toggleDelete()},
                onDelete = {viewModel.deleteWish()}
            )
        }

        if(uiState.value.setBudgetNote) {
            SetNoteDialog(onDismiss = { viewModel.endUpdate(updateInDatabase = false) },
                note = uiState.value.changeNoteState.newValue,
                onNoteChange = {viewModel.updateNoteValue(it)},
                onSubmit = {viewModel.endUpdate(updateInDatabase = true)},
                price = uiState.value.changeNoteState.newPrice,
                onPriceChange = {viewModel.updatePrice(it)}
            )
        }


        if(uiState.value.addBudgetStatus) {
            AddBudgetDialog(
                onDismiss = { viewModel.addBudgetStatus(false) },
                budgetElementUiState = uiState.value.newBudgetElement,
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
// Fejl i denne funktion. Den skal udvide sig sammen med den indtastede tekst.
// Fejl i alle dialogs, undtagen GuestPage, da den er sat singleline = true

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SetNoteDialog(
    onDismiss: () -> Unit,
    note: String,
    onNoteChange: (String) -> Unit,
    price : Int,
    onPriceChange: (Int) -> Unit,
    onSubmit : () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(modifier = Modifier
            .padding(all = 15.dp),
            shape = RoundedCornerShape(10),
            colors = CardDefaults.cardColors(Background)
        ) {
            Column(
                horizontalAlignment = CenterHorizontally,
                modifier = Modifier
                    .padding(horizontal = 10.dp)
            ) {
                Text(text = stringResource(R.string.budgetDialogDescriptionText),
                    modifier = Modifier
                        .align(CenterHorizontally)
                        .padding(vertical = 10.dp),
                    fontSize = 30.sp
                )

                OutlinedTextField(
                    value = if(price == 0) "" else price.toString(),
                    onValueChange = {
                        if(it.isEmpty()) {
                            onPriceChange(0)
                        } else if (it.isDigitsOnly()) {
                            onPriceChange(it.toInt())
                        }
                    },
                    modifier = Modifier
                        .align(CenterHorizontally)
                        .padding(vertical = 10.dp),
                    label = { Text(text = stringResource(id = R.string.wishPrice))},
                    colors = TextFieldDefaults.outlinedTextFieldColors(containerColor = Color.White),
                    shape = RoundedCornerShape(10)
                )

                OutlinedTextField(value = note, onValueChange = onNoteChange,
                    modifier = Modifier
                        .align(CenterHorizontally)
                        .padding(vertical = 10.dp),
                    label = { Text(text = stringResource(R.string.budgetChooseDescriptionTextField))},
                    colors = TextFieldDefaults.outlinedTextFieldColors(containerColor = Color.White),
                    shape = RoundedCornerShape(10)
                )
                Row() {
                    TextButton(onClick = onDismiss) {
                        Text(text = stringResource(R.string.interrupt))
                    }
                    TextButton(onClick = onSubmit) {
                        Text(text = stringResource(R.string.create), fontWeight = FontWeight.ExtraBold)
                    }
                }
            }
        }
    }
}


// Den sætter max budget for festen ind som en budgetpost
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SetMaxBudgetDialog(
    onDismiss: () -> Unit,
    budgetElementUiState: BudgetListUiState,
    onAddNewBudgetMax: () -> Unit,
    onNewMaxChange: (Int) -> Unit,

    ) {
    Dialog(onDismissRequest = onDismiss) {
        Card(modifier = Modifier
            .padding(all = 15.dp),
            shape = RoundedCornerShape(10)
        ) {
            Column(

            ) {
                Text(
                    text = stringResource(R.string.budgetAddExpectedTotalBudgetText),
                    modifier = Modifier
                        .align(CenterHorizontally)
                        .padding(vertical = 10.dp),
                    fontSize = 30.sp
                )
                OutlinedTextField(
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    value = budgetElementUiState.newBudgetMax.toString(),
                    onValueChange = {
                        if (it.isDigitsOnly() && it.isNotEmpty()) {
                            onNewMaxChange(it.toInt())
                        }
                    },
                    modifier = Modifier
                        .align(CenterHorizontally)
                        .padding(vertical = 10.dp),
                    label = { Text(text = stringResource(R.string.budgetForPartyTextField)) },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        containerColor = Color.White
                    ),
                    shape = RoundedCornerShape(10)
                )

                Row() {
                    TextButton(onClick = onDismiss) {
                        Text(text = stringResource(R.string.interrupt))
                    }
                    TextButton(onClick = onAddNewBudgetMax) {
                        Text(text = stringResource(R.string.change), fontWeight = FontWeight.ExtraBold)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddBudgetDialog(
    onDismiss: () -> Unit,
    budgetElementUiState: BudgetElementUiState,
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
                val stdModifier = Modifier
                    .align(CenterHorizontally)
                    .padding(vertical = 10.dp)

                Text(text = stringResource(R.string.budgetAddExpensePostText),
                    modifier = Modifier
                        .align(CenterHorizontally)
                        .padding(vertical = 10.dp),
                    fontSize = 25.sp)

                GenericOutlineTextField(modifier = stdModifier,
                    value = budgetElementUiState.budgetName,
                    onValueChange = onNameChange,
                    labelText = stringResource(id = R.string.budgetChooseNameForExpensePost),
                    isSingleline = true
                )


                GenericOutlineTextField(
                    keyOption = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = stdModifier,
                    value = if(budgetElementUiState.budgetPrice != 0) budgetElementUiState.budgetPrice.toString() else "",
                    onValueChange = {
                        if(it.isEmpty()) {
                            onPriceChange(0)
                        } else if (it.isDigitsOnly()) {
                            onPriceChange(it.toInt())
                        }
                    },
                    labelText = stringResource(id = R.string.wishPrice))

                Row() {
                    TextButton(onClick = onDismiss) {
                        Text(text = stringResource(R.string.interrupt))
                    }
                    TextButton(onClick = onAddBudgetItem) {
                        Text(text = stringResource(R.string.create), fontWeight = FontWeight.ExtraBold)
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
        Column{
            Spacer(modifier = Modifier.height(5.dp))
            Text(stringResource(R.string.budget), color = OnSecondaryContainer)
            Spacer(modifier = Modifier.height(5.dp))
            Text(stringResource(R.string.budgetUsed), color = OnSecondaryContainer)
            Spacer(modifier = Modifier.height(5.dp))
            Text(stringResource(R.string.remainingBudget), color = OnSecondaryContainer)
            Spacer(modifier = Modifier.height(5.dp))

        }

        Column() {
            // Her skal input fra de rigtige tal i budgettet ind
            Spacer(modifier = Modifier.height(5.dp))
            Text (budgetListUiState.budgetMax.toString() + stringResource(R.string.valuta), color = OnSecondaryContainer)
            Spacer(modifier = Modifier.height(5.dp))
            Text (budgetListUiState.budgetSpent.toString()+ stringResource(R.string.valuta), color = OnSecondaryContainer)
            Spacer(modifier = Modifier.height(5.dp))
            Text (budgetListUiState.budgetLeft.toString()+ stringResource(R.string.valuta), color = OnSecondaryContainer)
            Spacer(modifier = Modifier.height(5.dp))
        }
        //Denne knap skal lave en dialog, hvor der kan ændres budget maksimum
        OutlinedButton(
            onClick = { onMaxBudgetChange() },
            colors = ButtonDefaults.buttonColors(PrimaryContainer)
        ) {
            Icon(imageVector = Icons.Outlined.Settings, contentDescription = null, modifier = Modifier.size(24.dp), tint = Color.Black)
            Spacer(modifier = Modifier.width(10.dp))
            Text(stringResource(R.string.edit), color = Color.Black)

        }
    }
}
@Composable
private fun InfoDropDownIndividualBudget(
    expanded: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(onClick = onClick, ) {
        Icon(imageVector = if(expanded) {Icons.Outlined.ExpandLess} else {Icons.Outlined.ArrowDropDown},
            contentDescription = stringResource(R.string.budgetDropDown),
            modifier = modifier.size(30.dp)
        )
    }
}

@Composable
fun BudgetInformationIndividual(budgetElementUiState: BudgetElementUiState,
                                onChangeNote : (BudgetElementUiState) -> Unit,
                                onDelete : (BudgetElementUiState) -> Unit
){
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .background(PrimaryContainer, shape = RoundedCornerShape(10.dp))
            .fillMaxWidth()
            .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(10.dp))
            .padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 10.dp),
    ) {
        //Her skal vi indtaste navnet oprettet på budgettet
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween){
            Text(
                budgetElementUiState.budgetName + ": " + budgetElementUiState.budgetPrice.toString() + stringResource(R.string.valuta),
                color = OnPrimaryContainer,
                fontSize = 25.sp
            )
            InfoDropDownIndividualBudget(expanded = expanded, onClick = { expanded = !expanded })
        }
        if (expanded) {
            Text(text = stringResource(id = R.string.budgetDialogDescriptionText), fontWeight = FontWeight.Bold)
            Text(text = budgetElementUiState.budgetNote)
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Button(onClick = {onChangeNote(budgetElementUiState)}) {
                    Text(text = stringResource(R.string.changeDesription))
                }
                TextButton(onClick = {onDelete(budgetElementUiState)}) {
                    Text(text = stringResource(R.string.delete))
                }
            }
        }
    }

}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpandedTextField(
    budgetUiState: BudgetElementUiState,
    onNoteChange: (String) -> Unit,

) {
    OutlinedTextField(
        modifier = Modifier
            .padding(vertical = 10.dp),
        value = budgetUiState.budgetNote,
        onValueChange = onNoteChange,
        label = { Text(text = "Tilføj en beskrivelse") },
        colors = TextFieldDefaults.outlinedTextFieldColors(containerColor = Color.White),
        minLines = 3,
        shape = RoundedCornerShape(10)
    )
}


// Her er Items tilføjet til at kunne gøre igennem listen af forskellige budgets
@Composable
fun IndividualBudgetList(budgets : List<BudgetElementUiState>,
                         onChangeNote: (BudgetElementUiState) -> Unit,
                         onDelete: (BudgetElementUiState) -> Unit){
    LazyColumn(
        modifier = Modifier
            .background(Background, shape = RoundedCornerShape(10.dp))
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp),

    ){
        items(budgets) { budgetInformationIndividual ->
            BudgetInformationIndividual(budgetElementUiState = budgetInformationIndividual, onChangeNote, onDelete)
        }

    }
        
}

@Preview(showBackground = true)
@Composable
fun BudgetPagePreview() {
    BudgetPage(viewModel = BudgetViewModel(BudgetServiceImpl(firestore = FirebaseFirestore.getInstance(),"7v3WIdoU8FmJFnb3fvA7")))
}
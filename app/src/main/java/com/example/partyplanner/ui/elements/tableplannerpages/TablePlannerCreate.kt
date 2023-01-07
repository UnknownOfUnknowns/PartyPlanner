package com.example.partyplanner.ui.elements.tableplannerpages



import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import com.example.partyplanner.ui.pages.partiesList.NewPartyViewModel
import com.example.partyplanner.ui.theme.Background
import com.example.partyplanner.ui.theme.Primary

@Composable
fun CreateTable(viewModel: TablePlannerViewModel){

    val uiState = viewModel.uiState.collectAsState()
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){

        Text (text = "Opret et nyt bord", fontSize = 30.sp)

        Spacer (modifier = Modifier.height(30.dp))

        DropdownTableMenu(uiState.value.tableType, onChangeTableType = { viewModel.updateTableType(it)})

        Spacer (modifier = Modifier.height(30.dp))

        NumberOfSeatsDropDown(uiState.value.tableSeats, onChangeTableSeat = {viewModel.updateTableSeats(it)})

        Spacer (modifier = Modifier.height(30.dp))

        CreateTableButton()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownTableMenu(selectedOption : TableType, onChangeTableType: (String) -> Unit) {

    val options = listOf("Kvadratisk bord", "Rektangulært bord", "Rundt bord")
    var expanded by remember { mutableStateOf(false) }
    val selectedOptionText = when (selectedOption) {
        TableType.SQUARETABLE -> "Kvadratisk bord"
        TableType.REKTTABLE -> "Rektangulært bord"
        else -> "Rundt bord"
    }
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {expanded = !expanded},
    ) {
        TextField(
            modifier = Modifier.menuAnchor(),
            readOnly = true,
            value = selectedOptionText,
            onValueChange = {},
            label = { Text("Vælg bordtype") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)},
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = {expanded = false},
        ) {
            options.forEach {selectionOption ->
                DropdownMenuItem(
                    text = { Text(selectionOption) },
                    onClick = {
                        onChangeTableType(selectionOption)
                        expanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NumberOfSeatsDropDown(tableSeats: Int, onChangeTableSeat: (Int) -> Unit){
    val options = listOf("4", "6", "8", "10", "12")
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {expanded = !expanded},
    ) {
        TextField(
            modifier = Modifier.menuAnchor(),
            readOnly = true,
            value = tableSeats.toString(),
            onValueChange = {},
            label = { Text("Vælg antal siddende ved bord") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)},
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = {expanded = false},
        ) {
            options.forEach {selectionOption ->
                DropdownMenuItem(
                    text = { Text(selectionOption) },
                    onClick = {
                        onChangeTableSeat(selectionOption.toInt())
                        expanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                )
            }
        }
    }
}

@Composable
fun CreateTableButton(){
    ElevatedButton(onClick = { /*TODO*/ },
        colors = ButtonDefaults.buttonColors(Primary)
    ) {
        Text(text = "Opret bord", color = Color.White)
    }
}

@Preview
@Composable
fun PreviewScreen(){
 CreateTable(viewModel = TablePlannerViewModel())
}
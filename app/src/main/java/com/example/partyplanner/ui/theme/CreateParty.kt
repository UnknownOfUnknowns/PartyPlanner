package com.example.partyplanner.ui.theme

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Preview(showBackground = true)
@Composable
fun StartPartyCreation() {
    FadeBackground() {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,

        ) {
            Spacer(modifier = Modifier.height(30.dp))
            Text(text = "Hvilken type fest vil du holde?",
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(20.dp))
            ChoosePartyDropDown()

            NextButton()
        }

    }

}
@Composable
fun NextButton() {
    Button(onClick = { /*TODO*/ },
        ) {
        Text("Næste")
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChoosePartyDropDown()
{
    val options = listOf<String>("Fødselsdag", "Bryllup", "Konfirmation", "Dåb", "Anden begivenhed")
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf(options[0]) }
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {expanded = !expanded},
    ) {
        TextField(
            modifier = Modifier.menuAnchor(),
            readOnly = true,
            value = selectedOptionText,
            onValueChange = {},
            label = { Text("Vælg begivenhed")},
            trailingIcon = {ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)},
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = {expanded = false},
        ) {
            options.forEach {selectionOption ->
                DropdownMenuItem(
                    text = {Text(selectionOption)},
                    onClick = {
                        selectedOptionText = selectionOption
                        expanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                )
            }
        }
    }
}
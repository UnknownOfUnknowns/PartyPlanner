package com.example.partyplanner.ui.theme

import android.app.DatePickerDialog
import android.content.Context
import android.widget.DatePicker
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.partyplanner.R
import java.util.*


@OptIn(ExperimentalMaterial3Api::class)
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
            HostAdder()
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
fun HostAdder(){
    val hosts = mutableListOf("")
    Box{
        LazyColumn() {
            items(items = hosts, itemContent = { item ->
                TextField(value = item,
                    onValueChange = {
                        val index = hosts.indexOf(item)
                        if(index >= 0) {
                            hosts[index] = it
                        }
                    }
                )
            })
        }
        OutlinedButton(onClick = {hosts.add("")}, modifier = Modifier.align(Alignment.BottomCenter)) {
            Text(text = "Tilføj vært")    
        }
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChoosePartyDropDown()
{
    val options = listOf("Fødselsdag", "Bryllup", "Konfirmation", "Dåb", "Anden begivenhed")
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

@Preview(showBackground = true)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SetPartyDataOnCreation() {

    FadeBackground() {

        Column(
            modifier =
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Spacer(modifier = Modifier.height(50.dp))
            var text by remember { mutableStateOf("") }

            TextField(
                value = text,
                onValueChange = { text = it },
                label = { Text("Vælg titel på begivenheden") },
                singleLine = true
            )

            Spacer(modifier = Modifier.height(25.dp))

            showDatePicker(LocalContext.current)

            Spacer(modifier = Modifier.height(25.dp))

            TextField(
                value = text,
                onValueChange = { text = it },
                label = { Text("Adresse på begivenheden") },
                singleLine = true
            )

            Spacer(modifier = Modifier.height(25.dp))

            TextField(
                value = text,
                onValueChange = { text = it },
                label = { Text("Post nr") },
                singleLine = true
            )

            Spacer(modifier = Modifier.height(25.dp))

            TextField(
                value = text,
                onValueChange = { text = it },
                label = { Text("By") },
                singleLine = true
            )

            Spacer(modifier = Modifier.height(25.dp))

            Button(
                onClick = { },
            ) {
                Text("Opret begivenhed")
            }
        }
    }
}


@Composable
fun showDatePicker(context: Context){
    val year: Int
    val month: Int
    val day: Int

    val calendar = Calendar.getInstance()
    year = calendar.get(Calendar.YEAR)
    month = calendar.get(Calendar.MONTH)
    day = calendar.get(Calendar.DAY_OF_MONTH)
    calendar.time = Date()

    val date = remember {
        mutableStateOf("")
    }
    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, year: Int,  month: Int, dayOfMonth: Int ->
        date.value = "$dayOfMonth/$month/$year"
        }, year, month, day
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Vælg dato: ${date.value}"
        )

        Spacer(modifier = Modifier.size(16.dp))

        Button(onClick={
            datePickerDialog.show()
        }) {
            Text(text = "Åben datoer")
        }
    }
}


@Composable
fun CreatePartyConfirmation(){
    FadeBackground() {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        )
        {
            CreatedCompletedText("Tillykke!", "Din begivenhed er nu oprettet")
                Image(
                painter = painterResource(id = R.drawable.ic_task_completed),
                contentDescription = null
            )

        }
    }
}

@Composable
fun CreatedCompletedText(createdcompletedmessage1: String, createdcompletedmessage2: String){
    Column (){

        Text(
            text = createdcompletedmessage1,
            fontSize = 50.sp,
            modifier = Modifier
                .wrapContentWidth(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(25.dp))

    }

    Text(
        text = createdcompletedmessage2,
        fontSize = 25.sp,
        modifier = Modifier
            .wrapContentWidth(Alignment.CenterHorizontally)
    )
    Spacer(modifier = Modifier.height(25.dp))
}
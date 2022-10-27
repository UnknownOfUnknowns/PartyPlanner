package com.example.partyplanner.ui.theme

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.widget.DatePicker
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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


@Composable
fun StartPartyCreation(onNextButtonClick: () -> Unit) {
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
            Button(onClick = onNextButtonClick) {
                Text("Næste")
            }
        }

    }

}


@Composable
fun NextButton() {
    Button(onClick = { /*TODO*/ }) {
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SetPartyDataOnCreation(onNextButtonClick: () -> Unit) {

    FadeBackground() {

        Column(
            modifier =
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Spacer(modifier = Modifier.height(30.dp))
            var text by remember { mutableStateOf("") }

            TextField(
                value = text,
                onValueChange = { text = it },
                label = { Text("Vælg titel på begivenheden") },
                singleLine = true
            )

            Spacer(modifier = Modifier.height(10.dp))

            showDatePicker(LocalContext.current)

            Spacer(modifier = Modifier.height(10.dp))

            ShowTimePicker(LocalContext.current)

            Spacer(modifier = Modifier.height(10.dp))

            TextField(
                value = text,
                onValueChange = { text = it },
                label = { Text("Adresse på begivenheden") },
                singleLine = true
            )

            Spacer(modifier = Modifier.height(10.dp))
Row (Modifier.padding(start = 25.dp, end = 25.dp)
) {
    TextField(
        value = text,
        onValueChange = { text = it },
        label = { Text("Post nr") },
        singleLine = true,
        maxLines =4,
        modifier = Modifier.weight(0.3f)
    )

    Spacer(modifier = Modifier.width(10.dp))

    TextField(
        value = text,
        onValueChange = { text = it },
        label = { Text("By") },
        singleLine = true,
        modifier = Modifier.weight(0.7f)
    )
}

            Spacer(modifier = Modifier.height(25.dp))

            Button(
                onClick = onNextButtonClick,
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
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        Button(onClick={
            datePickerDialog.show()
        }) {
            Text(text = "Vælg dato")
        }
        Spacer(modifier = Modifier.size(5.dp))

        Text(
            text = "Valgt dato: ${date.value}"
        )
    }
}

@Composable
fun ShowTimePicker(context: Context) {
    val mContext = LocalContext.current

    val mCalendar = Calendar.getInstance()
    val mHour = mCalendar[Calendar.HOUR_OF_DAY]
    val mMinute = mCalendar[Calendar.MINUTE]

    val mTime = remember { mutableStateOf("") }

    val mTimePickerDialog = TimePickerDialog(
        mContext,
        { _, mHour: Int, mMinute: Int ->
            mTime.value = "$mHour:$mMinute"
        }, mHour, mMinute, false
    )
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        Button(
            onClick = { mTimePickerDialog.show() }
            )
         {
            Text(text = "Vælg starttidspunkt")
        }
        Spacer(modifier = Modifier.size(5.dp))

        Text(text = "Valgt tidspunkt: ${mTime.value}", fontSize = 15.sp)

    }
}


@Composable
fun CreatePartyConfirmation(){

        Column(
            modifier = Modifier.fillMaxSize().background(Background),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,

        )
        {
            CreatedCompletedText("Tillykke!", "Din begivenhed er nu oprettet")
                Image(
                painter = painterResource(id = R.drawable.ic_task_completed),
                contentDescription = null
            )

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
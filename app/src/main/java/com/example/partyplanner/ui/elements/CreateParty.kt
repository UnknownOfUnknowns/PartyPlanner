package com.example.partyplanner.ui.elements

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.partyplanner.R
import com.example.partyplanner.ui.pages.partiesList.NewPartyViewModel
import com.example.partyplanner.ui.state.PartyCoreInfoUiState
import com.example.partyplanner.ui.state.PartyType
import com.example.partyplanner.ui.theme.Background
import java.util.*


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StartPartyCreation(onNextButtonClick: () -> Unit, viewModel: NewPartyViewModel) {

    val uiState = viewModel.uiState.collectAsState()

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

            ChoosePartyDropDown(uiState.value.partyType, onChangePartyType = {})

            //For now the app can only handle one host
            Spacer(modifier = Modifier.height(20.dp))


            TextField(value = "", onValueChange = {},
                label = { Text(text = stringResource(R.string.whoHostsTheParty))}
                )

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
fun ChoosePartyDropDown(selectedOption : PartyType, onChangePartyType: (String) -> Unit)
{

    val options = listOf("Fødselsdag", "Bryllup", "Konfirmation", "Dåb", "Anden begivenhed")
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText = when (selectedOption){
        PartyType.BIRTHDAY -> "Fødselsdag"
        PartyType.WEDDING -> "Bryllup"
        PartyType.CONFIRMATION -> "Konfirmation"
        PartyType.BAPTISM -> "Dåb"
        else -> "Anden begivenhed"
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
                        onChangePartyType(selectionOption)
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
fun SetPartyDataOnCreation(
    onNextButtonClick: () -> Unit,
    party: PartyCoreInfoUiState,
    setName: (String) -> Unit,
    setAddress: (String) -> Unit,
    setZip: (String) -> Unit,
    setCity: (String) -> Unit,
) {

    FadeBackground() {

        Column(
            modifier =
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Spacer(modifier = Modifier.height(30.dp))


            TextField(
                value = party.name,
                onValueChange = setName,
                label = { Text("Vælg titel på begivenheden") },
                singleLine = true
            )

            Spacer(modifier = Modifier.height(10.dp))

            showDatePicker(LocalContext.current)

            Spacer(modifier = Modifier.height(10.dp))

            ShowTimePicker(LocalContext.current)

            Spacer(modifier = Modifier.height(10.dp))

            TextField(
                value = party.address,
                onValueChange = setAddress,
                label = { Text("Adresse på begivenheden") },
                singleLine = true
            )

            Spacer(modifier = Modifier.height(10.dp))
            Row (Modifier.padding(start = 25.dp, end = 25.dp)) {
                TextField(
                    value = party.zip,
                    onValueChange = setZip,
                    label = { Text("Post nr") },
                    singleLine = true,
                    maxLines =4,
                    modifier = Modifier.weight(0.3f)
                )

                Spacer(modifier = Modifier.width(10.dp))

                TextField(
                    value = party.city,
                    onValueChange = setCity,
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
            modifier = Modifier
                .fillMaxSize()
                .background(Background),
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
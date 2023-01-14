package com.example.partyplanner.ui.elements

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
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
            val stdModifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = stringResource(R.string.choosePartyTypeText),
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(20.dp))

            ChoosePartyDropDown(uiState.value.partyType, onChangePartyType = {viewModel.updatePartyType(it)})

            //For now the app can only handle one host
            Spacer(modifier = Modifier.height(20.dp))

            GenericOutlineTextField(modifier = stdModifier,
                value = uiState.value.partyHost,
                onValueChange = {viewModel.changeHostName(it)},
                labelText = stringResource(id = R.string.whoHostsTheParty)
            )


            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = onNextButtonClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                shape = RoundedCornerShape(10)
            ) {
                Text(stringResource(R.string.createPartyNextButtonText))
            }
        }

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
            Text(text = stringResource(R.string.createPartyAddHostText))
        }
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChoosePartyDropDown(selectedOption : PartyType, onChangePartyType: (String) -> Unit)
{

    val options = listOf(stringResource(R.string.birthday),
        stringResource(R.string.wedding),
        stringResource(R.string.confirmation),
        stringResource(R.string.baptism),
        stringResource(R.string.otherEvent)
    )
    var expanded by remember { mutableStateOf(false) }
    val selectedOptionText = when (selectedOption){
        PartyType.BIRTHDAY -> stringResource(R.string.birthday)
        PartyType.WEDDING -> stringResource(R.string.wedding)
        PartyType.CONFIRMATION -> stringResource(R.string.confirmation)
        PartyType.BAPTISM -> stringResource(R.string.baptism)
        else -> stringResource(R.string.otherEvent)
    }
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {expanded = !expanded},
    ) {
        OutlinedTextField(
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
                .padding(10.dp),
            shape = RoundedCornerShape(10),
            readOnly = true,
            value = selectedOptionText,
            onValueChange = {},
            label = { Text(stringResource(R.string.choosePartyTypeDropDownText))},
            trailingIcon = {ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)},
            colors = TextFieldDefaults.outlinedTextFieldColors(containerColor = Color.White),
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
    setDate: (Date) -> Unit,
) {
    val stdModifier = Modifier
        .fillMaxWidth()
        .padding(10.dp)
    val context = LocalContext.current

    FadeBackground() {

        Column(
            modifier =
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Spacer(modifier = Modifier.height(30.dp))

            GenericOutlineTextField(modifier = stdModifier,
                value = party.name,
                onValueChange = {
                    if(it.length <= 50) {
                        setName(it)
                    } else {
                        Toast.makeText(context, "Maksimum 50 tegn", Toast.LENGTH_SHORT).show()
                    }
                },
                labelText = stringResource(id = R.string.choosePartyTitleText),
            isSingleline = true
            )

            Spacer(modifier = Modifier.height(10.dp))

            showDatePicker(party.date, setDate)

            Spacer(modifier = Modifier.height(10.dp))

            ShowTimePicker(party.date, setDate)

            Spacer(modifier = Modifier.height(10.dp))

            GenericOutlineTextField(modifier = stdModifier,
                value = party.address,
                onValueChange = {
                    if(it.length <= 50) {
                        setAddress(it)
                    } else {
                        Toast.makeText(context, "Maksimum 50 tegn", Toast.LENGTH_SHORT).show()
                    }
                },
                labelText = stringResource(id = R.string.chooseAdressOnCreatePartyTextField),
            isSingleline = true
            )


            Spacer(modifier = Modifier.height(10.dp))
            Row (
                Modifier
                    .fillMaxWidth()) {
                GenericOutlineTextField(
                    keyOption = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = stdModifier.weight(0.3f),
                    value = party.zip,
                    onValueChange = {
                        if(it.length <= 4) {
                            setZip(it)
                        } else {
                            Toast.makeText(context, "Maksimum 4 tal", Toast.LENGTH_SHORT).show()
                        }
                    },
                    labelText = stringResource(id = R.string.chooseZipCodeOnCreatePartyText),
                    isSingleline = true
                )

                GenericOutlineTextField(modifier = stdModifier
                    .weight(0.7f)
                    .padding(start = 10.dp),
                    value = party.city,
                    onValueChange = {
                        if(it.length <= 25) {
                            setCity(it)
                        } else {
                            Toast.makeText(context, "Maksimum 25 tegn", Toast.LENGTH_SHORT).show()
                        }
                    },
                    labelText = stringResource(id = R.string.chooseCityOnCreatePartyText),
                isSingleline = true
                )
            }

            Spacer(modifier = Modifier.height(25.dp))

            Button(
                onClick = onNextButtonClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                shape = RoundedCornerShape(10)
            ) {
                Text(stringResource(R.string.createPartyButtonText))
            }
        }
    }
}


@Composable
fun showDatePicker(date : Date, setDate : (Date) -> Unit){

    val calendar = Calendar.getInstance()
    calendar.time = date
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)



    val datePickerDialog = DatePickerDialog(
        LocalContext.current,
        { _,y : Int, m : Int, d : Int ->
            calendar.set(Calendar.DAY_OF_MONTH, d)
            calendar.set(Calendar.MONTH, m)
            calendar.set(Calendar.YEAR, y)
            setDate(calendar.time)
        }, year, month, day
    )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { datePickerDialog.show() }
                .padding(10.dp)
                .background(Color.White)
                .border(
                    width = 1.dp,
                    shape = RoundedCornerShape(10),
                    color = Color.Gray,
                ),

        ){

            Row(
                modifier = Modifier.padding(15.dp),
            ){
                Text (
                    text = stringResource(R.string.pickDate) + "${day} /" + " ${month+1} -" + " ${year}",
                    modifier = Modifier.weight(1F),
                    color = Color.DarkGray
                )

                Icon(Icons.Default.DateRange, contentDescription = stringResource(R.string.dateRangeLogoDescription))
            }
        }
}

@Composable
fun ShowTimePicker(date : Date, setDate : (Date) -> Unit) {
    val mContext = LocalContext.current

    val mCalendar = Calendar.getInstance()
    mCalendar.time = date
    val mHour = mCalendar[Calendar.HOUR_OF_DAY]
    val mMinute = mCalendar[Calendar.MINUTE]


    val mTimePickerDialog = TimePickerDialog(
        mContext,
        { _,  h : Int, m : Int  ->
            mCalendar.set(Calendar.HOUR_OF_DAY, h)
            mCalendar.set(Calendar.MINUTE, m)
            setDate(mCalendar.time)
        }, mHour, mMinute, true
    )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { mTimePickerDialog.show() }
                .padding(10.dp)
                .background(Color.White)
                .border(
                    width = 1.dp,
                    shape = RoundedCornerShape(10),
                    color = Color.Gray,
                ),
        ){

            Row(
                modifier = Modifier.padding(15.dp),
            ){
                Text (
                    text = stringResource(R.string.pickStartTime) + " ${mHour}:$mMinute",
                    modifier = Modifier.weight(1F),
                    color = Color.DarkGray
                )

                Icon(Icons.Default.Alarm, contentDescription = stringResource(R.string.alarmLogoDescription))
            }
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
            CreatedCompletedText(stringResource(R.string.congratulationsOnCreatePartyTitleText), stringResource(
                            R.string.congratulationsOnCreatePartyMessageText)
                        )
                Image(
                painter = painterResource(id = R.drawable.ic_task_completed),
                contentDescription = stringResource(R.string.completeLogoOnCreateParty)
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
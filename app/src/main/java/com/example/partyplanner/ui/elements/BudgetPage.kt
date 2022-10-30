package com.example.partyplanner.ui.elements

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.AlertDialogDefaults.shape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.partyplanner.ui.theme.Background
import com.example.partyplanner.ui.theme.BudgetBoxColor
import com.example.partyplanner.ui.theme.PrimaryContainer

@Composable
fun BudgetInfoTopScreen(){

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
            Text("Budget: ")
            Spacer(modifier = Modifier.height(5.dp))
            Text("Budget brugt: ")
            Spacer(modifier = Modifier.height(5.dp))
            Text("Resterende budget: ")
            Spacer(modifier = Modifier.height(5.dp))

        }

        Column() {
            // Her skal input fra de rigtige tal i budgettet ind
            Spacer(modifier = Modifier.height(5.dp))
            Text ("1kr")
            Spacer(modifier = Modifier.height(5.dp))
            Text ("2kr")
            Spacer(modifier = Modifier.height(5.dp))
            Text ("3kr")
            Spacer(modifier = Modifier.height(5.dp))
        }

        Button(
            onClick = {  },
        ) {
            Icon(imageVector = Icons.Outlined.Settings, contentDescription = null, modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.width(10.dp))
            Text("Ændr")

        }
    }
}

@Composable
fun PreviewIndividualBudgetInfoBox(){
    Row(
        modifier = Modifier
            .background(PrimaryContainer, shape = RoundedCornerShape(10.dp))
            .fillMaxWidth()
            .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(10.dp))
            .padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        //Her skal vi indtaste navnet oprettet på budgettet
        Text("budgetnavn", fontSize = 25.sp)
        Spacer (modifier = Modifier.width(10.dp))
        //Her skal vi indtaste beløbet som er afsat til denne post
        Text("500kr", fontSize = 25.sp)
        Spacer (modifier = Modifier.width(10.dp))
        Icon(imageVector = Icons.Outlined.ArrowDropDown, contentDescription = null, modifier = Modifier.size(30.dp))
    }
}

@Composable
fun IndividualBudgetInfoBox(){
    LazyColumn(
        modifier = Modifier
            .background(PrimaryContainer, shape = RoundedCornerShape(10.dp))
            .fillMaxWidth()
            .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(10.dp))
            .padding(start = 10.dp, end = 10.dp),
    ){


        }
        
    }

@Preview
@Composable
fun BudgetPagePreview() {
    Box(
        modifier = Modifier
            .background(Background)
            .fillMaxSize()
            .padding(top = 10.dp, start = 10.dp, end = 10.dp, bottom = 10.dp)


    ) {
        Column() {


            BudgetInfoTopScreen()
            Spacer(modifier = Modifier.height(15.dp))
            IndividualBudgetInfoBox()
            PreviewIndividualBudgetInfoBox()
        }
        DefaultFAB(modifier = Modifier.align(Alignment.BottomEnd), onClick = {})
    }
}
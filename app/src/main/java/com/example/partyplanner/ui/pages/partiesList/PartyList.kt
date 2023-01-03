package com.example.partyplanner.ui.elements


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.partyplanner.data.party.Party
import com.example.partyplanner.ui.pages.partiesList.NewPartyViewModel
import com.example.partyplanner.ui.theme.Background
import com.example.partyplanner.ui.theme.OnPrimary


@Composable
fun PartyListAndCreate(viewModel: NewPartyViewModel, onAddButton: () -> Unit, onEdit: () -> Unit) {
    val parties by viewModel.parties.collectAsState(initial = listOf())

    Box {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Background),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(parties){ party ->
                val partyIndex = parties.indexOf(party)
                val alpha : Double = (partyIndex+6)*(1.0/(parties.size+5))
                PartyCard(partyInfo = party,
                    onClick = onEdit,
                    backgroundColor = Color(30/256f, 0f, 93/256f, alpha.toFloat()))
            }

        }

        DefaultFAB(modifier = Modifier.align(Alignment.BottomEnd), onClick = onAddButton)
    }

}
@Composable
fun DefaultFAB(modifier: Modifier = Modifier, onClick: () -> Unit) {
    FloatingActionButton(onClick = onClick,
        modifier = modifier
            .padding(end = 10.dp, bottom = 10.dp)
            .size(60.dp),
        shape = FloatingActionButtonDefaults.largeShape,
        containerColor = Color.Green
    ) {
        Icon(Icons.Default.Add, null, modifier = Modifier.fillMaxSize(), tint = Color.White)
    }

}

@Composable
fun PartyCard(partyInfo: Party, backgroundColor : Color = Color.White, onClick: () -> Unit) {
    Spacer(modifier = Modifier.height(20.dp))
    Card(
        modifier = Modifier
            .size(width = 370.dp, height = 100.dp),
        shape = RoundedCornerShape(15.dp)

    ) {
        Column(
            Modifier
                .fillMaxSize()
                .background(backgroundColor)) {
            Text(partyInfo.name, fontSize = 25.sp, fontWeight = FontWeight.Bold, color = OnPrimary)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(partyInfo.address, color = OnPrimary)
                Button(onClick = onClick
                ) {
                    Text("Rediger i begivenhed")
                }
            }
        }
    }
}

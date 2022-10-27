package com.example.partyplanner.ui.theme


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.partyplanner.ui.state.PartiesUiState
import com.example.partyplanner.ui.state.PartyCoreInfoUiState


@Composable
fun PartyListAndCreate(partiesUiState: PartiesUiState) {
    Box {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Background),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(partiesUiState.parties){ party ->
                val partyIndex = partiesUiState.parties.indexOf(party)
                val alpha : Double = (partyIndex+6)*(1.0/(partiesUiState.parties.size+5))
                PartyCard(partyInfo = party.coreInfo, backgroundColor = Color(30/256f, 0f, 93/256f, alpha.toFloat()))

            }
        }
        FloatingActionButton(onClick = { /*TODO*/ },
            modifier = Modifier.align(Alignment.BottomEnd).padding(end=10.dp, bottom = 10.dp).size(56.dp),
            shape = FloatingActionButtonDefaults.largeShape
            ) {
            Icon(Icons.Default.AddCircle, null, modifier = Modifier.size(56.dp), tint = Color.Green)
        }
    }

}

@Composable
fun PartyCard(partyInfo: PartyCoreInfoUiState, backgroundColor : Color = Color.White) {
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
                Button(onClick = {/*TODO*/}
                ) {
                    Text("Rediger i begivenhed")
                }
            }
        }
    }
}

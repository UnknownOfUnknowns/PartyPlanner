package com.example.partyplanner.ui.elements

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


//Not in use any longer
@Preview(showBackground = true)
@Composable
fun StartSide(onCreateParty : () -> Unit = {}, onPartyOverview: () -> Unit = {}){
    Box{
        FadeBackground() {
            Column(modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .wrapContentSize(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally,


                ) {
                DefaultButton(onClick = onCreateParty, buttonName = "Opret Fest")
                Spacer(modifier = Modifier.height(50.dp))
                DefaultButton(onClick = onPartyOverview , buttonName = "Mine begivenheder")
            }
        }

    }

}



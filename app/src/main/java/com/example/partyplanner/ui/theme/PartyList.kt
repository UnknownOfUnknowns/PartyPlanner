package com.example.partyplanner.ui.theme

import android.graphics.drawable.shapes.Shape
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.layout.*


import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.End
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun PartyListAndCreate() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        ElevatedCard(
            onClick = { },
            modifier = Modifier
                .size(width = 370.dp, height = 100.dp)
                .align(CenterHorizontally)
        ) {
            Column(Modifier.fillMaxSize()) {
                Text("Hanzos Bryllup med You know who", fontSize = 25.sp, fontWeight = FontWeight.Bold)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Hans Andersen")
                        Button(onClick = {/*TODO*/}
                        ) {
                            Text("Rediger i begivenhed")
                        }
                    }
            }
        }
    }

}



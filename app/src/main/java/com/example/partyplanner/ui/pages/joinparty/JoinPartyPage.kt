package com.example.partyplanner.ui.pages.joinparty

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.partyplanner.ui.elements.GenericOutlineTextField
import com.example.partyplanner.ui.theme.Background

@Composable
fun JoinPartyPage() {
    Column(modifier = Modifier
        .fillMaxSize()
        .background(Background),
        horizontalAlignment = CenterHorizontally) {
        Text(text = "Tilmeld", fontSize = 30.sp)
        Text(text = "Indtast ID fra din mail for at kunne se festen")


        GenericOutlineTextField(
            value = "",
            onValueChange = {},
            labelText = "Guest ID"
        )

        GenericOutlineTextField(
            value = "",
            onValueChange = {},
            labelText = "Party ID"
        )

        Row {
            OutlinedButton(onClick = { /*TODO*/ }) {
                Text(text = "Scan QR")
                Icon(imageVector = Icons.Default.QrCodeScanner, contentDescription = null)
            }

            Spacer(modifier = Modifier.width(10.dp))

            Button(onClick = { /*TODO*/ }) {
                Text(text = "Se festen")
            }
        }
    }

}



@Preview()
@Composable
fun JoinPartyPagePreview() {
    JoinPartyPage()
}
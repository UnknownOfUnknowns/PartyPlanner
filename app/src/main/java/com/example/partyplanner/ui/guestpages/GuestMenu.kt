package com.example.partyplanner.ui.guestpages

import android.graphics.Color
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Toc
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.partyplanner.ui.elements.FadeBackground
import com.example.partyplanner.ui.state.AttendanceState
import com.example.partyplanner.ui.theme.*


@Composable
fun GuestMenuPage(attendanceState: AttendanceState = AttendanceState.NOT_ATTENDING) {

    var invitationColorState = BudgetBoxColor

    if (attendanceState == AttendanceState.AWAITING){
        invitationColorState = BudgetBoxColor
    }
    if (attendanceState == AttendanceState.ATTENDS){
        invitationColorState = AttendingColor
    }

    if (attendanceState == AttendanceState.NOT_ATTENDING){
        invitationColorState = NotAttendingColor
    }


    FadeBackground() {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            Spacer (modifier = Modifier.height(20.dp))

            Text(text = "EVENTNAVN")

            Spacer (modifier = Modifier.height(20.dp))

            Column(
                modifier = Modifier
                    .background(invitationColorState)
                    .fillMaxWidth()
                    .padding(30.dp)
            ){
                Text(text = "EVENT")

                Spacer(modifier = Modifier.height(10.dp))

                Text(text = "BESKRIVELSE")

                Spacer (modifier = Modifier.height(10.dp))


                if (attendanceState == AttendanceState.AWAITING){
                    Text(text = "")
                    BottomBarWhenAwaiting()
                }
                if (attendanceState == AttendanceState.ATTENDS){
                    Text(text = "Du deltager til denne begivenhed", color = AlreadyAttendingTextColor)
                    Spacer (modifier = Modifier.height(10.dp))
                    BottomBarWhenAttends()
                }
                if (attendanceState == AttendanceState.NOT_ATTENDING){
                    Text(text = "Du deltager ikke til denne begivenhed", color = androidx.compose.ui.graphics.Color.Red)
                    Spacer (modifier = Modifier.height(10.dp))
                    BottomBarWhenNotAttending()
                }
            }

            Spacer (modifier = Modifier.height(15.dp))

            WishListButtonInvitationMenu()
        }
    }
}


@Composable
fun BottomBarWhenAwaiting(){
    Row(){


        Button(onClick = {
            AttendanceState.ATTENDS
        },
            colors = ButtonDefaults.buttonColors(AttendingColor)) {

            Text("Deltager", color = OnPrimaryContainer)
        }

        Spacer (modifier = Modifier.weight(1f))

        Button(onClick = {
            AttendanceState.NOT_ATTENDING
        },  colors = ButtonDefaults.buttonColors(
            NotAttendingColor)) {
            Text("Deltager ikke", color = OnPrimaryContainer)
        }
    }
}

@Composable
fun BottomBarWhenAttends(){
    Button(
        onClick = {},
        colors = ButtonDefaults.buttonColors(
            NotAttendingColor)) {
        Text("Meld afbud", color = OnPrimaryContainer)

    }
}

@Composable
fun BottomBarWhenNotAttending(){
    Button(
        onClick = {},
        colors = ButtonDefaults.buttonColors(
            AttendingColor)) {
        Text("Deltag", color = OnPrimaryContainer)

    }
}






@Composable
fun InvitationAttendingMenu(){
    Column(modifier = Modifier
        .background(AttendingColor)
        .fillMaxWidth()
        .padding(30.dp)) {

        Text(text = "EVENTBESKRIVELSE")

        Spacer (modifier = Modifier.height(10.dp))

        Text(text = "Du deltager til denne begivenhed", color = AlreadyAttendingTextColor)

        Spacer (modifier = Modifier.height(50.dp))

        Button(
            onClick = {},
            modifier = Modifier.align(Alignment.CenterHorizontally),
            colors = ButtonDefaults.buttonColors(
            NotAttendingColor)) {
            Text("Meld afbud", color = OnPrimaryContainer)

        }
    }
}

@Composable
fun InvitationNotAttendingMenu(){
    Column(modifier = Modifier
        .background(AttendingColor)
        .fillMaxWidth()
        .padding(30.dp)) {

        Text(text = "EVENTBESKRIVELSE")

        Spacer (modifier = Modifier.height(10.dp))

        Text(text = "Du deltager ikke til denne begivenhed", color = androidx.compose.ui.graphics.Color.Red)

        Spacer (modifier = Modifier.height(50.dp))

        Button(
            onClick = {},
            modifier = Modifier.align(Alignment.CenterHorizontally),
            colors = ButtonDefaults.buttonColors(
                NotAttendingColor)) {
            Text("Deltag", color = AttendingColor)

        }
    }
}


@Composable
fun WishListButtonInvitationMenu(){
    Button(onClick = {}) {

        Text("Ønskeliste")

        Spacer (modifier = Modifier.width(10.dp))

        Icon(imageVector = Icons.Outlined.Toc, contentDescription = "WishLogo",
        modifier = Modifier.size(ButtonDefaults.IconSize))

    }
}


@Preview
@Composable
fun Denherertiltest(){
    GuestMenuPage()
}
package com.example.partyplanner.ui.guestpages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Toc
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.partyplanner.data.party.GuestPartyInfo
import com.example.partyplanner.ui.elements.FadeBackground
import com.example.partyplanner.ui.state.AttendanceState
import com.example.partyplanner.ui.theme.*


@Composable
fun GuestMenuPage(viewModel: GuestMenuViewModel, navigateToWishList : () -> Unit) {

    val uiState by viewModel.uiState.collectAsState(initial = GuestPartyInfo())

    FadeBackground() {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer (modifier = Modifier.height(20.dp))

            Text(text = uiState.name, fontSize = 25.sp)

            Spacer (modifier = Modifier.height(20.dp))

            InviteArea(uiState.attendanceState, uiState.eventDescription,
                updateAttendanceState = {viewModel.updateAttendanceState(it)})

            Spacer (modifier = Modifier.height(15.dp))

            WishListButton(onWishListPress = navigateToWishList)
        }
    }
}

@Composable
fun InviteArea(attendanceState: AttendanceState,
               eventDescription: String,
               updateAttendanceState: (AttendanceState) -> Unit) {
    val invitationColorState = when (attendanceState) {
        AttendanceState.AWAITING -> BudgetBoxColor
        AttendanceState.ATTENDS -> AttendingColor
        else -> NotAttendingColor
    }
Row(
modifier = Modifier.fillMaxWidth()
) {

    Spacer (modifier = Modifier.width(10.dp))

    Column(
        modifier = Modifier
            .background(invitationColorState, shape = RoundedCornerShape(15.dp))
            .fillMaxWidth()
            .padding(20.dp),
    ) {

        Spacer(modifier = Modifier.height(10.dp))

        Text(text = eventDescription)

        Spacer(modifier = Modifier.height(10.dp))


        when (attendanceState) {
            AttendanceState.AWAITING -> {
                Text(text = "")
                BottomBarWhenAwaiting(updateAttendanceState)
            }
            AttendanceState.ATTENDS -> {
                Text(text = "Du deltager til denne begivenhed", color = AlreadyAttendingTextColor)
                Spacer(modifier = Modifier.height(10.dp))

                BottomBarWhenAttends(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    updateAttendanceState = updateAttendanceState
                )
            }
            AttendanceState.NOT_ATTENDING -> {
                Text(
                    text = "Du deltager ikke til denne begivenhed",
                    color = androidx.compose.ui.graphics.Color.Red
                )
                Spacer(modifier = Modifier.height(10.dp))
                BottomBarWhenNotAttending(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    updateAttendanceState = updateAttendanceState
                )
            }
        }
    }
    Spacer (modifier = Modifier.width(10.dp))
}

}

@Composable
fun BottomBarWhenAwaiting(updateAttendanceState: (AttendanceState) -> Unit){
    Row{
        ElevatedButton(
            onClick = { updateAttendanceState(AttendanceState.ATTENDS) },
            colors = ButtonDefaults.buttonColors(AttendingColor)) {

            Text("Deltager", color = OnPrimaryContainer)
        }

        Spacer (modifier = Modifier.weight(1f))

        ElevatedButton(onClick = {
            updateAttendanceState(AttendanceState.NOT_ATTENDING)
        },
            colors = ButtonDefaults.buttonColors(
            NotAttendingColor))
        {
            Text("Deltager ikke", color = OnPrimaryContainer)
        }
    }
}

@Composable
fun BottomBarWhenAttends(modifier : Modifier = Modifier, updateAttendanceState: (AttendanceState) -> Unit){
    ElevatedButton(
        onClick = {updateAttendanceState(AttendanceState.NOT_ATTENDING)},
        colors = ButtonDefaults.buttonColors(
            NotAttendingColor),
        modifier = modifier
    ) {
        Text("Meld afbud", color = OnPrimaryContainer)

    }
}

@Composable
fun BottomBarWhenNotAttending(modifier : Modifier = Modifier, updateAttendanceState: (AttendanceState) -> Unit){
    ElevatedButton(
        onClick = {updateAttendanceState(AttendanceState.ATTENDS)},

        colors = ButtonDefaults.buttonColors(AttendingColor),
        modifier = modifier
    ) {
        Text("Deltag", color = OnPrimaryContainer)

    }
}


@Composable
fun WishListButton(onWishListPress : () -> Unit){
    ElevatedButton(
        onClick = onWishListPress,
        colors  = ButtonDefaults.buttonColors(Primary)
    ) {

        Text("Ønskeliste", color = Color.White)

        Spacer (modifier = Modifier.width(10.dp))

        Icon(imageVector = Icons.Outlined.Toc, contentDescription = "WishLogo",
        modifier = Modifier.size(ButtonDefaults.IconSize))

    }
}


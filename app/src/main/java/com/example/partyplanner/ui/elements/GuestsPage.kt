package com.example.partyplanner.ui.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.partyplanner.ui.state.*
import com.example.partyplanner.ui.theme.*


@Composable
fun GuestsListEntry(guestListUiState: GuestListUiState) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        items(guestListUiState.guests) { item ->
            GuestCard(guestState = item,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .padding(top = 10.dp, bottom = 10.dp)
            )
        }
    }

}

@Composable
fun GuestCard(guestState : GuestUiState, modifier : Modifier = Modifier) {

    val backgroundColor = when(guestState.attendanceState) {
        AttendanceState.ATTENDS -> AttendingColor
        AttendanceState.AWAITING -> AwaitingColor
            AttendanceState.NOT_ATTENDING -> NotAttendingColor
    }

    Row(
        modifier = modifier.background(backgroundColor,shape = RoundedCornerShape(25.dp)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        //This row is nested such that Arrangement.SpaceBetween maximises space between name and trashcan
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(start = 10.dp)) {
            Icon(imageVector = Icons.Outlined.Person, contentDescription = null, modifier = Modifier.size(24.dp))
            Text(guestState.name,
                color = OnPrimaryContainer,
                fontSize = 20.sp,
                modifier = Modifier.padding(start = 5.dp))
        }
        Icon(imageVector = Icons.Outlined.Delete,
            contentDescription = null,
            modifier = Modifier
                .size(35.dp)
                .padding(end = 10.dp)
        )
    }
}



@Composable
fun GuestListPage(viewModel: GuestListViewModel) {
    val uiState = viewModel.uiState.collectAsState()
    Box(Modifier.background(Background)) {
        Column(Modifier
            .padding(start = 12.dp, end = 12.dp)
        ) {
            QuestOverview(modifier = Modifier.height(height = 92.dp).fillMaxWidth(),
                guestListUiState = uiState.value)
            GuestsListEntry(uiState.value)
        }
        DefaultFAB(modifier = Modifier.align(Alignment.BottomEnd), onClick = {})

    }
}



@Composable
fun QuestOverviewWithIcon(icon: ImageVector, questInCategory: Int, color: Color, categoryName: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = categoryName)
        Row() {
            Text(text = questInCategory.toString())
            Icon(tint = color, imageVector = icon, contentDescription = null)
        }
    }
}


@Composable
fun QuestOverview(modifier: Modifier = Modifier, guestListUiState: GuestListUiState) {
    Card(modifier = modifier,
        shape = RoundedCornerShape(8),
        colors = CardDefaults.cardColors(PrimaryContainer)
    ) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween) {
            QuestOverviewWithIcon(icon = Icons.Default.Done,
                questInCategory = guestListUiState.guests.filter { it.attendanceState == AttendanceState.ATTENDS }.size,
                color = Color.Green, categoryName = "Deltager")
            QuestOverviewWithIcon(icon = Icons.Default.Close,
                questInCategory = guestListUiState.guests.filter { it.attendanceState == AttendanceState.NOT_ATTENDING }.size
                , color = Color.Red, categoryName = "Afbud")
            QuestOverviewWithIcon(icon = Icons.Default.Refresh,
                questInCategory = guestListUiState.guests.filter { it.attendanceState == AttendanceState.AWAITING }.size
                , color = Color.Yellow, categoryName = "Afventer")
            QuestOverviewWithIcon(icon = Icons.Default.Email, questInCategory = guestListUiState.totalInvites, color = Color.White, categoryName = "Invitationer")

        }
    }
}


@Preview(showBackground = true)
@Composable
fun QuestOverviewPreview() {
    val viewModel = GuestListViewModel()
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        QuestOverview(modifier = Modifier.size(width = 342.dp, height = 92.dp),
            viewModel.uiState.collectAsState().value)
    }
}

@Preview
@Composable
fun GuestListPagePreview() {
    val viewModel = GuestListViewModel()

    GuestListPage(viewModel = viewModel)

}
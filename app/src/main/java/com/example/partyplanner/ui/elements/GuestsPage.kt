package com.example.partyplanner.ui.elements

import android.graphics.Paint.Align
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.*
import androidx.compose.material3.AlertDialogDefaults.shape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode.Companion.Color
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel
import com.example.partyplanner.ui.state.*
import com.example.partyplanner.ui.theme.*


@Composable
fun GuestsListEntry() {
    Box {
        LazyColumn(
            modifier = Modifier
                .background(PrimaryContainer)
                .fillMaxSize()
        ) {
        }
        GuestCard(guestState = GuestUiState("Ole Wedel", AttendanceState.ATTENDS),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 10.dp),
        )

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

        horizontalArrangement = Arrangement.SpaceBetween,

    ) {
        Icon(imageVector = Icons.Default.Person, contentDescription = null, modifier = Modifier.size(50.dp))
        Text(guestState.name, color = OnPrimaryContainer, fontSize = 40.sp)
        Icon(imageVector = Icons.Default.Delete, contentDescription = null, modifier = Modifier.size(50.dp))
    }
}



@Composable
fun GuestListPage(viewModel: GuestListViewModel) {
    val uiState = viewModel.uiState.collectAsState()


Box {
    GuestsListEntry()
    DefaultFAB(modifier = Modifier.align(Alignment.BottomEnd), onClick = {})

}




}

@Preview
@Composable
fun GuestListPagePreview() {
    val viewModel = GuestListViewModel()

    GuestListPage(viewModel = viewModel)

}

@Composable
fun QuestOverviewWithIcon(icon: ImageVector, questInCategory: Int, color: Color, categoryName: String) {
    Column(
        modifier = Modifier,
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
fun QuestOverview(modifier: Modifier = Modifier) {
    Card(modifier = modifier,
        shape = RoundedCornerShape(8),
        colors = CardDefaults.cardColors(PrimaryContainer)
    ) {
        Row(modifier = Modifier.
                fillMaxWidth().
                fillMaxHeight(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween) {
            QuestOverviewWithIcon(icon = Icons.Default.Done, questInCategory = 10, color = androidx.compose.ui.graphics.Color.Green, categoryName = "Deltager")
            QuestOverviewWithIcon(icon = Icons.Default.Close, questInCategory = 10, color = androidx.compose.ui.graphics.Color.Red, categoryName = "Afbud")
            QuestOverviewWithIcon(icon = Icons.Default.Refresh, questInCategory = 10, color = androidx.compose.ui.graphics.Color.Yellow, categoryName = "Afventer")
            QuestOverviewWithIcon(icon = Icons.Default.Email, questInCategory = 10, color = androidx.compose.ui.graphics.Color.White, categoryName = "Invitationer")

        }
    }
}



@Composable
fun QuestOverviewPreview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        QuestOverview(modifier = Modifier.size(width = 342.dp, height = 92.dp))
    }
}

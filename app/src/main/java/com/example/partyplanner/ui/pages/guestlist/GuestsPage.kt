package com.example.partyplanner.ui.pages.guestlist

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.partyplanner.R
import com.example.partyplanner.data.Guest
import com.example.partyplanner.data.GuestServiceImpl
import com.example.partyplanner.ui.elements.DefaultFAB
import com.example.partyplanner.ui.state.AttendanceState
import com.example.partyplanner.ui.theme.*
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun GuestListPage(viewModel: GuestListViewModel) {
    val uiState = viewModel.uiState.collectAsState()
    val deleteUserDialogOn = remember {
        mutableStateOf(false)
    }

    Box(Modifier.background(Background)) {
        Column(Modifier
            .padding(start = 12.dp, end = 12.dp)
        ) {
            GuestOverview(modifier = Modifier
                .height(height = 92.dp)
                .fillMaxWidth(),
                guestListUiState = uiState.value)
            GuestsListEntry(onDeleteGuest = {
                viewModel.setGuestToBeDeleted(it)
                deleteUserDialogOn.value = true
            },uiState.value)
        }
        if(uiState.value.invitationState.inviteOn){
            SendInviteDialog(
                onDismiss = { viewModel.changeInviteOn(false) },
                onSend = { viewModel.addGuest()},
                onAddressChange = {viewModel.changeSendingAddress(it)},
                onMethodChange = {viewModel.changeSendingMethod(it)},
                onGuestChange = {viewModel.changeGuestName(it)},
                sendInvitationUiState = uiState.value.invitationState
            )
        }
        DefaultFAB(modifier = Modifier.align(Alignment.BottomEnd), onClick = { viewModel.changeInviteOn(true) })

        if(deleteUserDialogOn.value) {
            DeleteGuestAlert(
                onConfirm = {
                    viewModel.deleteGuest()
                    deleteUserDialogOn.value = false
                },
                onDismiss = {deleteUserDialogOn.value = false})
        }
    }
}

@Composable
fun GuestsListEntry(onDeleteGuest : (Guest) -> Unit, guestListUiState: GuestListUiState) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        items(guestListUiState.guests) { item ->
            GuestCard(guestState = item,
                onDelete= {onDeleteGuest(it)},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .padding(top = 10.dp, bottom = 10.dp)
            )
        }
    }

}

@Composable
fun GuestCard(onDelete : (Guest) -> Unit, guestState : Guest, modifier : Modifier = Modifier) {

    val backgroundColor = when(guestState.attendanceState) {
        AttendanceState.ATTENDS -> AttendingColor
        AttendanceState.AWAITING -> AwaitingColor
        AttendanceState.NOT_ATTENDING -> NotAttendingColor
    }

    Row(
        modifier = modifier.background(backgroundColor,shape = RoundedCornerShape(25.dp)),
        verticalAlignment = CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        //This row is nested such that Arrangement.SpaceBetween maximises space between name and trashcan
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(start = 10.dp)) {
            Icon(imageVector = Icons.Outlined.Person, contentDescription = null, modifier = Modifier.size(24.dp))
            Text(text = guestState.name,
                color = OnPrimaryContainer,
                fontSize = 20.sp,
                modifier = Modifier.padding(start = 5.dp),
                fontFamily = Roboto)
        }
        Icon(imageVector = Icons.Outlined.Delete,
            contentDescription = stringResource(R.string.deleteIconDescription),
            modifier = Modifier
                .size(35.dp)
                .padding(end = 10.dp)
                .clickable {
                    onDelete(guestState)
                }
        )
    }
}







@Composable
fun QuestOverviewWithIcon(icon: ImageVector, questInCategory: Int, color: Color, categoryName: String) {
    Column(
        horizontalAlignment = CenterHorizontally
    ) {
        Text(text = categoryName)
        Row() {
            Text(text = questInCategory.toString())
            Icon(tint = color, imageVector = icon, contentDescription = null)
        }
    }
}


@Composable
fun GuestOverview(modifier: Modifier = Modifier, guestListUiState: GuestListUiState) {
    Card(modifier = modifier
        .padding(top = 10.dp, bottom = 10.dp),
        shape = RoundedCornerShape(8),
        colors = CardDefaults.cardColors(AttendingInfoColor),
    ) {
        Row(modifier = Modifier
            .fillMaxSize()
            .padding(start = 10.dp, end = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween) {
            QuestOverviewWithIcon(icon = Icons.Default.Done,
                questInCategory = guestListUiState.guests.filter { it.attendanceState == AttendanceState.ATTENDS }.size,
                color = Color.Green, categoryName = stringResource(id = R.string.participating))
            QuestOverviewWithIcon(icon = Icons.Default.Close,
                questInCategory = guestListUiState.guests.filter { it.attendanceState == AttendanceState.NOT_ATTENDING }.size
                , color = Color.Red, categoryName = stringResource(R.string.cancel))
            QuestOverviewWithIcon(icon = Icons.Default.Refresh,
                questInCategory = guestListUiState.guests.filter { it.attendanceState == AttendanceState.AWAITING }.size
                , color = Color.Yellow, categoryName = stringResource(R.string.awaits))
            QuestOverviewWithIcon(icon = Icons.Default.Email,
                questInCategory = guestListUiState.totalInvites,
                color = Color.White, categoryName = stringResource(R.string.invitations))

        }
    }
}

fun getStringFromSendingMethod(method: SendingMethod) : String {
    return when(method) {
        SendingMethod.SMS -> "SMS"
        SendingMethod.E_BOKS -> "E-Boks"
        SendingMethod.EMAIL -> "Email"
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SendInviteDialog(
    onDismiss: () -> Unit,
    onSend: () -> Unit,
    onAddressChange: (String) -> Unit,
    onMethodChange: (SendingMethod) -> Unit,
    onGuestChange: (String) -> Unit,
    sendInvitationUiState: SendInvitationUiState,
) {


    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(12.dp),

        ) {
            Column(
                horizontalAlignment = CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp)
            ) {
                Text(text = stringResource(R.string.inviteGuest), fontSize = 30.sp)
                TextField(value = sendInvitationUiState.guest,
                    onValueChange = onGuestChange,
                    label = { Text(text = stringResource(R.string.enterGuestName))},
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.White
                    ),
                    modifier = Modifier.padding(vertical = 10.dp),
                    singleLine = true
                )

                Divider(modifier = Modifier.fillMaxWidth())

                InviteRadioButtons(
                    onButtonChange = onMethodChange,
                    activeItem = sendInvitationUiState.sendingMethod,
                    modifier = Modifier.fillMaxWidth()
                )
                TextField(
                    value = sendInvitationUiState.address,
                    onValueChange = onAddressChange,
                    label = { Text(text = stringResource(R.string.Enter) + getStringFromSendingMethod(sendInvitationUiState.sendingMethod))},
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.White
                    ),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = when(sendInvitationUiState.sendingMethod) {
                            SendingMethod.SMS -> KeyboardType.Phone
                            else -> KeyboardType.Email
                        },
                        autoCorrect = false
                    ),

                )
                Spacer(modifier = Modifier.height(10.dp))
                Button(onClick = onSend, modifier = Modifier. fillMaxWidth()) {
                    Text(text = stringResource(R.string.sendInvitation))

                }
            }
        }
    }
}

@Composable
fun InviteRadioButtons(
    onButtonChange: (SendingMethod) -> Unit,
    activeItem: SendingMethod,
    modifier : Modifier = Modifier
){
    val sendingMethods = SendingMethod.values()
    Column(modifier = modifier) {
        Text(stringResource(R.string.chooseSendingMethod), color = Color.Gray)
        sendingMethods.forEach { item ->
            Row(verticalAlignment = CenterVertically) {
                RadioButton(selected = item == activeItem, onClick = { onButtonChange(item) })
                Text(text = getStringFromSendingMethod(item))
            }

        }
    }
}

@Composable
fun DeleteGuestAlert(
    onConfirm : () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(text = stringResource(R.string.DeleteGuest))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = stringResource(R.string.regret))
            }
        },
        title = {
            Text(text = stringResource(R.string.aboutToDelete), fontSize = 16.sp)
        },
        text = {
            Text(text = stringResource(R.string.wantToDelete))
        }
    )
}


@Preview
@Composable
fun SendInviteDialogPreview() {
    SendInviteDialog(onDismiss = { /*TODO*/ }, onSend = {}, onAddressChange = {}, onMethodChange = {},
        sendInvitationUiState = SendInvitationUiState("", SendingMethod.EMAIL, "123"), onGuestChange = {})
}

@Preview(showBackground = true)
@Composable
fun QuestOverviewPreview() {
    val viewModel = GuestListViewModel(GuestServiceImpl(firestore = FirebaseFirestore.getInstance(), "7v3WIdoU8FmJFnb3fvA7"))
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        GuestOverview(modifier = Modifier.size(width = 342.dp, height = 92.dp),
            viewModel.uiState.collectAsState().value)
    }
}

@Preview
@Composable
fun GuestListPagePreview() {
    val viewModel = GuestListViewModel(GuestServiceImpl(firestore = FirebaseFirestore.getInstance(),"7v3WIdoU8FmJFnb3fvA7"))

    GuestListPage(viewModel = viewModel)

}
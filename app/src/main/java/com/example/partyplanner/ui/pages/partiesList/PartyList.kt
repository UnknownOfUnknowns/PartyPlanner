package com.example.partyplanner.ui.elements


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.partyplanner.R
import com.example.partyplanner.data.party.Party
import com.example.partyplanner.ui.pages.partiesList.NewPartyViewModel
import com.example.partyplanner.ui.theme.Background
import com.example.partyplanner.ui.theme.OnPrimary
import com.example.partyplanner.ui.theme.OnPrimaryContainer


@Composable
fun PartyListAndCreate(viewModel: NewPartyViewModel, modifier : Modifier = Modifier,
                       onAddButton: () -> Unit, onEdit: (Party, Boolean) -> Unit) {
    val hostParties by viewModel.parties.collectAsState(initial = listOf())
    val guestParties by viewModel.guestParties.collectAsState(initial = listOf())
    var tabIndex by remember { mutableStateOf(0) }
    val parties = if(tabIndex == 0) hostParties else guestParties
    Box(modifier = modifier) {
        Column {
            StatusTab(tabIndex = tabIndex, { tabIndex = it })
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
                        showDelete = tabIndex == 0,
                        onClick = {onEdit(party, tabIndex == 0)},
                        backgroundColor = Color(30/256f, 0f, 93/256f, alpha.toFloat()),
                        onDelete = {viewModel.toggleDelete(it)}
                    )
                }

            }

        }
        if(viewModel.partyToBeDeleted.value != null) {
            DeleteDialog(
                text = "Er du sikker på at du vil slette festen: ${viewModel.partyToBeDeleted.value?.name}",
                onDismiss = {viewModel.toggleDelete(null)},
                onDelete = {viewModel.confirmDeletion()})
        }
        if(viewModel.openInviteDialogOn.value) {
            InviteDialog(
                        onDismiss = {viewModel.toggleInviteOn()},
                        onIdChange = {viewModel.updateGuestId(it)},
                        idValue = viewModel.guestId.value,
                        onAddButton = {viewModel.connectUserToParty()})
        }
        Button(onClick = viewModel::toggleInviteOn,
            Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 10.dp)) {
            Icon(imageVector = Icons.Default.PersonAdd, contentDescription = "Add user icon")
            Text(text = "Åbn Invitation")
        }
        DefaultFAB(modifier = Modifier.align(Alignment.BottomEnd), onClick = onAddButton)
    }

}


@Composable
fun InviteDialog(onDismiss : () -> Unit,
                 onIdChange : (String) -> Unit,
                 idValue : String,
                 onAddButton : () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Card(modifier = Modifier
            .padding(all = 10.dp),
            shape = RoundedCornerShape(5),
            colors = CardDefaults.cardColors(Background)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Tilmeld", fontSize = 20.sp, modifier = Modifier.padding (10.dp))
                Text(text = "Indtast ID fra din mail", modifier = Modifier.padding(10.dp))

                GenericOutlineTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                        .clip(RoundedCornerShape(10)),
                    shape = RoundedCornerShape(10),
                    value = idValue,
                    onValueChange = onIdChange,
                    labelText = "Party ID"
                )

                Button(onClick = onAddButton, modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 15.dp, start = 10.dp, end = 10.dp, bottom = 15.dp)
                    .clip(RoundedCornerShape(10)),
                shape = RoundedCornerShape(10)) {
                    Text(text = "Se festen")
                }
            }
        }
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
        Icon(
            Icons.Default.Add,
            contentDescription = stringResource(R.string.defaultAddButtonDescription),
            modifier = Modifier.fillMaxSize(),
            tint = Color.White
        )
    }

}

@Composable
fun PartyCard(partyInfo: Party, showDelete : Boolean, backgroundColor : Color = Color.White, onClick: () -> Unit, onDelete: (Party) -> Unit) {
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
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(partyInfo.name, fontSize = 25.sp, fontWeight = FontWeight.Bold, color = OnPrimary)
                if(showDelete){
                    Icon(
                        modifier = Modifier
                            .clickable { onDelete(partyInfo) }
                            .padding(top = 7.dp, end = 7.dp),
                        tint = Color.Red,
                        imageVector = Icons.Default.Close,
                        contentDescription = stringResource(id = R.string.deleteIconDescription)
                    )
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(partyInfo.address, color = OnPrimary)
                Button(onClick = onClick
                ) {
                    Text(stringResource(R.string.editEvent))
                }
            }
        }
    }
}

@Composable
fun StatusTab(tabIndex : Int, onTap : (Int) -> Unit) {

    val tabData = listOf(
        stringResource(R.string.host),
        stringResource(R.string.guest)
    )
    TabRow(selectedTabIndex = tabIndex,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        contentColor = OnPrimaryContainer,
        containerColor = Background,


    ) {
        tabData.forEachIndexed {index, data ->
            val selected = tabIndex == index

            Tab(selected = selected, onClick = {onTap(index)},
                modifier = Modifier,
                enabled = true,

                interactionSource = MutableInteractionSource(),
                selectedContentColor = OnPrimaryContainer,
                unselectedContentColor = OnPrimaryContainer
            ) {
                 Text(
                    text = data,
                     fontSize = 20.sp,
                     modifier = Modifier.padding(12.dp),
                    fontWeight = if (selected) {
                        FontWeight.Bold
                    }
                    else {
                        FontWeight.Normal
                    }
                )
            }
        }
    }
}


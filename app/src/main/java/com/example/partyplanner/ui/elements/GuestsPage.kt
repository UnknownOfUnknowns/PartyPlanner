package com.example.partyplanner.ui.elements

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.partyplanner.ui.state.GuestListViewModel
import com.example.partyplanner.ui.theme.PrimaryContainer

@Composable
fun GuestListPage(viewModel: GuestListViewModel) {
    val uiState = viewModel.uiState.collectAsState()

}

@Composable
fun QuestOverview(modifier: Modifier = Modifier) {
    Card(modifier = modifier,
        shape = RoundedCornerShape(8),
        colors = CardDefaults.cardColors(PrimaryContainer)
    ) {
        Row() {
            Icon(imageVector = Icons.Default.Add, contentDescription = null)
        }
    }
}

@Preview (showBackground = true)
@Composable
fun QuestOverviewPreview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        QuestOverview(modifier = Modifier.size(width = 190.dp, height = 40.dp),
        )
    }
}

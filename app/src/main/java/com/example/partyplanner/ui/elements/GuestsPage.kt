package com.example.partyplanner.ui.elements

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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
        Row(modifier = Modifier.
                fillMaxWidth().
                fillMaxHeight(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween) {
            QuestOverviewWithIcon(icon = Icons.Default.Done, questInCategory = 10, color = Color.Green, categoryName = "Deltager")
            QuestOverviewWithIcon(icon = Icons.Default.Close, questInCategory = 10, color = Color.Red, categoryName = "Afbud")
            QuestOverviewWithIcon(icon = Icons.Default.Refresh, questInCategory = 10, color = Color.Yellow, categoryName = "Afventer")
            QuestOverviewWithIcon(icon = Icons.Default.Email, questInCategory = 10, color = Color.White, categoryName = "Invitationer")

        }
    }
}
@Composable
fun QuestOverviewWithIcon(icon: ImageVector, questInCategory: Int, color: Color, categoryName: String) {
    Column(
        modifier = Modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(text = categoryName)
        Row() {
            Text(text = questInCategory.toString())
            Icon(tint = color, imageVector = icon, contentDescription = null )
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
        QuestOverview(modifier = Modifier.size(width = 342.dp, height = 92.dp))
    }
}

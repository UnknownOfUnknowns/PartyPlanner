package com.example.partyplanner.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

interface PartyPlannerDestination {
    val route: String
    val name : String
    val icon : ImageVector
}

object StartPage : PartyPlannerDestination {
    override val route = "start"
    override val name: String
        get() = TODO("Not yet implemented")
    override val icon: ImageVector
        get() = TODO("Not yet implemented")
}

object NewPartyPage : PartyPlannerDestination {
    override val route = "createParty"
    override val name: String
        get() = TODO("Not yet implemented")
    override val icon: ImageVector
        get() = TODO("Not yet implemented")

}

object AdditionalPartyDataPage : PartyPlannerDestination {
    override val route = "additionalPartyData"
    override val name: String
        get() = TODO("Not yet implemented")
    override val icon: ImageVector
        get() = TODO("Not yet implemented")
}

object PartiesOverviewPage : PartyPlannerDestination {
    override val route = "partiesOverviewPage"
    override val name: String = "Fester"
    override val icon: ImageVector = Icons.Filled.PartyMode
}

object ConfirmationPage : PartyPlannerDestination {
    override val route = "confirmationPage"
    override val name: String
        get() = TODO("Not yet implemented")
    override val icon: ImageVector
        get() = TODO("Not yet implemented")
}

object Guestlist : PartyPlannerDestination {
    override val route = "Guestlist"
    override val name: String = "Gæster"
    override val icon: ImageVector = Icons.Filled.Person
}

object LoginPage : PartyPlannerDestination {
    override val route = "login"
    override val name: String
        get() = TODO("Not yet implemented")
    override val icon: ImageVector
        get() = TODO("Not yet implemented")
}
object WishPage : PartyPlannerDestination {
    override val route = "wish"
    override val name: String = "Ønsker"
    override val icon: ImageVector = Icons.Default.GifBox
}

object BudgetPage : PartyPlannerDestination {
    override val route = "budget"
    override val name: String = "Budget"
    override val icon: ImageVector = Icons.Filled.Money

}

object GuestMenuPagee : PartyPlannerDestination {
    override val route = "GuestMenu"
    override val name: String
        get() = TODO("Not yet implemented")
    override val icon: ImageVector
        get() = TODO("Not yet implemented")
}

object TablePlannerPage : PartyPlannerDestination {
    override val route = "TablePlanner"
    override val name: String = "Bordplan"
    override val icon: ImageVector = Icons.Default.NextPlan
}

val partyPlannerScreens = listOf(StartPage, NewPartyPage)


val hostPartyScreens = listOf(Guestlist, WishPage, BudgetPage, TablePlannerPage)
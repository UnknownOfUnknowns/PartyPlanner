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
    override val name: String = ""
    override val icon: ImageVector = Icons.Default.Add
}
object NewPartyPage : PartyPlannerDestination {
    override val route = "createParty"
    override val name: String = "Ny fest"
    override val icon: ImageVector = Icons.Default.PartyMode

}

object AdditionalPartyDataPage : PartyPlannerDestination {
    override val route = "additionalPartyData"
    override val name: String ="Ny fest"
    override val icon: ImageVector = Icons.Default.PartyMode
}

object PartiesOverviewPage : PartyPlannerDestination {
    override val route = "partiesOverviewPage"
    override val name: String = "Fester"
    override val icon: ImageVector = Icons.Filled.PartyMode
}

object ConfirmationPage : PartyPlannerDestination {
    override val route = "confirmationPage"
    override val name: String = "Begivenhed oprettet"
    override val icon: ImageVector = Icons.Default.Done
}
object WishListGuestPage : PartyPlannerDestination {
    override val route: String = "wishListGuest"
    override val name: String
        get() = "Ønsker"
    override val icon: ImageVector
        get() = Icons.Filled.GifBox

}
object WishProductGuest : PartyPlannerDestination {
    override val route: String = "wishProductGuest"
    override val name: String
        get() = "Ønske"
    override val icon: ImageVector = Icons.Filled.GifBox

}
object WishProduct : PartyPlannerDestination {
    override val route: String = "wishProductPage"
    override val name: String = "Ønske"
    override val icon: ImageVector = Icons.Filled.CardGiftcard

}
object Guestlist : PartyPlannerDestination {
    override val route = "Guestlist"
    override val name: String = "Gæster"
    override val icon: ImageVector = Icons.Filled.Person
}

object LoginPage : PartyPlannerDestination {
    override val route = "login"
    override val name: String ="Login"
    override val icon: ImageVector = Icons.Default.Login
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

object GuestMenuInvitePage : PartyPlannerDestination {
    override val route = "GuestMenu"
    override val name: String
        get() = "Gæste menu"
    override val icon: ImageVector
        get() = Icons.Default.Person
}

object TablePlannerPage : PartyPlannerDestination {
    override val route = "TablePlanner"
    override val name: String = "Bordplan"
    override val icon: ImageVector = Icons.Default.NextPlan
}

object CreateLoginPage : PartyPlannerDestination {
    override val route = "CreateLogin"
    override val name: String = "CreateLogin"
    override val icon: ImageVector = Icons.Default.Login
}

val partyPlannerScreens = listOf(StartPage, NewPartyPage)


val hostPartyScreens = listOf(Guestlist, WishPage, BudgetPage, TablePlannerPage)
val guestPartyScreens = listOf(GuestMenuInvitePage, WishListGuestPage)
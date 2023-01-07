package com.example.partyplanner

interface PartyPlannerDestination {
    val route: String
}

object StartPage : PartyPlannerDestination{
    override val route = "start"
}

object NewPartyPage : PartyPlannerDestination{
    override val route = "createParty"
}

object AdditionalPartyDataPage : PartyPlannerDestination {
    override val route = "additionalPartyData"
}

object PartiesOverviewPage : PartyPlannerDestination {
    override val route = "partiesOverviewPage"
}

object ConfirmationPage : PartyPlannerDestination {
    override val route = "confirmationPage"
}

object Guestlist : PartyPlannerDestination {
    override val route = "Guestlist"
}

object LoginPage : PartyPlannerDestination {
    override val route = "login"
}
object WishPage : PartyPlannerDestination {
    override val route = "wish"
}

object BudgetPage : PartyPlannerDestination {
    override val route = "budget"
}

object GuestMenuPagee : PartyPlannerDestination{
    override val route = "GuestMenu"
}

object TablePlannerPage : PartyPlannerDestination{
    override val route = "TablePlanner"
}

val partyPlannerScreens = listOf(StartPage, NewPartyPage)
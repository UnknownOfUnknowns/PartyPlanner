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

val partyPlannerScreens = listOf(StartPage, NewPartyPage)
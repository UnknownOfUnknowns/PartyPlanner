package com.example.partyplanner.ui.theme

interface PartyPlannerDestination {
    val route: String
}

object StartPage : PartyPlannerDestination{
    override val route = "start"
}

object NewPartyPage : PartyPlannerDestination{
    override val route = "createParty"
}

val partyPlannerScreens = listOf(StartPage, NewPartyPage)
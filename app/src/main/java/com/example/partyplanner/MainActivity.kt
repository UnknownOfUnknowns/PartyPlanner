package com.example.partyplanner


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.partyplanner.data.GuestServiceImpl
import com.example.partyplanner.data.PartiesRepository
import com.example.partyplanner.data.account.AccountServiceImpl
import com.example.partyplanner.data.party.PartyServiceImpl
import com.example.partyplanner.data.wish.WishServiceImpl
import com.example.partyplanner.ui.elements.*
import com.example.partyplanner.ui.pages.guestlist.GuestListPage
import com.example.partyplanner.ui.pages.guestlist.GuestListViewModel
import com.example.partyplanner.ui.pages.login.LoginViewModel
import com.example.partyplanner.ui.pages.login.SignInScreen
import com.example.partyplanner.ui.pages.partiesList.NewPartyViewModel
import com.example.partyplanner.ui.pages.wishlist.WishListViewModel
import com.example.partyplanner.ui.state.PartyViewModel
import com.example.partyplanner.ui.theme.PartyPlannerTheme
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : ComponentActivity() {


    private val viewModel = PartyViewModel(PartiesRepository)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PartyPlannerApp(viewModel)
        }
    }


}

@Composable
fun PartyPlannerApp(viewModel: PartyViewModel){
    PartyPlannerTheme {
        fun NavHostController.navigateSingleTopTo(route: String) =
            this.navigate(route) {
                popUpTo(this@navigateSingleTopTo.graph.findStartDestination().id){
                    saveState = true
                }

                launchSingleTop = true

                restoreState = true
            }
        val navigationController = rememberNavController()
        val state = viewModel.uiState.collectAsState().value
        val loginService = AccountServiceImpl()
        NavHost(
            navController = navigationController,
            startDestination = LoginPage.route,
            modifier = Modifier.fillMaxSize()
        ) {
            composable(route = LoginPage.route) {
                val loginViewModel = LoginViewModel(loginService) {
                    navigationController.navigateSingleTopTo(
                        BudgetPage.route
                    )
                }
                SignInScreen(loginViewModel)
            }
            composable(route = WishPage.route) {
                val wishViewModel = WishListViewModel(WishServiceImpl(firestore = FirebaseFirestore.getInstance(),"7v3WIdoU8FmJFnb3fvA7"))

                WishListPage(wishViewModel)
            }
            composable(route = PartiesOverviewPage.route) {
                viewModel.fetchParties()
                PartyListAndCreate(
                    NewPartyViewModel(PartyServiceImpl(loginService)),
                    onAddButton = {
                        viewModel.newParty()
                        navigationController.navigateSingleTopTo(NewPartyPage.route)
                    },
                    onEdit = {navigationController.navigateSingleTopTo(Guestlist.route)}
                )
            }
            composable(route = BudgetPage.route) {
                TODO("INSERT BUDGET PAGE COMPOSABLE")
            }
            composable(route = NewPartyPage.route) {

                StartPartyCreation(onNextButtonClick =  {
                    navigationController.navigateSingleTopTo(AdditionalPartyDataPage.route)
                },
                    viewModel = NewPartyViewModel(PartyServiceImpl(loginService))
                )
            }

            composable(route = AdditionalPartyDataPage.route){

                SetPartyDataOnCreation(onNextButtonClick = {
                    navigationController.navigateSingleTopTo(ConfirmationPage.route)
                    },
                    party = state.currentParty.coreInfo,
                    setAddress = {viewModel.updateAddress(it)},
                    setCity = {viewModel.updateCity(it)},
                    setName = {viewModel.updateName(it)},
                    setZip = {viewModel.updateZip(it)}
                )
            }
            composable(route = ConfirmationPage.route){
                CreatePartyConfirmation()
            }

            composable(route = Guestlist.route){
                val db = Firebase.firestore
                GuestListPage(viewModel = GuestListViewModel(GuestServiceImpl(db, "7v3WIdoU8FmJFnb3fvA7")))
            }
        }
    }
}




package com.example.partyplanner


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.partyplanner.data.GuestRepository
import com.example.partyplanner.data.PartiesRepository
import com.example.partyplanner.data.account.AccountServiceImpl
import com.example.partyplanner.data.party.PartyServiceImpl
import com.example.partyplanner.ui.elements.*
import com.example.partyplanner.ui.pages.login.LoginViewModel
import com.example.partyplanner.ui.pages.login.SignInScreen
import com.example.partyplanner.ui.pages.partiesList.NewPartyViewModel
import com.example.partyplanner.ui.state.GuestListViewModel
import com.example.partyplanner.ui.state.PartyViewModel
import com.example.partyplanner.ui.theme.PartyPlannerTheme
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
                        PartiesOverviewPage.route
                    )
                }
                SignInScreen(loginViewModel)
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
            composable(route = NewPartyPage.route) {

                StartPartyCreation(onNextButtonClick =  {
                    navigationController.navigateSingleTopTo(AdditionalPartyDataPage.route)
                },
                party = state.currentParty
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
                GuestListPage(viewModel = GuestListViewModel(GuestRepository(db)))
            }
        }
    }
}


//Not in use any longer
@Preview(showBackground = true)
@Composable
fun StartSide(onCreateParty : () -> Unit = {}, onPartyOverview: () -> Unit = {}){
    Box{
        FadeBackground() {
            Column(modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .wrapContentSize(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally,


                ) {
                DefaultButton(onClick = onCreateParty, buttonName = "Opret Fest")
                Spacer(modifier = Modifier.height(50.dp))
                DefaultButton(onClick = onPartyOverview , buttonName = "Mine begivenheder")
            }
        }

    }

}



@Composable
fun DefaultButton(onClick: () -> Unit, buttonName: String) {

    Button(onClick = onClick,
        modifier = Modifier
            .width(322.dp)
            .height(97.dp),
        shape = RoundedCornerShape(100),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(
                red = 0.40392157435417175f,
                green = 0.3137255012989044f,
                blue = 0.6431372761726379f,
                alpha = 1f),
            contentColor = Color(0xFFFFF5EE),
        )

    ) {
        Text(text = buttonName, fontSize = 26.sp, fontStyle = FontStyle.Normal)
    }

}
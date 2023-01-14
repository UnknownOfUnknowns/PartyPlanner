package com.example.partyplanner


import android.graphics.Bitmap
import android.os.Bundle
import android.util.Size
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.partyplanner.data.account.AccountServiceImpl
import com.example.partyplanner.data.party.PartyServiceImpl
import com.example.partyplanner.data.wish.WishServiceImpl
import com.example.partyplanner.domain.ImagePicker
import com.example.partyplanner.navigation.*
import com.example.partyplanner.ui.elements.*
import com.example.partyplanner.ui.guestpages.GuestMenuPage
import com.example.partyplanner.ui.guestpages.GuestMenuViewModel
import com.example.partyplanner.ui.pages.login.CreateLoginScreen
import com.example.partyplanner.ui.pages.login.CreateUserViewModel
import com.example.partyplanner.ui.pages.login.LoginViewModel
import com.example.partyplanner.ui.pages.login.SignInScreen
import com.example.partyplanner.ui.pages.partiesList.NewPartyViewModel
import com.example.partyplanner.ui.pages.wishlist.WishListViewModel
import com.example.partyplanner.ui.pages.wishlist.WishViewModel
import com.example.partyplanner.ui.theme.PartyPlannerTheme
import com.google.firebase.firestore.FirebaseFirestore
import hostPartyGraph

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        ImagePicker.launcher = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
                if (uri != null) {
                        val bmp : Bitmap = applicationContext.contentResolver.loadThumbnail(
                            uri, Size(640,480), null
                        )
                        ImagePicker.callback(bmp)

                } else {
                    ImagePicker.callback(null)
                }

            }
        super.onCreate(savedInstanceState)
        setContent {
            PartyPlannerApp()
        }
    }


}

@Composable
fun PartyPlannerApp(){
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
        val loginService = AccountServiceImpl()
        val partyId = "partyId"
        val wishId = "wishId"
        NavHost(
            navController = navigationController,
            startDestination = LoginPage.route,
            modifier = Modifier.fillMaxSize()
        ) {
            composable(route = LoginPage.route) {
                val loginViewModel = LoginViewModel(loginService, onCreateNewUser = {navigationController.navigateSingleTopTo(CreateLoginPage.route)}) {
                    navigationController.navigateSingleTopTo(
                        PartiesOverviewPage.route
                    )
                }
                SignInScreen(loginViewModel)
            }

            composable(route = CreateLoginPage.route) {
                val createUserViewModel = CreateUserViewModel(
                    accountService = loginService,
                    onCreateNewUser = {
                    navigationController.navigateSingleTopTo(PartiesOverviewPage.route)
                })

                CreateLoginScreen(viewModel = createUserViewModel)
            }

            composable(route = "${WishListGuestPage.route}/{$partyId}") { backStack ->
                val party = backStack.arguments?.getString(partyId) ?: ""
                val wishViewModel = WishListViewModel(WishServiceImpl(firestore = FirebaseFirestore.getInstance(),party))

                WishListGuestPage(wishViewModel, navigateToProduct = {
                    println(it)
                    navigationController.navigate("${WishProductGuest.route}/${it.id}/$party")
                })
            }

            composable(route = "${WishProductGuest.route}/{$wishId}/{$partyId}") { backStack ->
                val party = backStack.arguments?.getString(partyId) ?: ""
                val wish = backStack.arguments?.getString(wishId) ?: ""

                val wishViewModel = WishViewModel(repository = WishServiceImpl(FirebaseFirestore.getInstance(), party), wish)
                WishProductGuestPage(viewModel = wishViewModel)
            }


            hostPartyGraph(navigationController)

            composable(route = PartiesOverviewPage.route) {
                PartyListAndCreate(
                    NewPartyViewModel(PartyServiceImpl()),
                    onAddButton = {
                        navigationController.navigateSingleTopTo(NewPartyPage.route)
                    },
                    onEdit = { party, hostTab ->
                         if(hostTab) {
                             navigationController.navigateSingleTopTo("${Guestlist.route}/${party.id}")
                         } else{
                             navigationController.navigateSingleTopTo("${GuestMenuPagee.route}/${party.id}")
                         }

                    }
                )
            }

            val partyViewModel = NewPartyViewModel(PartyServiceImpl())

            composable(route = NewPartyPage.route) {

                StartPartyCreation(onNextButtonClick =  {
                    navigationController.navigateSingleTopTo(AdditionalPartyDataPage.route)
                },
                    viewModel = partyViewModel
                )
            }

            composable(route = AdditionalPartyDataPage.route){

                val state by partyViewModel.uiState.collectAsState()

                SetPartyDataOnCreation(onNextButtonClick = {
                    partyViewModel.createParty()
                    navigationController.navigateSingleTopTo(PartiesOverviewPage.route)
                    },
                    party = state,
                    setAddress = {partyViewModel.updateAddress(it)},
                    setCity = {partyViewModel.updateCity(it)},
                    setName = {partyViewModel.updateName(it)},
                    setZip = {partyViewModel.updateZip(it)},
                    setDate = {partyViewModel.updateDate(it)}
                )
            }
            composable(route = ConfirmationPage.route){
                CreatePartyConfirmation()
            }



            composable(route = "${GuestMenuPagee.route}/{partyId}"){ backStack ->
                val party = backStack.arguments?.getString("partyId") ?: ""

                GuestMenuPage(GuestMenuViewModel(PartyServiceImpl(), party)) {
                    navigationController.navigate(
                        "${WishListGuestPage.route}/$party"
                    )
                }
            }


        }
    }
}


/*
    private fun test2(){
        val cursor = contentResolver.query(ContactsContract.Data.CONTENT_URI,
            arrayOf(ContactsContract.Data._ID, Phone.NUMBER, Phone.TYPE, Phone.LABEL),
            "1=1",
            arrayOf(), null)

        println(cursor?.getString(0))

    }
    private fun test(){
        var resultValue = 1
        if( applicationContext.checkSelfPermission( WRITE_CONTACTS) != PackageManager.PERMISSION_GRANTED )
            ActivityCompat.requestPermissions(this, arrayOf(WRITE_CONTACTS, READ_CONTACTS), resultValue);
        val values = ContentValues();
        values.put(ContactsContract.Data.RAW_CONTACT_ID, "1234")
        values.put(ContactsContract.Data.MIMETYPE, Phone.CONTENT_ITEM_TYPE)
        values.put(Phone.NUMBER, "93935411")
        values.put(Phone.TYPE, Phone.TYPE_CUSTOM)
        values.put(Phone.LABEL, "What a try")
        val dataUri = contentResolver.insert(ContactsContract.Data.CONTENT_URI, values)
        println(dataUri.toString() + "dfjadsjkjlkjæadsadjsladfskjæl")
    }
* */

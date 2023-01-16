
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.partyplanner.data.GuestServiceImpl
import com.example.partyplanner.data.budget.BudgetServiceImpl
import com.example.partyplanner.data.party.PartyServiceImpl
import com.example.partyplanner.data.wish.WishServiceImpl
import com.example.partyplanner.navigation.*
import com.example.partyplanner.ui.elements.BudgetPage
import com.example.partyplanner.ui.elements.WishListGuestPage
import com.example.partyplanner.ui.elements.WishListPage
import com.example.partyplanner.ui.elements.WishProductPage
import com.example.partyplanner.ui.elements.tableplannerpages.CreateTable
import com.example.partyplanner.ui.elements.tableplannerpages.TablePlannerViewModel
import com.example.partyplanner.ui.guestpages.GuestMenuPage
import com.example.partyplanner.ui.guestpages.GuestMenuViewModel
import com.example.partyplanner.ui.pages.budget.BudgetViewModel
import com.example.partyplanner.ui.pages.guestlist.GuestListPage
import com.example.partyplanner.ui.pages.guestlist.GuestListViewModel
import com.example.partyplanner.ui.pages.wishlist.WishListViewModel
import com.example.partyplanner.ui.pages.wishlist.WishViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
val partyId = "partyId"
val wishId = "wishId"
fun NavGraphBuilder.hostPartyGraph(navController : NavController) {

    navigation(startDestination = "${Guestlist.route}/{$partyId}", route = "host") {
        val navigatePage : (PartyPlannerDestination, String) -> Unit = { dest, id ->
            if(dest.route == PartiesOverviewPage.route) {
                navController.navigate(PartiesOverviewPage.route)
            } else {
                navController.navigate("${dest.route}/$id")
            }
        }

        composable(route = "${WishPage.route}/{$partyId}") { backStack ->

            val id = backStack.arguments?.getString(partyId) ?: ""
            val wishViewModel = WishListViewModel(WishServiceImpl(firestore = FirebaseFirestore.getInstance(),id))
            NavigationOverlay(
                currentDestination = hostPartyScreens.find { it.route == navController.currentDestination?.route?.substringBefore("/") } ?: WishPage,
                destinations = hostPartyScreens,
                navigate = { dest -> navigatePage(dest,id) }
            ) {
                WishListPage(wishViewModel, navigateToProduct = {navController.navigate("${WishProduct.route}/${it.id}/$id")})
            }
        }

        composable(route = "${WishProduct.route}/{$wishId}/{$partyId}") { backStack ->
            val party = backStack.arguments?.getString(partyId) ?: ""
            val wish = backStack.arguments?.getString(wishId) ?: ""

            val viewModel = WishViewModel(
                repository = WishServiceImpl(FirebaseFirestore.getInstance(), party),
                navigateOnWishDeleted = {
                    navigatePage(WishPage, party)
                },
                wishId = wish)
            WishProductPage(viewModel = viewModel, isGuest = false)

        }

        composable(route = "${BudgetPage.route}/{$partyId}") { backStack ->
            val id = backStack.arguments?.getString(partyId) ?: ""
            val budgetViewModel = BudgetViewModel(BudgetServiceImpl(firestore = FirebaseFirestore.getInstance(),id))
            NavigationOverlay(currentDestination = hostPartyScreens.find { it.route == navController.currentDestination?.route?.substringBefore("/") } ?: WishPage,
                destinations = hostPartyScreens,
                navigate = { dest -> navigatePage(dest,id) }) {
                BudgetPage(budgetViewModel)
            }
        }
        composable(route = "${Guestlist.route}/{$partyId}"){ backStack ->
            val id = backStack.arguments?.getString(partyId) ?: ""
            val db = Firebase.firestore
            NavigationOverlay(currentDestination = hostPartyScreens.find { it.route == navController.currentDestination?.route?.substringBefore("/") } ?: WishPage,
                destinations = hostPartyScreens,
                navigate = { dest -> navigatePage(dest,id) }) {
                GuestListPage(viewModel = GuestListViewModel(GuestServiceImpl(db, id)))
            }

        }
        //PartyId is gonna be used later hence it is required now
        composable(route = "${TablePlannerPage.route}/{$partyId}"){ backStack ->
            val id = backStack.arguments?.getString(partyId) ?: ""
            NavigationOverlay(currentDestination = hostPartyScreens.find { it.route == navController.currentDestination?.route?.substringBefore("/") } ?: WishPage,
                destinations = hostPartyScreens,
                navigate = { dest -> navigatePage(dest,id) }
            ) {
                CreateTable(TablePlannerViewModel())
            }
        }


    }
}

fun NavGraphBuilder.guestGraph(navController: NavController) {
    navigation(route= "guest", startDestination = "${Guestlist.route}/{$partyId}") {
        val navigatePage : (PartyPlannerDestination, String) -> Unit = { dest, id ->
            if(dest.route == PartiesOverviewPage.route) {
                navController.navigate(PartiesOverviewPage.route)
            } else {
                navController.navigate("${dest.route}/$id")
            }
        }
        composable(route = "${GuestMenuPagee.route}/{partyId}"){ backStack ->
            val party = backStack.arguments?.getString("partyId") ?: ""

            NavigationOverlay(currentDestination = guestPartyScreens.find { it.route == navController.currentDestination?.route?.substringBefore("/") } ?: GuestMenuPagee,
                destinations = guestPartyScreens,
                navigate = { dest -> navigatePage(dest,party) }) {
                GuestMenuPage(GuestMenuViewModel(PartyServiceImpl(), party)) {
                    navController.navigate(
                        "${WishListGuestPage.route}/$party"
                    )
                }
            }
        }

        composable(route = "${WishListGuestPage.route}/{$partyId}") { backStack ->
            val party = backStack.arguments?.getString(partyId) ?: ""
            val wishViewModel = WishListViewModel(WishServiceImpl(firestore = FirebaseFirestore.getInstance(),party))

            NavigationOverlay(currentDestination = guestPartyScreens.find { it.route == navController.currentDestination?.route?.substringBefore("/") } ?: GuestMenuPagee,
                destinations = guestPartyScreens,
                navigate = { dest -> navigatePage(dest,party) }) {

                WishListGuestPage(wishViewModel, navigateToProduct = {
                    navController.navigate("${WishProductGuest.route}/${it.id}/$party")
                })
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationOverlay(currentDestination : PartyPlannerDestination,
                      destinations : List<PartyPlannerDestination>,
                      navigate : (PartyPlannerDestination) -> Unit,
                      content : @Composable () -> Unit) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val allDestinations  = mutableListOf<PartyPlannerDestination>(PartiesOverviewPage)
    destinations.forEach {
        allDestinations.add(it)
    }
    Scaffold(topBar = {
        CenterAlignedTopAppBar(
            title = { Text(text = currentDestination.name)},
            navigationIcon = {
                IconButton(onClick = {
                    if(drawerState.isClosed) {
                        scope.launch { drawerState.open() }
                    } else {
                        scope.launch { drawerState.close() }
                    }
                }) {
                    Icon(Icons.Filled.Menu, contentDescription = null)
                }
            }
        )
    }) {
        Box(modifier = Modifier.padding(it)){
            ModalNavigationDrawer(
                drawerState = drawerState,
                drawerContent = {
                    ModalDrawerSheet {
                        Spacer(Modifier.height(12.dp))
                        allDestinations.forEach{ destination ->
                            NavigationDrawerItem(
                                label = { Text(destination.name) },
                                selected = destination == currentDestination,
                                onClick = {
                                    scope.launch {
                                        drawerState.close()
                                    }
                                    navigate(destination)
                                  },
                                icon = {Icon(destination.icon, contentDescription = null)},
                                modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                            )
                        }
                    }

                },
                content = content
            )

        }
    }
}
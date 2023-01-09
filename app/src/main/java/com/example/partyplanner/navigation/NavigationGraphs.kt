
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
import com.example.partyplanner.data.wish.WishServiceImpl
import com.example.partyplanner.navigation.*
import com.example.partyplanner.ui.elements.BudgetPage
import com.example.partyplanner.ui.elements.WishListPage
import com.example.partyplanner.ui.elements.tableplannerpages.CreateTable
import com.example.partyplanner.ui.elements.tableplannerpages.TablePlannerViewModel
import com.example.partyplanner.ui.pages.budget.BudgetViewModel
import com.example.partyplanner.ui.pages.guestlist.GuestListPage
import com.example.partyplanner.ui.pages.guestlist.GuestListViewModel
import com.example.partyplanner.ui.pages.wishlist.WishListViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

fun NavGraphBuilder.hostPartyGraph(navController : NavController) {
    navigation(startDestination = Guestlist.name, route = "host") {

        composable(route = WishPage.route) {
            val wishViewModel = WishListViewModel(WishServiceImpl(firestore = FirebaseFirestore.getInstance(),"7v3WIdoU8FmJFnb3fvA7"))
            NavigationOverlay(currentDestination = hostPartyScreens.find { it.route == navController.currentDestination?.route } ?: WishPage,
                destinations = hostPartyScreens,
                navigate = {navController.navigate(it.route)}) {
                WishListPage(wishViewModel)
            }

        }

        composable(route = BudgetPage.route) {
            val budgetViewModel = BudgetViewModel(BudgetServiceImpl(firestore = FirebaseFirestore.getInstance(),"7v3WIdoU8FmJFnb3fvA7"))
            NavigationOverlay(currentDestination = hostPartyScreens.find { it.route == navController.currentDestination?.route } ?: WishPage,
                destinations = hostPartyScreens,
                navigate = {navController.navigate(it.route)}) {
                BudgetPage(budgetViewModel)
            }
        }
        composable(route = Guestlist.route){
            val db = Firebase.firestore
            NavigationOverlay(currentDestination = hostPartyScreens.find { it.route == navController.currentDestination?.route } ?: WishPage,
                destinations = hostPartyScreens,
                navigate = {navController.navigate(it.route)}) {
                GuestListPage(viewModel = GuestListViewModel(GuestServiceImpl(db, "7v3WIdoU8FmJFnb3fvA7")))
            }

        }
        composable(route = TablePlannerPage.route){
            NavigationOverlay(currentDestination = hostPartyScreens.find { it.route == navController.currentDestination?.route } ?: WishPage,
                destinations = hostPartyScreens,
                navigate = {navController.navigate(it.route)}
            ) {
                CreateTable(TablePlannerViewModel())
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
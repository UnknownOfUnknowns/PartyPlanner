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
import com.example.partyplanner.data.PartiesRepository
import com.example.partyplanner.ui.elements.*
import com.example.partyplanner.ui.state.PartyViewModel
import com.example.partyplanner.ui.theme.PartyPlannerTheme

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
        NavHost(
            navController = navigationController,
            startDestination = PartiesOverviewPage.route,
            modifier = Modifier.fillMaxSize()
        ) {

            composable(route = PartiesOverviewPage.route) {
                viewModel.fetchParties()
                PartyListAndCreate(partiesUiState = viewModel.uiState.collectAsState().value, onAddButton = {
                    viewModel.newParty()
                    navigationController.navigateSingleTopTo(NewPartyPage.route)
                })
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





/*
Canvas(
modifier = Modifier
    .width(322.dp)
    .height(97.dp)
    .fillMaxWidth()
//.aspectRatio(1f)
)


val fillPath = PathParser.createPathFromPathData("M 0 0 L 322 0 L 322 97 L 0 97 L 0 0 Z ")
//fillPath.fillType = Path.FillType.EVEN_ODD
val rectF = RectF()
fillPath.computeBounds(rectF, true)
val matrix = Matrix()
val scale = minOf( size.width / rectF.width(), size.height / rectF.height() )
matrix.setScale(scale, scale)
fillPath.transform(matrix)
val composePathFill = fillPath.asComposePath()
shape = RoundedCornerShape(100)
drawPath(path = composePathFill, color = Color(red = 0.40392157435417175f, green = 0.3137255012989044f, blue = 0.6431372761726379f, alpha = 1f), style = androidx.compose.ui.graphics.drawscope.Fill)
drawPath(path = composePathFill, color = androidx.compose.ui.graphics.Color.Transparent, style = Stroke(width = 3f, miter = 4f, join = StrokeJoin.Round))
*/
/*
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PartyplannerlegTheme {
        backgroundDefault()
    }
}
 */
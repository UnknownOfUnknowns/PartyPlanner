package com.example.partyplanner


import android.graphics.Matrix
import android.graphics.RectF
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PaintingStyle.Companion.Fill
import androidx.compose.ui.graphics.PaintingStyle.Companion.Stroke
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.PathParser
import com.example.partyplanner.R
import com.example.partyplanner.ui.theme.PartyPlannerTheme
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {
            PartyPlannerTheme {
                StartSide()

            }
        }
    }
}

@Composable
fun backgroundDefault() {
    val image = painterResource(R.drawable.ic_launcher_background)

    Box {
        Image(
            painter = image,
            contentDescription = null,
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(),
            contentScale = ContentScale.Crop
        )

    }
}
@Preview(showBackground = true)
@Composable
fun StartSide(){

    backgroundDefault()
    Column(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()
        .wrapContentSize(Alignment.Center),
        horizontalAlignment = Alignment.CenterHorizontally,


        ) {
        Spacer(modifier = Modifier.height(200.dp))
        DefaultButton(onClick = { /*TODO*/ }, buttonName = "Opret Fest")
        Spacer(modifier = Modifier.height(50.dp))
        DefaultButton(onClick = { /*TODO*/ }, buttonName = "Mine begivenheder")
    }
}



@Composable
fun DefaultButton(onClick: () -> Unit, buttonName: String) {

    Button(onClick = onClick,
        modifier = Modifier
            .width(322.dp)
            .height(97.dp),
        shape = RoundedCornerShape(100),
        elevation = ButtonDefaults.elevation(
            defaultElevation = 6.dp,
            pressedElevation = 6.dp
        ),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color(
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
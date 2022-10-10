package com.example.partyplanner.ui.theme

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.partyplanner.R


@Composable
fun FadeBackground() {
    val firstFadeColor = Color(0xFF5E1DE8)
    val secondFadeColor = Color(0xFFACA0C6)
    Column(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier
            .fillMaxSize()
            .weight(0.3f)) {
            Canvas(modifier = Modifier
                .fillMaxSize()){
                drawRect(
                    brush = Brush.linearGradient(
                        0f to firstFadeColor,
                        0.45f to secondFadeColor,
                        start = Offset(0f, 0f),
                        end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
                    )
                )
                drawArc(
                    color = Background,
                    startAngle = 0f,
                    sweepAngle = -180f,
                    useCenter = true,
                    size = Size(size.width, size.height*0.50f),
                    topLeft = Offset(0f, size.height*.75f)
                )
            }
            ChampagneLogo(modifier = Modifier.align(Alignment.BottomCenter))
        }
        Canvas(modifier = Modifier
            .fillMaxSize()
            .weight(0.7f)){
            drawRect(
                color = Background
            )
        }
    }

}



@Preview
@Composable
fun ChampagneLogo(modifier: Modifier = Modifier){
    Surface(
        modifier = modifier.background(Background, shape = CircleShape).
            border(1.dp, color = Color.Black, shape = CircleShape),
        shape = CircleShape
    ) {
        Image(painter = painterResource(id = R.drawable.champange_glasses), contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.background(Background).padding(10.dp)
        )
    }
}

@Preview
@Composable
fun FadeBackgroundPreview() {
    PartyPlannerTheme {
        FadeBackground()
    }
}
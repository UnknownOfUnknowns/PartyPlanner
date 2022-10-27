package com.example.partyplanner.ui.elements

import android.graphics.Paint
import android.graphics.Path
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.partyplanner.R
import com.example.partyplanner.ui.theme.Background


@Composable
fun FadeBackground(content: @Composable() ( () -> Unit) = {}) {
    val firstFadeColor = Color(0xFF5E1DE8)
    val secondFadeColor = Color(0xFFACA0C6)
    Column(modifier = Modifier.fillMaxSize()) {
        Box(modifier =
        Modifier
            .fillMaxSize()
            .weight(0.3f)
        ){
            Canvas(modifier = Modifier
                .fillMaxSize()
            ) {
                drawRect(
                    brush = Brush.linearGradient(
                        0f to firstFadeColor,
                        0.45f to secondFadeColor,
                        start = Offset(0f, 0f),
                        end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
                    )
                )
                drawIntoCanvas {
                    val textPadding = 30.dp.toPx()
                    val arcHeight = 300.dp.toPx()
                    val arcWidth = 300.dp.toPx()
                    val path = Path().apply {
                        addArc(0f, textPadding, arcWidth, arcHeight, 180f, 180f)
                    }
                    it.nativeCanvas.drawTextOnPath(
                        "Hello Geek! This is Curved Text.",
                        path,
                        0f,
                        0f,
                        Paint().apply {
                            textSize = 20.sp.toPx()
                            textAlign = Paint.Align.CENTER
                        }
                    )
                }
                drawArc(
                    color = Background,
                    startAngle = 0f,
                    sweepAngle = -180f,
                    useCenter = true,
                    size = Size(size.width, size.height * 0.50f),
                    topLeft = Offset(0f, size.height * .56f)
                )
                drawRect(
                    color = Background,
                    size = Size(size.width, size.height*0.4f),
                    topLeft = Offset(0f, size.height*0.8f)
                )
            }
            ChampagneLogo(
                Modifier
                    .align(Alignment.BottomCenter)
                    .size(72.dp)
                    .offset(0.dp, (-3).dp))
        }

        Box(modifier = Modifier
            .fillMaxSize()
            .weight(0.7f)) {
            Canvas(modifier = Modifier.fillMaxSize()){
                drawRect(
                    color = Background
                )
            }
            content()
        }

    }
}



@Preview
@Composable
fun ChampagneLogo(modifier: Modifier = Modifier){
    Surface(
        modifier = modifier
            .background(Background, shape = CircleShape)
            .border(1.dp, color = Color.Black, shape = CircleShape)
            .shadow(5.dp, shape = CircleShape, clip = true),
        shape = CircleShape,
    ) {
        Image(painter = painterResource(id = R.drawable.champange_glasses), contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .background(Background)
                .padding(10.dp)
        )
    }
}


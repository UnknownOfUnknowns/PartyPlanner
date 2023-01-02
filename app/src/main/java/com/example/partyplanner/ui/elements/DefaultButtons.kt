package com.example.partyplanner.ui.elements

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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
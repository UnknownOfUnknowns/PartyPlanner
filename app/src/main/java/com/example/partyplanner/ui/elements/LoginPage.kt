package com.example.partyplanner.ui.elements

import androidx.compose.animation.EnterTransition.Companion.None
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.partyplanner.R

@Preview
@Composable
fun SigninScreen(onOpretBrugerClick: () -> Unit = {}, onLoginClick: () -> Unit = {}){
    FadeBackground(){
        Column(
            modifier = Modifier
                .fillMaxSize()
        ){

            Spacer(modifier = Modifier.weight(0.2f))

            Username("")

            Spacer(modifier = Modifier.weight(0.03f))

            Password("")

            Spacer(modifier = Modifier.weight(0.03f))

            Button(
                onClick = onLoginClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                shape = RoundedCornerShape(10),
            ) {

                Text("Log ind",
                    fontSize = 15.sp,
                    )
            }

            Spacer(modifier = Modifier.weight(0.4f))

            Button(
                onClick = onOpretBrugerClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                shape = RoundedCornerShape(10)
            ) {

                Text("Opret ny bruger",
                    fontSize = 15.sp
                    )

            }

            Spacer(modifier = Modifier.weight(0.1f))

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Username(username: String){

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clip(RoundedCornerShape(10)),
        value = username,
        onValueChange = {},
        shape = RoundedCornerShape(10),
        colors = TextFieldDefaults.textFieldColors(),
        label = { Text("Indast brugernavn",
            color = Color.Gray,
            fontSize = 15.sp,
            )
        }

    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Password(password: String){

    val showPassword = remember { mutableStateOf(false) }

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clip(RoundedCornerShape(10)),
        value = password,
        onValueChange = {},
        shape = RoundedCornerShape(10),
        colors = TextFieldDefaults.textFieldColors(Color.Red),

        visualTransformation = if (showPassword.value) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        },

        label = { Text("Indtast password",
            color = Color.Gray,
            fontSize = 15.sp,
        )
        },
        trailingIcon = {
            if (showPassword.value) {
                IconButton(onClick = {showPassword.value = false}) {
                    Icon(imageVector = Icons.Filled.Visibility,
                        contentDescription = stringResource(R.string.hide_password)
                    ) }
            } else {
                IconButton(onClick = {showPassword.value = true}) {
                    Icon(imageVector = Icons.Filled.VisibilityOff,
                        contentDescription = stringResource(R.string.show_password)
                    ) }
            }
        }

    )
}


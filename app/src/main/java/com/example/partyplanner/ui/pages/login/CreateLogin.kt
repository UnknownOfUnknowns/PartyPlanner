package com.example.partyplanner.ui.pages.login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.partyplanner.R
import com.example.partyplanner.ui.elements.FadeBackground

@Composable
fun CreateLoginPage(){

    FadeBackground() {

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text ( text  = "Opret ny bruger", fontSize = 30.sp)

            Spacer(modifier = Modifier.height(20.dp))

            FullName()

            Spacer(modifier = Modifier.height(20.dp))

            Email()

            Spacer(modifier = Modifier.height(20.dp))

            NewPassword()

            Spacer(modifier = Modifier.height(20.dp))

            RepeatPassword()

            Spacer(modifier = Modifier.height(20.dp))

            Button(onClick = { /*TODO*/ }) {
                Text (text = "Opret bruger")
            }

        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FullName() {

var fullName by remember { mutableStateOf(TextFieldValue("")) }

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clip(RoundedCornerShape(10)),
        value = fullName,
        onValueChange = { newfullName -> fullName = newfullName },
        shape = RoundedCornerShape(10),
        colors = TextFieldDefaults.textFieldColors(),
        label = { Text(text = "Indast fulde navn",
            color = Color.Gray,
            fontSize = 15.sp,
        )
        }

    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Email() {

    var email by remember { mutableStateOf(TextFieldValue("")) }

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clip(RoundedCornerShape(10)),
        value = email,
        onValueChange = { newemail -> email = newemail},
        shape = RoundedCornerShape(10),
        colors = TextFieldDefaults.textFieldColors(),
        label = { Text(text = "Indtast e-mail addresse",
            color = Color.Gray,
            fontSize = 15.sp,
        )
        }

    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewPassword(){

    val showPassword = remember { mutableStateOf(false) }
    var newPassword by remember { mutableStateOf(TextFieldValue("")) }

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clip(RoundedCornerShape(10)),
        value = newPassword,
        onValueChange = { newnewPassword -> newPassword = newnewPassword },
        shape = RoundedCornerShape(10),

        visualTransformation = if (showPassword.value) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        },

        label = { Text("Indtast kodeord",
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RepeatPassword(){

    val showPassword = remember { mutableStateOf(false) }
    var repeatPassword by remember { mutableStateOf(TextFieldValue("")) }

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clip(RoundedCornerShape(10)),
        value = repeatPassword,
        onValueChange = {newrepeatPassword -> repeatPassword = newrepeatPassword},
        shape = RoundedCornerShape(10),

        visualTransformation = if (showPassword.value) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        },

        label = { Text("Gentag kodeord",
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


@Preview
@Composable
fun PreviewCreateLoginPage(){
    CreateLoginPage()
}
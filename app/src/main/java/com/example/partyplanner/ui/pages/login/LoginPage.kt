package com.example.partyplanner.ui.pages.login

import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.partyplanner.R
import com.example.partyplanner.ui.elements.FadeBackground


@Composable
fun SignInScreen(viewModel: LoginViewModel){
    val state = viewModel.uiState.value
    FadeBackground(){
        Column(
            modifier = Modifier
                .fillMaxSize()
        ){

            Spacer(modifier = Modifier.weight(0.2f))

            Username(viewModel::onUsernameChange, state.email)

            Spacer(modifier = Modifier.weight(0.03f))

            Password(viewModel::onPasswordChange, state.password)

            Spacer(modifier = Modifier.weight(0.03f))

            Text(text = state.error, color = Color.Red, modifier = Modifier.padding(start = 10.dp))

            Button(
                onClick = viewModel::login,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                shape = RoundedCornerShape(10),
            ) {

                Text(
                                    stringResource(R.string.logInButtonText),
                    fontSize = 15.sp,
                    )
            }

            Spacer(modifier = Modifier.weight(0.4f))

            Button(
                onClick = viewModel::navigateToCreateUser,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                shape = RoundedCornerShape(10)
            ) {

                Text(
                                    stringResource(R.string.createNewUserButtonText),
                    fontSize = 15.sp
                    )

            }

            Spacer(modifier = Modifier.weight(0.1f))

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Username(onValueChange: (String) -> Unit, username: String){

    val context = LocalContext.current

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clip(RoundedCornerShape(10)),
        value = username,
        onValueChange = {
            if(it.length <= 40) {
                onValueChange(it)
            } else {
                Toast.makeText(context, "Maksimum 40 tegn", Toast.LENGTH_SHORT).show()
            }
        },

        shape = RoundedCornerShape(10),
        colors = TextFieldDefaults.outlinedTextFieldColors(Color.Black),
        label = { Text(
                    stringResource(R.string.usernameTextField),
            )
        }

    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Password(onValueChange: (String) -> Unit, password: String){

    val context = LocalContext.current

    val showPassword = remember { mutableStateOf(false) }

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clip(RoundedCornerShape(10)),
        value = password,
        onValueChange = {
            if(it.length <= 40) {
                onValueChange(it)
            } else {
                Toast.makeText(context, "Maksimum 40 tegn i kodeordet", Toast.LENGTH_SHORT).show()
            }
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(Color.Black),
        shape = RoundedCornerShape(10),

        visualTransformation = if (showPassword.value) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        },

        label = { Text(
                    stringResource(R.string.insertPasswordTextField),
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


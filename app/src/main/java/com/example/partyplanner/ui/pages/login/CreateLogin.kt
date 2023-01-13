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
import androidx.compose.ui.Alignment
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
fun CreateLoginScreen(viewModel: CreateUserViewModel){

    val state = viewModel.uiState.value

    FadeBackground() {

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text ( text  = stringResource(R.string.CreateNewUserTitleText), fontSize = 30.sp)

            Spacer(modifier = Modifier.height(20.dp))

            FullName(viewModel::onFullNameChange, state.fullname)

            Spacer(modifier = Modifier.height(20.dp))

            Email(viewModel::onUsernameChange, state.email)

            Spacer(modifier = Modifier.height(20.dp))

            NewPassword(viewModel::onPasswordChange, state.password)

            Spacer(modifier = Modifier.height(20.dp))

            RepeatPassword(viewModel::onRepeatedPassword, state.repeatedpassword)

            Spacer(modifier = Modifier.height(20.dp))

            Button(onClick = viewModel::createNewUser,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            shape = RoundedCornerShape(10)
            ) {
                Text (text = stringResource(R.string.CreateNewUserTextButton))
            }

        }
    }
    if(state.error) {
        TextFieldNotFilledDialog(onDismiss = viewModel::toggleError)
    }

    if(state.signUpError) {
        SignUpFailedDialog(onDismiss = viewModel::toggleSignUpError)
    }
}

@Composable
fun TextFieldNotFilledDialog(onDismiss : () -> Unit) {
    AlertDialog(onDismissRequest = onDismiss,
        title = { Text(text = stringResource(R.string.AlertNoEmptyFieldTitleText), fontSize = 20.sp)},
        text = { Text(text = stringResource(R.string.AlertNoEmptyFieldsAndMinPasswordText))},
        confirmButton = { Button(onClick = onDismiss) {
            Text(text = stringResource(R.string.ConfirmTextFromAlert))
        }})
}
@Composable
fun SignUpFailedDialog(onDismiss : () -> Unit) {
    AlertDialog(onDismissRequest = onDismiss,
        title = { Text(text = stringResource(R.string.AlertSignupFailTitleText), fontSize = 20.sp)},
        text = { Text(text = stringResource(R.string.AlertSignupFailTextMessage))},
        confirmButton = { Button(onClick = onDismiss) {
            Text(text = stringResource(R.string.AlertSignupFailConfirm))
        }})
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FullName(onValueChange: (String) -> Unit, fullName: String) {

    val context = LocalContext.current

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clip(RoundedCornerShape(10)),
        value = fullName,
        onValueChange = {
            if(it.length <= 40) {
                onValueChange(it)
            } else {
                Toast.makeText(context, "Maksimum 40 tegn", Toast.LENGTH_SHORT).show()
            }
        },
        shape = RoundedCornerShape(10),
        colors = TextFieldDefaults.textFieldColors(),
        label = { Text(text = stringResource(R.string.FullNameTextField),
            color = Color.Gray,
            fontSize = 15.sp,
        )
        }

    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Email(onValueChange: (String) -> Unit, email: String) {

    val context = LocalContext.current

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clip(RoundedCornerShape(10)),
        value = email,
        onValueChange = {
            if(it.length <= 40) {
                onValueChange(it)
            } else {
                Toast.makeText(context, "Maksimum 40 tegn", Toast.LENGTH_SHORT).show()
            }
        },
        shape = RoundedCornerShape(10),
        colors = TextFieldDefaults.textFieldColors(),
        label = { Text(text = stringResource(R.string.EmailTextField),
            color = Color.Gray,
            fontSize = 15.sp,
        )
        }

    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewPassword(onValueChange: (String) -> Unit, password: String){

    val showPassword = remember { mutableStateOf(false) }
    val context = LocalContext.current

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clip(RoundedCornerShape(10)),
        value = password,
        onValueChange = {
            if(it.length <= 30) {
                onValueChange(it)
            } else {
                Toast.makeText(context, "Maksimum 30 tegn som kodeord", Toast.LENGTH_SHORT).show()
            }
        },
        shape = RoundedCornerShape(10),

        visualTransformation = if (showPassword.value) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        },

        label = { Text(
                    stringResource(R.string.NewPasswordTextField),
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
fun RepeatPassword(onValueChange2: (String) -> Unit, repeatPassword: String){

    val showPassword2 = remember { mutableStateOf(false) }
    val context = LocalContext.current

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clip(RoundedCornerShape(10)),
        value = repeatPassword,
        onValueChange = {
            if(it.length <= 30) {
                onValueChange2(it)
            } else {
                Toast.makeText(context, "Maksimum 30 tegn som kodeord", Toast.LENGTH_SHORT).show()
            }
        },
        shape = RoundedCornerShape(10),

        visualTransformation = if (showPassword2.value) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        },

        label = { Text(
                    stringResource(R.string.RepeatPasswordTextField),
            color = Color.Gray,
            fontSize = 15.sp,
        )
        },
        trailingIcon = {
            if (showPassword2.value) {
                IconButton(onClick = {showPassword2.value = false}) {
                    Icon(imageVector = Icons.Filled.Visibility,
                        contentDescription = stringResource(R.string.hide_password)
                    ) }
            } else {
                IconButton(onClick = {showPassword2.value = true}) {
                    Icon(imageVector = Icons.Filled.VisibilityOff,
                        contentDescription = stringResource(R.string.show_password)
                    ) }
            }
        }

    )
}
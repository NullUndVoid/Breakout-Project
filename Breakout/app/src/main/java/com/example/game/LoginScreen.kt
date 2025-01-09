package com.example.game

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.game.ui.theme.GameTheme

@Composable
fun LoginView(modifier: Modifier = Modifier, onLogin : ()->Unit = {}) {

    val viewModel : LoginViewModel = viewModel()
    val loginstate = viewModel.state.value

    Box(modifier = modifier.fillMaxSize().background(color = Color.Black),
        contentAlignment = Alignment.Center)
    {
        Column (
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            TextField(colors = TextFieldDefaults.colors(focusedIndicatorColor = Color.White,
                                                        unfocusedIndicatorColor = Color.White,
                                                        disabledIndicatorColor = Color.White,
                                                        errorIndicatorColor = Color.White,

                                                        focusedTextColor = Color.White,
                                                        unfocusedTextColor = Color.White,
                                                        disabledTextColor = Color.White,
                                                        errorTextColor = Color.White,

                                                        focusedContainerColor = Color.Transparent,
                                                        unfocusedContainerColor = Color.Transparent,
                                                        disabledContainerColor = Color.Transparent,
                                                        errorContainerColor =  Color.Transparent), value = loginstate.email,
                onValueChange = {
                    viewModel.onEmailChange(it)
                },

                placeholder = {
                    Text("email", color = Color.White)
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(colors = TextFieldDefaults.colors(focusedIndicatorColor = Color.White,
                                                        unfocusedIndicatorColor = Color.White,
                                                        disabledIndicatorColor = Color.White,
                                                        errorIndicatorColor = Color.White,

                                                        focusedTextColor = Color.White,
                                                        unfocusedTextColor = Color.White,
                                                        disabledTextColor = Color.White,
                                                        errorTextColor = Color.White,

                                                        focusedContainerColor = Color.Transparent,
                                                        unfocusedContainerColor = Color.Transparent,
                                                        disabledContainerColor = Color.Transparent,
                                                        errorContainerColor =  Color.Transparent),
                value = loginstate.password,
                onValueChange = {
                    viewModel.onPasswordChange(it)
                },

                placeholder = {
                    Text("password", color = Color.White)
                },

                visualTransformation = PasswordVisualTransformation()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB00000)),
                onClick = {
                    viewModel.onLoginClick {
                        onLogin()
                    }

                },
                content = {
                    Text("Login")
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            if (loginstate.error != null)
                Text(loginstate.error, color = Color.White)

            if (loginstate.isLoading)
                CircularProgressIndicator()


        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginViewPreview() {
    GameTheme {
        LoginView()
    }
}
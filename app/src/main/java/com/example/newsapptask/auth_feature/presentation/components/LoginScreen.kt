package com.example.newsapptask.auth_feature.presentation.components

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.newsapptask.R
import com.example.newsapptask.auth_feature.presentation.viewmodels.LoginViewModel
import com.example.newsapptask.core.utils.Resource
import kotlinx.coroutines.flow.collectLatest

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavController, viewModel: LoginViewModel = hiltViewModel()) {
    val email by viewModel.email.collectAsState()
    val password by viewModel.password.collectAsState()
    val showPassword by remember { mutableStateOf(value = false) }
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.loginState.collectLatest { resource ->
            when (resource) {
                is Resource.Loading -> {
                    // CircularProgressIndicator()
                }

                is Resource.Success -> {
                    Toast.makeText(context, "Logged in successfully!", Toast.LENGTH_SHORT).show()
                    navController.navigate("home")
                }

                is Resource.Error -> {
                    Toast.makeText(context, resource.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 11.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Welcome to News App",
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 11.dp),
            style = TextStyle(
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.Black
            )
        )

        Text(
            text = "Sign in to continue",
            modifier = Modifier.padding(top = 4.dp)
        )

        TextField(
            value = email,
            onValueChange = { viewModel.email.value = it },
            modifier = Modifier
                .background(Color.White)
                .padding(top = 22.dp)
                .fillMaxWidth(),
            label = { Text("Your Email") },
            leadingIcon = {
                Icon(
                    painterResource(id = R.drawable.ic_email),
                    contentDescription = "Email",
                    modifier = Modifier.size(24.dp)
                )
            }
        )

        TextField(
            value = password,
            onValueChange = { viewModel.password.value = it },
            modifier = Modifier
                .background(Color.White)
                .padding(top = 12.dp)
                .fillMaxWidth(),

            visualTransformation = if (showPassword) {

                VisualTransformation.None

            } else {

                PasswordVisualTransformation()

            },
            label = { Text("Password") },
            leadingIcon = {
                Icon(
                    painterResource(id = R.drawable.ic_password),
                    contentDescription = "Password",
                    modifier = Modifier.size(24.dp)
                )
            }
        )

        Button(
            onClick = { viewModel.login() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 29.dp)
        ) {
            Text("Sign in", style = TextStyle(fontSize = 20.sp))
        }



        Row(
            modifier = Modifier
                .padding(top = 4.dp)
                .align(Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Don't have an account?", style = TextStyle(fontSize = 17.sp))
            TextButton(onClick = { navController.navigate("register") }) {
                Text(
                    text = "register",
                    style = TextStyle(
                        color = colorResource(id = R.color.purple_700),
                        fontSize = 17.sp
                    )
                )
            }
        }
    }
}

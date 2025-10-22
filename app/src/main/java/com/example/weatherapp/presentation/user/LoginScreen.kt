package com.example.weatherapp.presentation.user

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun LoginScreen(
        loginViewModel: LoginViewModel = viewModel(),
        onLoginSuccess: () -> Unit,
        onNavigateToRegister: () -> Unit
    ) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val loginState by loginViewModel.loginState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Login", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { loginViewModel.loginUser(email, password) },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0x66B0E0E6),
                contentColor = Color.DarkGray
            )
        ) {
            Text("Login")
        }

        Spacer(modifier = Modifier.height(8.dp))

        loginState?.let { success ->
            if (success) {
                LaunchedEffect(Unit) { onLoginSuccess() }
            } else {
                Text("Invalid email or password", color = MaterialTheme.colorScheme.error)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(onClick = { onNavigateToRegister() }) {
            Text("Don’t have an account? Register")
        }
    }
}
package com.example.socialconnect.ui.theme.auth


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.socialconnect.viewmodel.AuthViewModel

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onNavigateToSignUp: () -> Unit,
    vm: AuthViewModel = viewModel()
) {
    val state by vm.uiState.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("Login", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(16.dp))
        OutlinedTextField(value = state.email, onValueChange = vm::onEmailChange, label = { Text("Email") }, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(value = state.password, onValueChange = vm::onPasswordChange, label = { Text("Password") }, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(12.dp))
        if (state.error != null) {
            Text(text = state.error ?: "", color = MaterialTheme.colorScheme.error)
            Spacer(Modifier.height(8.dp))
        }
        if (state.loading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        } else {
            Button(onClick = { vm.login { ok, _ -> if (ok) onLoginSuccess() } }, modifier = Modifier.fillMaxWidth()) {
                Text("Login")
            }
        }
        Spacer(Modifier.height(8.dp))
        TextButton(onClick = onNavigateToSignUp, modifier = Modifier.align(Alignment.CenterHorizontally)) { Text("Create account") }
    }
}

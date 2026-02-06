package com.dev.sicenet.interfaces

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun LoginScreen(viewModel: LoginViewModel = viewModel(), modifier: Modifier = Modifier) {
    val state = viewModel.loginState

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("SICENET",
            style = MaterialTheme.typography.headlineLarge)

        Text(
            text = "Inicio de sesión",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(40.dp))

        OutlinedTextField(
            value = state.matricula,
            onValueChange = { viewModel.onMatriculaChanged(it) },
            label = { Text("Matrícula") },
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = state.contrasena,
            onValueChange = { viewModel.onContrasenaChanged(it) },
            label = { Text("Contraseña") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            modifier = Modifier
                .height(48.dp),
            enabled = !state.isLoading,
            onClick = { viewModel.login() }) {
            Text("Ingresar")
        }

        if (state.isLoading) {
            CircularProgressIndicator(
                strokeWidth = 3.dp
            )
        }

        state.errorMessage?.let {
            Spacer(modifier = Modifier.height(16.dp))
            Text("Error: $it", color = Color.Red, style = MaterialTheme.typography.bodySmall)
        }

        if (state.isSuccess) {
            Spacer(modifier = Modifier.height(24.dp))
            Column (
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Bienvenido, token: ${state.token}",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodyLarge)
                Text("Matrícula: ${state.matricula}",
                    style = MaterialTheme.typography.bodySmall)
                Text("Estado: Autenticación exitosa")
                Text("Fecha de acceso: ${java.time.LocalDateTime.now()}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }

    }
}


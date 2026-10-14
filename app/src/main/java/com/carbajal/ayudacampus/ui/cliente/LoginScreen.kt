package com.carbajal.ayudacampus.ui.cliente

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.carbajal.ayudacampus.model.Rol
import com.carbajal.ayudacampus.ui.components.AyudaButton
import com.carbajal.ayudacampus.ui.components.AyudaTextField
import com.carbajal.ayudacampus.viewmodel.SesionViewModel

@Composable
fun LoginScreen(
    viewModel: SesionViewModel,
    onLoginExitoso: (Rol) -> Unit
) {
    var correo by remember { mutableStateOf("estudiante@campus.edu.pe") }
    var clave by remember { mutableStateOf("123456") }
    var mensajeError by remember { mutableStateOf<String?>(null) }

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Mesa de Ayuda TI",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "Inicia sesión con tu cuenta de campus",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(32.dp))

            AyudaTextField(
                value = correo,
                onValueChange = { correo = it },
                label = "Correo Institucional"
            )
            Spacer(modifier = Modifier.height(16.dp))

            AyudaTextField(
                value = clave,
                onValueChange = { clave = it },
                label = "Contraseña",
                visualTransformation = PasswordVisualTransformation()
            )
            Spacer(modifier = Modifier.height(24.dp))

            if (mensajeError != null) {
                Text(
                    text = mensajeError!!,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            AyudaButton(
                texto = "Ingresar",
                onClick = {
                    viewModel.login(correo, clave) { exito, error ->
                        if (exito) {
                            val usuario = viewModel.usuarioActual.value
                            if (usuario != null) {
                                onLoginExitoso(usuario.rol)
                            }
                        } else {
                            mensajeError = error
                        }
                    }
                }
            )
        }
    }
}

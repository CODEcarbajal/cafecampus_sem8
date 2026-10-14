package com.carbajal.ayudacampus.ui.cliente

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.carbajal.ayudacampus.ui.components.AppTopBar
import com.carbajal.ayudacampus.ui.components.AyudaButton
import com.carbajal.ayudacampus.viewmodel.SesionViewModel

@Composable
fun PerfilScreen(
    viewModel: SesionViewModel,
    onCerrarSesion: () -> Unit
) {
    val usuario by viewModel.usuarioActual.collectAsState()

    Scaffold(
        topBar = { AppTopBar(titulo = "Mi Perfil") }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp)
        ) {
            Text(
                text = usuario?.nombre ?: "Estudiante",
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = usuario?.correo ?: "",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.secondary
            )
            Spacer(modifier = Modifier.height(32.dp))

            AyudaButton(
                texto = "Cerrar Sesión",
                onClick = {
                    viewModel.cerrarSesion()
                    onCerrarSesion()
                }
            )
        }
    }
}

package com.carbajal.ayudacampus.ui.soporte

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.carbajal.ayudacampus.ui.components.AppTopBar
import com.carbajal.ayudacampus.ui.components.AyudaButton
import com.carbajal.ayudacampus.viewmodel.SesionViewModel

@Composable
fun AjustesScreen(
    viewModel: SesionViewModel,
    onCerrarSesion: () -> Unit
) {
    val temaOscuro by viewModel.temaOscuro.collectAsState()
    val usuario by viewModel.usuarioActual.collectAsState()

    Scaffold(
        topBar = { AppTopBar(titulo = "Ajustes y Perfil TI") }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp)
        ) {
            Text(
                text = usuario?.nombre ?: "Personal de Soporte TI",
                style = MaterialTheme.typography.headlineSmall
            )
            Text(
                text = usuario?.correo ?: "",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.secondary
            )

            Spacer(modifier = Modifier.height(32.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Modo Oscuro",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.weight(1f)
                )
                Switch(
                    checked = temaOscuro,
                    onCheckedChange = { viewModel.toggleTemaOscuro(it) }
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

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

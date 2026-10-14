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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.carbajal.ayudacampus.model.Prioridad
import com.carbajal.ayudacampus.ui.components.AppTopBar
import com.carbajal.ayudacampus.ui.components.AyudaButton
import com.carbajal.ayudacampus.ui.components.AyudaTextField
import com.carbajal.ayudacampus.viewmodel.NuevoTicketViewModel

@Composable
fun NuevoTicketScreen(
    viewModel: NuevoTicketViewModel,
    onBack: () -> Unit,
    onTicketCreado: () -> Unit
) {
    val categorias by viewModel.categoriasActivas.collectAsState()

    var titulo by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var ubicacion by remember { mutableStateOf("") }
    var categoriaIdSeleccionada by remember { mutableStateOf<Int?>(null) }
    var prioridadSeleccionada by remember { mutableStateOf(Prioridad.MEDIA) }

    Scaffold(
        topBar = { AppTopBar(titulo = "Reportar Incidencia", onBack = onBack) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            AyudaTextField(
                value = titulo,
                onValueChange = { titulo = it },
                label = "Título del problema"
            )
            Spacer(modifier = Modifier.height(12.dp))

            AyudaTextField(
                value = descripcion,
                onValueChange = { descripcion = it },
                label = "Descripción detallada",
                singleLine = false
            )
            Spacer(modifier = Modifier.height(12.dp))

            AyudaTextField(
                value = ubicacion,
                onValueChange = { ubicacion = it },
                label = "Ubicación (Ej: Pabellón A - Aula 102)"
            )
            Spacer(modifier = Modifier.height(24.dp))

            AyudaButton(
                texto = "Enviar Ticket",
                enabled = titulo.isNotBlank() && descripcion.isNotBlank() && (categoriaIdSeleccionada ?: (categorias.firstOrNull()?.id)) != null,
                onClick = {
                    val catId = categoriaIdSeleccionada ?: categorias.firstOrNull()?.id ?: return@AyudaButton
                    viewModel.crearTicket(
                        categoriaId = catId,
                        titulo = titulo,
                        descripcion = descripcion,
                        ubicacion = ubicacion,
                        prioridad = prioridadSeleccionada,
                        onSuccess = onTicketCreado
                    )
                }
            )
        }
    }
}

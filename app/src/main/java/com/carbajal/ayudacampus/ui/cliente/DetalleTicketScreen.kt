package com.carbajal.ayudacampus.ui.cliente

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.carbajal.ayudacampus.ui.components.AppTopBar
import com.carbajal.ayudacampus.ui.components.BurbujaComentario
import com.carbajal.ayudacampus.ui.components.EstadoChip
import com.carbajal.ayudacampus.ui.components.Estrellas
import com.carbajal.ayudacampus.ui.components.StepperTicket
import com.carbajal.ayudacampus.viewmodel.TicketDetalleViewModel

@Composable
fun DetalleTicketScreen(
    ticketId: Int,
    viewModel: TicketDetalleViewModel,
    onBack: () -> Unit
) {
    LaunchedEffect(ticketId) {
        viewModel.setTicketId(ticketId)
    }

    val ticketDetalle by viewModel.ticketDetalle.collectAsState()
    var nuevoComentario by remember { mutableStateOf("") }

    Scaffold(
        topBar = { AppTopBar(titulo = "Detalle del Ticket #${ticketId}", onBack = onBack) }
    ) { padding ->
        val detalle = ticketDetalle
        if (detalle == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text("Cargando ticket...")
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp)
            ) {
                Text(text = detalle.ticket.titulo, style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(4.dp))
                EstadoChip(estado = detalle.ticket.estado)
                Spacer(modifier = Modifier.height(8.dp))
                StepperTicket(estadoActual = detalle.ticket.estado)

                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Ubicación: ${detalle.ticket.ubicacion}", style = MaterialTheme.typography.bodyMedium)
                Text(text = detalle.ticket.descripcion, style = MaterialTheme.typography.bodySmall)

                Spacer(modifier = Modifier.height(16.dp))
                Divider()
                Spacer(modifier = Modifier.height(8.dp))

                Text(text = "Calificación del servicio:", style = MaterialTheme.typography.labelMedium)
                Estrellas(
                    calificacion = detalle.ticket.calificacion ?: 0,
                    onCalificar = { estrellas -> viewModel.calificarAtencion(estrellas) }
                )

                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Conversación:", style = MaterialTheme.typography.titleMedium)

                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(detalle.comentarios) { comentario ->
                        BurbujaComentario(
                            comentario = comentario,
                            esUsuarioActual = comentario.autorId == detalle.ticket.clienteId
                        )
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = nuevoComentario,
                        onValueChange = { nuevoComentario = it },
                        modifier = Modifier.weight(1f),
                        placeholder = { Text("Escribe un mensaje...") }
                    )
                    TextButton(onClick = {
                        viewModel.agregarComentario(nuevoComentario)
                        nuevoComentario = ""
                    }) {
                        Text("Enviar")
                    }
                }
            }
        }
    }
}

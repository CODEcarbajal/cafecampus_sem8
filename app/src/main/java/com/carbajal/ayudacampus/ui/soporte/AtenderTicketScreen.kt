package com.carbajal.ayudacampus.ui.soporte

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
import androidx.compose.material3.Button
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
import com.carbajal.ayudacampus.model.EstadoTicket
import com.carbajal.ayudacampus.ui.components.AppTopBar
import com.carbajal.ayudacampus.ui.components.BurbujaComentario
import com.carbajal.ayudacampus.ui.components.EstadoChip
import com.carbajal.ayudacampus.ui.components.PrioridadChip
import com.carbajal.ayudacampus.viewmodel.AtenderViewModel

@Composable
fun AtenderTicketScreen(
    ticketId: Int,
    viewModel: AtenderViewModel,
    onBack: () -> Unit
) {
    LaunchedEffect(ticketId) {
        viewModel.setTicketId(ticketId)
    }

    val ticketDetalle by viewModel.ticketDetalle.collectAsState()
    var respuestaTexto by remember { mutableStateOf("") }

    Scaffold(
        topBar = { AppTopBar(titulo = "Atender Ticket #${ticketId}", onBack = onBack) }
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

                Row(verticalAlignment = Alignment.CenterVertically) {
                    EstadoChip(estado = detalle.ticket.estado)
                    Spacer(modifier = Modifier.height(8.dp))
                    PrioridadChip(prioridad = detalle.ticket.prioridad)
                }

                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Cliente: ${detalle.cliente.nombre} (${detalle.cliente.correo})")
                Text(text = "Ubicación: ${detalle.ticket.ubicacion}")
                Text(text = "Descripción: ${detalle.ticket.descripcion}")

                Spacer(modifier = Modifier.height(12.dp))
                Row {
                    Button(onClick = { viewModel.cambiarEstado(EstadoTicket.EN_PROCESO) }) {
                        Text("En Proceso")
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = { viewModel.cambiarEstado(EstadoTicket.RESUELTO) }) {
                        Text("Resolver")
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))
                Divider()
                Spacer(modifier = Modifier.height(8.dp))

                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(detalle.comentarios) { comentario ->
                        BurbujaComentario(
                            comentario = comentario,
                            esUsuarioActual = comentario.autorId == detalle.cliente.id
                        )
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = respuestaTexto,
                        onValueChange = { respuestaTexto = it },
                        modifier = Modifier.weight(1f),
                        placeholder = { Text("Responder al estudiante...") }
                    )
                    TextButton(onClick = {
                        viewModel.responder(respuestaTexto)
                        respuestaTexto = ""
                    }) {
                        Text("Responder")
                    }
                }
            }
        }
    }
}

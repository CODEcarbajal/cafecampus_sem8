package com.carbajal.ayudacampus.ui.cliente

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.carbajal.ayudacampus.ui.components.AppTopBar
import com.carbajal.ayudacampus.ui.components.TicketCard
import com.carbajal.ayudacampus.viewmodel.MisTicketsViewModel

@Composable
fun MisTicketsScreen(
    viewModel: MisTicketsViewModel,
    onNuevoTicket: () -> Unit,
    onVerDetalle: (Int) -> Unit
) {
    val misTickets by viewModel.misTickets.collectAsState()

    Scaffold(
        topBar = { AppTopBar(titulo = "Mis Incidencias") },
        floatingActionButton = {
            FloatingActionButton(onClick = onNuevoTicket) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Nueva Incidencia")
            }
        }
    ) { padding ->
        if (misTickets.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No has reportado ninguna incidencia aún",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp)
            ) {
                items(misTickets) { ticketDetalle ->
                    TicketCard(
                        ticketDetalle = ticketDetalle,
                        onClick = { onVerDetalle(ticketDetalle.ticket.id) }
                    )
                }
            }
        }
    }
}

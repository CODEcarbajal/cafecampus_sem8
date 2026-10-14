package com.carbajal.ayudacampus.ui.soporte

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.carbajal.ayudacampus.ui.components.AppTopBar
import com.carbajal.ayudacampus.ui.components.TicketCard
import com.carbajal.ayudacampus.viewmodel.BandejaViewModel

@Composable
fun BandejaScreen(
    viewModel: BandejaViewModel,
    onAtenderTicket: (Int) -> Unit
) {
    val tickets by viewModel.ticketsFiltrados.collectAsState()
    val query by viewModel.busquedaQuery.collectAsState()

    Scaffold(
        topBar = { AppTopBar(titulo = "Bandeja de Tickets TI") }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = query,
                onValueChange = { viewModel.busquedaQuery.value = it },
                label = { Text("Buscar por título, usuario o categoría") },
                modifier = Modifier.fillMaxSize()
            )
            Spacer(modifier = Modifier.height(12.dp))

            LazyColumn(modifier = Modifier.weight(1f)) {
                items(tickets) { item ->
                    TicketCard(
                        ticketDetalle = item,
                        onClick = { onAtenderTicket(item.ticket.id) }
                    )
                }
            }
        }
    }
}

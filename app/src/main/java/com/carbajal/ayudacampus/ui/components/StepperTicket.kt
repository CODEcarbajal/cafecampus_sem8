package com.carbajal.ayudacampus.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.carbajal.ayudacampus.model.EstadoTicket

@Composable
fun StepperTicket(estadoActual: EstadoTicket) {
    val estados = EstadoTicket.entries

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        estados.forEach { estado ->
            val seleccionado = estado.ordinal <= estadoActual.ordinal
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = estado.name.take(3),
                    style = MaterialTheme.typography.labelSmall,
                    color = if (seleccionado) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline
                )
            }
        }
    }
}

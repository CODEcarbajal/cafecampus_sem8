package com.carbajal.ayudacampus.ui.components.graficos

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.carbajal.ayudacampus.model.EstadoTicket

@Composable
fun GraficoBarras(datosEstado: Map<EstadoTicket, Int>) {
    val maximo = (datosEstado.values.maxOrNull() ?: 1).coerceAtLeast(1)

    Column(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
        EstadoTicket.entries.forEach { estado ->
            val cantidad = datosEstado[estado] ?: 0
            val progreso = cantidad.toFloat() / maximo

            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = estado.name,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.width(90.dp)
                )
                LinearProgressIndicator(
                    progress = { progreso },
                    modifier = Modifier.weight(1f).height(12.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "$cantidad",
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }
    }
}

package com.carbajal.ayudacampus.ui.components

import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.carbajal.ayudacampus.model.EstadoTicket

@Composable
fun EstadoChip(estado: EstadoTicket) {
    val (bgColor, textColor) = when (estado) {
        EstadoTicket.ABIERTO -> Color(0xFFE3F2FD) to Color(0xFF1976D2)
        EstadoTicket.EN_PROCESO -> Color(0xFFFFF3E0) to Color(0xFFE65100)
        EstadoTicket.RESUELTO -> Color(0xE8E8F5E9) to Color(0xFF2E7D32)
        EstadoTicket.CERRADO -> Color(0xFFECEFF1) to Color(0xFF455A64)
    }

    AssistChip(
        onClick = { },
        label = { Text(text = estado.name, color = textColor) },
        colors = AssistChipDefaults.assistChipColors(containerColor = bgColor)
    )
}

package com.carbajal.ayudacampus.ui.components

import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.carbajal.ayudacampus.model.Prioridad

@Composable
fun PrioridadChip(prioridad: Prioridad) {
    val (bgColor, textColor) = when (prioridad) {
        Prioridad.BAJA -> Color(0xFFF1F8E9) to Color(0xFF33691E)
        Prioridad.MEDIA -> Color(0xFFFFF8E1) to Color(0xFFF57F17)
        Prioridad.ALTA -> Color(0xFFFFEBEE) to Color(0xFFC62828)
    }

    AssistChip(
        onClick = { },
        label = { Text(text = "Prioridad: ${prioridad.name}", color = textColor) },
        colors = AssistChipDefaults.assistChipColors(containerColor = bgColor)
    )
}

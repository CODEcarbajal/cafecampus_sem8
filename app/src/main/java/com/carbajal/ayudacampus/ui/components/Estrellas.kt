package com.carbajal.ayudacampus.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun Estrellas(
    calificacion: Int,
    onCalificar: ((Int) -> Unit)? = null
) {
    Row {
        (1..5).forEach { i ->
            val icon = if (i <= calificacion) Icons.Filled.Star else Icons.Outlined.Star
            val tint = if (i <= calificacion) Color(0xFFFFC107) else Color.Gray

            Icon(
                imageVector = icon,
                contentDescription = "$i estrellas",
                tint = tint,
                modifier = Modifier.clickable(enabled = onCalificar != null) {
                    onCalificar?.invoke(i)
                }
            )
        }
    }
}

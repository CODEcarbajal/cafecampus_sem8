package com.carbajal.ayudacampus.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.carbajal.ayudacampus.data.local.entity.Comentario
import com.carbajal.ayudacampus.model.Rol

@Composable
fun BurbujaComentario(
    comentario: Comentario,
    esUsuarioActual: Boolean
) {
    val align = if (esUsuarioActual) Alignment.End else Alignment.Start
    val bgColor = if (esUsuarioActual) {
        MaterialTheme.colorScheme.primaryContainer
    } else {
        MaterialTheme.colorScheme.secondaryContainer
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalAlignment = align
    ) {
        Surface(
            shape = RoundedCornerShape(12.dp),
            color = bgColor,
            modifier = Modifier.fillMaxWidth(0.85f)
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = "${comentario.autorNombre} (${if (comentario.autorRol == Rol.ADMIN) "Soporte TI" else "Estudiante"})",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = comentario.texto,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

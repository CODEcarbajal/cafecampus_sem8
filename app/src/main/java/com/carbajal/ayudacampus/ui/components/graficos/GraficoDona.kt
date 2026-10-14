package com.carbajal.ayudacampus.ui.components.graficos

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.carbajal.ayudacampus.model.ConteoCategoria

@Composable
fun GraficoDona(datos: List<ConteoCategoria>) {
    val total = datos.sumOf { it.cantidad }
    val colores = listOf(
        Color(0xFF3F51B5), Color(0xFFE91E63), Color(0xFF009688),
        Color(0xFFFF9800), Color(0xFF9C27B0), Color(0xFF607D8B)
    )

    Canvas(modifier = Modifier.size(160.dp)) {
        var startAngle = -90f
        if (total == 0) {
            drawArc(
                color = Color.LightGray,
                startAngle = 0f,
                sweepAngle = 360f,
                useCenter = false,
                style = Stroke(width = 30f)
            )
        } else {
            datos.forEachIndexed { index, item ->
                val sweepAngle = (item.cantidad.toFloat() / total) * 360f
                drawArc(
                    color = colores[index % colores.size],
                    startAngle = startAngle,
                    sweepAngle = sweepAngle,
                    useCenter = false,
                    style = Stroke(width = 30f)
                )
                startAngle += sweepAngle
            }
        }
    }
}

package com.carbajal.ayudacampus.ui.soporte

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.carbajal.ayudacampus.ui.components.graficos.GraficoBarras
import com.carbajal.ayudacampus.ui.components.graficos.GraficoDona
import com.carbajal.ayudacampus.viewmodel.PanelViewModel

@Composable
fun PanelScreen(viewModel: PanelViewModel) {
    val categoriasCount by viewModel.ticketsPorCategoria.collectAsState()
    val estadoCount by viewModel.conteoPorEstado.collectAsState()

    Scaffold(
        topBar = { AppTopBar(titulo = "Panel de Control TI") }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Tickets por Categoría",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(12.dp))
            GraficoDona(datos = categoriasCount)

            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Estado de Atenciones",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(12.dp))
            GraficoBarras(datosEstado = estadoCount)
        }
    }
}

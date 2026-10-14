package com.carbajal.ayudacampus.ui.soporte

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.carbajal.ayudacampus.ui.components.AppTopBar
import com.carbajal.ayudacampus.ui.components.AyudaButton
import com.carbajal.ayudacampus.ui.components.AyudaTextField
import com.carbajal.ayudacampus.viewmodel.CategoriasViewModel

@Composable
fun FormularioCategoriaScreen(
    categoriaId: Int?,
    viewModel: CategoriasViewModel,
    onBack: () -> Unit
) {
    var nombre by remember { mutableStateOf("") }
    var icono by remember { mutableStateOf("tag") }
    var activa by remember { mutableStateOf(true) }

    Scaffold(
        topBar = { AppTopBar(titulo = if (categoriaId != null) "Editar Categoría" else "Nueva Categoría", onBack = onBack) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            AyudaTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = "Nombre de la categoría"
            )
            Spacer(modifier = Modifier.height(12.dp))

            AyudaTextField(
                value = icono,
                onValueChange = { icono = it },
                label = "Icono (pc, code, wifi, key, printer, tag)"
            )
            Spacer(modifier = Modifier.height(12.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = activa, onCheckedChange = { activa = it })
                Text(text = "Categoría activa")
            }
            Spacer(modifier = Modifier.height(24.dp))

            AyudaButton(
                texto = "Guardar Categoría",
                enabled = nombre.isNotBlank(),
                onClick = {
                    viewModel.guardarCategoria(
                        id = categoriaId ?: 0,
                        nombre = nombre,
                        icono = icono,
                        activa = activa
                    )
                    onBack()
                }
            )
        }
    }
}

package com.carbajal.ayudacampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carbajal.ayudacampus.data.local.entity.Categoria
import com.carbajal.ayudacampus.data.local.entity.Ticket
import com.carbajal.ayudacampus.data.prefs.PreferenciasStore
import com.carbajal.ayudacampus.data.repository.AyudaRepository
import com.carbajal.ayudacampus.model.Prioridad
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class NuevoTicketViewModel(
    private val repository: AyudaRepository,
    private val preferenciasStore: PreferenciasStore
) : ViewModel() {

    val categoriasActivas: StateFlow<List<Categoria>> = repository.obtenerCategoriasActivas()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun crearTicket(
        categoriaId: Int,
        titulo: String,
        descripcion: String,
        ubicacion: String,
        prioridad: Prioridad,
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            val usuarioId = preferenciasStore.usuarioIdFlow.firstOrNull() ?: return@launch
            val ticket = Ticket(
                clienteId = usuarioId,
                categoriaId = categoriaId,
                titulo = titulo,
                descripcion = descripcion,
                ubicacion = ubicacion,
                prioridad = prioridad
            )
            repository.crearTicket(ticket)
            onSuccess()
        }
    }
}

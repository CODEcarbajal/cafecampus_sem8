package com.carbajal.ayudacampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carbajal.ayudacampus.data.local.relation.TicketConDetalle
import com.carbajal.ayudacampus.data.repository.AyudaRepository
import com.carbajal.ayudacampus.model.ConteoCategoria
import com.carbajal.ayudacampus.model.EstadoTicket
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class PanelViewModel(private val repository: AyudaRepository) : ViewModel() {

    val todosLosTickets: StateFlow<List<TicketConDetalle>> = repository.obtenerTodosLosTickets()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val ticketsPorCategoria: StateFlow<List<ConteoCategoria>> = todosLosTickets
        .map { list ->
            list.groupBy { it.categoria.nombre }
                .map { (nombre, items) ->
                    ConteoCategoria(
                        categoriaNombre = nombre,
                        cantidad = items.size
                    )
                }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val conteoPorEstado: StateFlow<Map<EstadoTicket, Int>> = todosLosTickets
        .map { list ->
            list.groupBy { it.ticket.estado }
                .mapValues { it.value.size }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyMap()
        )
}

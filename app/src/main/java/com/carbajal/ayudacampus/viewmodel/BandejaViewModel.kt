package com.carbajal.ayudacampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carbajal.ayudacampus.data.local.relation.TicketConDetalle
import com.carbajal.ayudacampus.data.repository.AyudaRepository
import com.carbajal.ayudacampus.model.EstadoTicket
import com.carbajal.ayudacampus.model.Prioridad
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class BandejaViewModel(private val repository: AyudaRepository) : ViewModel() {

    val estadoFiltro = MutableStateFlow<EstadoTicket?>(null)
    val prioridadFiltro = MutableStateFlow<Prioridad?>(null)
    val busquedaQuery = MutableStateFlow("")

    val ticketsFiltrados: StateFlow<List<TicketConDetalle>> = combine(
        repository.obtenerTodosLosTickets(),
        estadoFiltro,
        prioridadFiltro,
        busquedaQuery
    ) { tickets, estado, prioridad, query ->
        tickets.filter { item ->
            val coincideEstado = estado == null || item.ticket.estado == estado
            val coincidePrioridad = prioridad == null || item.ticket.prioridad == prioridad
            val coincideQuery = query.isBlank() ||
                    item.ticket.titulo.contains(query, ignoreCase = true) ||
                    item.cliente.nombre.contains(query, ignoreCase = true) ||
                    item.categoria.nombre.contains(query, ignoreCase = true)

            coincideEstado && coincidePrioridad && coincideQuery
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )
}

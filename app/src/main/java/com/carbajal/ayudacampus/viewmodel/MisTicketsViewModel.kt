package com.carbajal.ayudacampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carbajal.ayudacampus.data.local.relation.TicketConDetalle
import com.carbajal.ayudacampus.data.prefs.PreferenciasStore
import com.carbajal.ayudacampus.data.repository.AyudaRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn

class MisTicketsViewModel(
    private val repository: AyudaRepository,
    preferenciasStore: PreferenciasStore
) : ViewModel() {

    @OptIn(ExperimentalCoroutinesApi::class)
    val misTickets: StateFlow<List<TicketConDetalle>> = preferenciasStore.usuarioIdFlow
        .flatMapLatest { id ->
            if (id != null) {
                repository.obtenerTicketsPorCliente(id)
            } else {
                flowOf(emptyList())
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
}

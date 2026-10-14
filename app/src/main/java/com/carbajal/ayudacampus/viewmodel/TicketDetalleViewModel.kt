package com.carbajal.ayudacampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carbajal.ayudacampus.data.local.entity.Comentario
import com.carbajal.ayudacampus.data.local.relation.TicketConDetalle
import com.carbajal.ayudacampus.data.prefs.PreferenciasStore
import com.carbajal.ayudacampus.data.repository.AyudaRepository
import com.carbajal.ayudacampus.model.Rol
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TicketDetalleViewModel(
    private val repository: AyudaRepository,
    private val preferenciasStore: PreferenciasStore
) : ViewModel() {

    private val _ticketId = MutableStateFlow<Int?>(null)

    fun setTicketId(id: Int) {
        _ticketId.value = id
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val ticketDetalle: StateFlow<TicketConDetalle?> = _ticketId
        .flatMapLatest { id ->
            if (id != null) {
                repository.obtenerTicketDetalle(id)
            } else {
                flowOf(null)
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    fun agregarComentario(texto: String) {
        val id = _ticketId.value ?: return
        if (texto.isBlank()) return

        viewModelScope.launch {
            val usuarioId = preferenciasStore.usuarioIdFlow.firstOrNull() ?: return@launch
            val usuario = repository.obtenerUsuarioPorIdSync(usuarioId) ?: return@launch

            val comentario = Comentario(
                ticketId = id,
                autorId = usuario.id,
                autorNombre = usuario.nombre,
                autorRol = usuario.rol,
                texto = texto
            )
            repository.agregarComentario(comentario)
        }
    }

    fun calificarAtencion(estrellas: Int) {
        val id = _ticketId.value ?: return
        viewModelScope.launch {
            repository.calificarTicket(id, estrellas)
        }
    }
}

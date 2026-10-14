package com.carbajal.ayudacampus.data.repository

import com.carbajal.ayudacampus.data.local.entity.Categoria
import com.carbajal.ayudacampus.data.local.entity.Comentario
import com.carbajal.ayudacampus.data.local.entity.Ticket
import com.carbajal.ayudacampus.data.local.entity.Usuario
import com.carbajal.ayudacampus.data.local.relation.TicketConDetalle
import com.carbajal.ayudacampus.model.EstadoTicket
import com.carbajal.ayudacampus.model.Prioridad
import kotlinx.coroutines.flow.Flow

interface AyudaRepository {
    suspend fun login(correo: String, clave: String): Usuario?
    fun obtenerUsuarioPorId(id: Int): Flow<Usuario?>
    suspend fun obtenerUsuarioPorIdSync(id: Int): Usuario?

    fun obtenerCategorias(): Flow<List<Categoria>>
    fun obtenerCategoriasActivas(): Flow<List<Categoria>>
    suspend fun guardarCategoria(categoria: Categoria)
    suspend fun eliminarCategoria(categoria: Categoria)

    fun obtenerTodosLosTickets(): Flow<List<TicketConDetalle>>
    fun obtenerTicketsPorCliente(clienteId: Int): Flow<List<TicketConDetalle>>
    fun obtenerTicketDetalle(ticketId: Int): Flow<TicketConDetalle?>

    suspend fun crearTicket(ticket: Ticket): Long
    suspend fun cambiarEstadoTicket(ticketId: Int, estado: EstadoTicket)
    suspend fun cambiarPrioridadTicket(ticketId: Int, prioridad: Prioridad)
    suspend fun calificarTicket(ticketId: Int, calificacion: Int)

    suspend fun agregarComentario(comentario: Comentario): Long
    fun obtenerComentarios(ticketId: Int): Flow<List<Comentario>>
}

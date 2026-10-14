package com.carbajal.ayudacampus.data.repository

import com.carbajal.ayudacampus.data.local.dao.CategoriaDao
import com.carbajal.ayudacampus.data.local.dao.TicketDao
import com.carbajal.ayudacampus.data.local.dao.UsuarioDao
import com.carbajal.ayudacampus.data.local.entity.Categoria
import com.carbajal.ayudacampus.data.local.entity.Comentario
import com.carbajal.ayudacampus.data.local.entity.Ticket
import com.carbajal.ayudacampus.data.local.entity.Usuario
import com.carbajal.ayudacampus.data.local.relation.TicketConDetalle
import com.carbajal.ayudacampus.model.EstadoTicket
import com.carbajal.ayudacampus.model.Prioridad
import kotlinx.coroutines.flow.Flow

class AyudaRepositoryRoom(
    private val usuarioDao: UsuarioDao,
    private val categoriaDao: CategoriaDao,
    private val ticketDao: TicketDao
) : AyudaRepository {

    override suspend fun login(correo: String, clave: String): Usuario? =
        usuarioDao.login(correo, clave)

    override fun obtenerUsuarioPorId(id: Int): Flow<Usuario?> =
        usuarioDao.obtenerPorId(id)

    override suspend fun obtenerUsuarioPorIdSync(id: Int): Usuario? =
        usuarioDao.obtenerPorIdSync(id)

    override fun obtenerCategorias(): Flow<List<Categoria>> =
        categoriaDao.obtenerTodas()

    override fun obtenerCategoriasActivas(): Flow<List<Categoria>> =
        categoriaDao.obtenerActivas()

    override suspend fun guardarCategoria(categoria: Categoria) {
        if (categoria.id == 0) {
            categoriaDao.insertar(categoria)
        } else {
            categoriaDao.actualizar(categoria)
        }
    }

    override suspend fun eliminarCategoria(categoria: Categoria) {
        categoriaDao.eliminar(categoria)
    }

    override fun obtenerTodosLosTickets(): Flow<List<TicketConDetalle>> =
        ticketDao.obtenerTodosConDetalle()

    override fun obtenerTicketsPorCliente(clienteId: Int): Flow<List<TicketConDetalle>> =
        ticketDao.obtenerPorCliente(clienteId)

    override fun obtenerTicketDetalle(ticketId: Int): Flow<TicketConDetalle?> =
        ticketDao.obtenerDetallePorId(ticketId)

    override suspend fun crearTicket(ticket: Ticket): Long =
        ticketDao.insertarTicket(ticket)

    override suspend fun cambiarEstadoTicket(ticketId: Int, estado: EstadoTicket) {
        val fechaCierre = if (estado == EstadoTicket.RESUELTO || estado == EstadoTicket.CERRADO) System.currentTimeMillis() else null
        ticketDao.cambiarEstado(ticketId, estado, fechaCierre)
    }

    override suspend fun cambiarPrioridadTicket(ticketId: Int, prioridad: Prioridad) {
        ticketDao.cambiarPrioridad(ticketId, prioridad)
    }

    override suspend fun calificarTicket(ticketId: Int, calificacion: Int) {
        ticketDao.calificarAtencion(ticketId, calificacion)
    }

    override suspend fun agregarComentario(comentario: Comentario): Long =
        ticketDao.insertarComentario(comentario)

    override fun obtenerComentarios(ticketId: Int): Flow<List<Comentario>> =
        ticketDao.obtenerComentarios(ticketId)
}

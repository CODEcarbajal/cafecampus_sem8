package com.carbajal.ayudacampus.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.carbajal.ayudacampus.data.local.entity.Comentario
import com.carbajal.ayudacampus.data.local.entity.Ticket
import com.carbajal.ayudacampus.data.local.relation.TicketConDetalle
import com.carbajal.ayudacampus.model.EstadoTicket
import com.carbajal.ayudacampus.model.Prioridad
import kotlinx.coroutines.flow.Flow

@Dao
interface TicketDao {
    @Transaction
    @Query("SELECT * FROM tickets ORDER BY fecha DESC")
    fun obtenerTodosConDetalle(): Flow<List<TicketConDetalle>>

    @Transaction
    @Query("SELECT * FROM tickets WHERE clienteId = :clienteId ORDER BY fecha DESC")
    fun obtenerPorCliente(clienteId: Int): Flow<List<TicketConDetalle>>

    @Transaction
    @Query("SELECT * FROM tickets WHERE id = :ticketId")
    fun obtenerDetallePorId(ticketId: Int): Flow<TicketConDetalle?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarTicket(ticket: Ticket): Long

    @Update
    suspend fun actualizarTicket(ticket: Ticket)

    @Query("UPDATE tickets SET estado = :estado, fechaCierre = :fechaCierre WHERE id = :ticketId")
    suspend fun cambiarEstado(ticketId: Int, estado: EstadoTicket, fechaCierre: Long? = null)

    @Query("UPDATE tickets SET prioridad = :prioridad WHERE id = :ticketId")
    suspend fun cambiarPrioridad(ticketId: Int, prioridad: Prioridad)

    @Query("UPDATE tickets SET calificacion = :calificacion WHERE id = :ticketId")
    suspend fun calificarAtencion(ticketId: Int, calificacion: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarComentario(comentario: Comentario): Long

    @Query("SELECT * FROM comentarios WHERE ticketId = :ticketId ORDER BY fecha ASC")
    fun obtenerComentarios(ticketId: Int): Flow<List<Comentario>>
}

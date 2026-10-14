package com.carbajal.ayudacampus.data.local

import com.carbajal.ayudacampus.data.local.entity.Categoria
import com.carbajal.ayudacampus.data.local.entity.Comentario
import com.carbajal.ayudacampus.data.local.entity.Ticket
import com.carbajal.ayudacampus.data.local.entity.Usuario
import com.carbajal.ayudacampus.model.EstadoTicket
import com.carbajal.ayudacampus.model.Prioridad
import com.carbajal.ayudacampus.model.Rol

object DatosIniciales {

    suspend fun poblarDatabase(db: AyudaDatabase) {
        val usuarioDao = db.usuarioDao()
        val categoriaDao = db.categoriaDao()
        val ticketDao = db.ticketDao()

        // Usuarios iniciales
        val clienteId = usuarioDao.insertar(
            Usuario(
                nombre = "Juan Pérez (Estudiante)",
                correo = "estudiante@campus.edu.pe",
                clave = "123456",
                rol = Rol.CLIENTE
            )
        ).toInt()

        val adminId = usuarioDao.insertar(
            Usuario(
                nombre = "Soporte TI Campus",
                correo = "soporte@campus.edu.pe",
                clave = "123456",
                rol = Rol.ADMIN
            )
        ).toInt()

        // Categorías iniciales
        val catWifi = categoriaDao.insertar(Categoria(nombre = "Redes y Wi-Fi", icono = "wifi")).toInt()
        val catHardware = categoriaDao.insertar(Categoria(nombre = "Equipos / Hardware", icono = "pc")).toInt()
        val catCuentas = categoriaDao.insertar(Categoria(nombre = "Cuentas y Accesos", icono = "key")).toInt()
        val catSoftware = categoriaDao.insertar(Categoria(nombre = "Software y Sistemas", icono = "code")).toInt()
        val catImpresoras = categoriaDao.insertar(Categoria(nombre = "Impresoras", icono = "printer")).toInt()

        // Tickets de prueba iniciales
        val ticket1Id = ticketDao.insertarTicket(
            Ticket(
                clienteId = clienteId,
                categoriaId = catWifi,
                titulo = "Sin acceso a Wi-Fi Eduroam",
                descripcion = "No puedo conectarme a la red inalámbrica desde el pabellón B.",
                ubicacion = "Pabellón B - Aula 204",
                prioridad = Prioridad.ALTA,
                estado = EstadoTicket.EN_PROCESO,
                fecha = System.currentTimeMillis() - 86400000L
            )
        ).toInt()

        ticketDao.insertarComentario(
            Comentario(
                ticketId = ticket1Id,
                autorId = clienteId,
                autorNombre = "Juan Pérez",
                autorRol = Rol.CLIENTE,
                texto = "Hola, me sale error de autenticación al ingresar mi correo institucional."
            )
        )

        ticketDao.insertarComentario(
            Comentario(
                ticketId = ticket1Id,
                autorId = adminId,
                autorNombre = "Soporte TI",
                autorRol = Rol.ADMIN,
                texto = "Hola Juan, estamos revisando el punto de acceso del pabellón B."
            )
        )

        ticketDao.insertarTicket(
            Ticket(
                clienteId = clienteId,
                categoriaId = catHardware,
                titulo = "Proyector no enciende",
                descripcion = "El proyector del aula de cómputo parpadea en rojo.",
                ubicacion = "Lab Cómputo 01",
                prioridad = Prioridad.MEDIA,
                estado = EstadoTicket.ABIERTO
            )
        )
    }
}

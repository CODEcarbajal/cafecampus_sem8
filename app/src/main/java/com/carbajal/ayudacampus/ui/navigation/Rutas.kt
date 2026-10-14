package com.carbajal.ayudacampus.ui.navigation

sealed class Rutas(val ruta: String) {
    object Splash : Rutas("splash")
    object Login : Rutas("login")

    // Estudiante
    object MisTickets : Rutas("mis_tickets")
    object NuevoTicket : Rutas("nuevo_ticket")
    object DetalleTicket : Rutas("detalle_ticket/{ticketId}") {
        fun crearRuta(ticketId: Int) = "detalle_ticket/$ticketId"
    }
    object Perfil : Rutas("perfil")

    // Soporte TI
    object Panel : Rutas("panel")
    object Bandeja : Rutas("bandeja")
    object AtenderTicket : Rutas("atender_ticket/{ticketId}") {
        fun crearRuta(ticketId: Int) = "atender_ticket/$ticketId"
    }
    object Categorias : Rutas("categorias")
    object FormularioCategoria : Rutas("form_categoria?categoriaId={categoriaId}") {
        fun crearRuta(categoriaId: Int? = null) = if (categoriaId != null) "form_categoria?categoriaId=$categoriaId" else "form_categoria"
    }
    object Ajustes : Rutas("ajustes")
}

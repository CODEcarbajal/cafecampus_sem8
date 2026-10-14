package com.carbajal.ayudacampus.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.ConfirmationNumber
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.carbajal.ayudacampus.AyudaApp
import com.carbajal.ayudacampus.model.Rol
import com.carbajal.ayudacampus.ui.cliente.DetalleTicketScreen
import com.carbajal.ayudacampus.ui.cliente.LoginScreen
import com.carbajal.ayudacampus.ui.cliente.MisTicketsScreen
import com.carbajal.ayudacampus.ui.cliente.NuevoTicketScreen
import com.carbajal.ayudacampus.ui.cliente.PerfilScreen
import com.carbajal.ayudacampus.ui.cliente.SplashScreen
import com.carbajal.ayudacampus.ui.soporte.AjustesScreen
import com.carbajal.ayudacampus.ui.soporte.AtenderTicketScreen
import com.carbajal.ayudacampus.ui.soporte.BandejaScreen
import com.carbajal.ayudacampus.ui.soporte.CategoriasScreen
import com.carbajal.ayudacampus.ui.soporte.FormularioCategoriaScreen
import com.carbajal.ayudacampus.ui.soporte.PanelScreen
import com.carbajal.ayudacampus.viewmodel.AtenderViewModel
import com.carbajal.ayudacampus.viewmodel.AyudaViewModelFactory
import com.carbajal.ayudacampus.viewmodel.BandejaViewModel
import com.carbajal.ayudacampus.viewmodel.CategoriasViewModel
import com.carbajal.ayudacampus.viewmodel.MisTicketsViewModel
import com.carbajal.ayudacampus.viewmodel.NuevoTicketViewModel
import com.carbajal.ayudacampus.viewmodel.PanelViewModel
import com.carbajal.ayudacampus.viewmodel.SesionViewModel
import com.carbajal.ayudacampus.viewmodel.TicketDetalleViewModel

@Composable
fun NavAyuda() {
    val context = LocalContext.current.applicationContext as AyudaApp
    val factory = AyudaViewModelFactory(context.repository, context.preferenciasStore)

    val sesionViewModel: SesionViewModel = viewModel(factory = factory)
    val categoriasViewModel: CategoriasViewModel = viewModel(factory = factory)
    val misTicketsViewModel: MisTicketsViewModel = viewModel(factory = factory)
    val nuevoTicketViewModel: NuevoTicketViewModel = viewModel(factory = factory)
    val ticketDetalleViewModel: TicketDetalleViewModel = viewModel(factory = factory)
    val panelViewModel: PanelViewModel = viewModel(factory = factory)
    val bandejaViewModel: BandejaViewModel = viewModel(factory = factory)
    val atenderViewModel: AtenderViewModel = viewModel(factory = factory)

    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val usuarioActual by sesionViewModel.usuarioActual.collectAsState()

    val mostrarBottomNav = currentRoute in listOf(
        Rutas.MisTickets.ruta,
        Rutas.Perfil.ruta,
        Rutas.Panel.ruta,
        Rutas.Bandeja.ruta,
        Rutas.Categorias.ruta,
        Rutas.Ajustes.ruta
    )

    Scaffold(
        bottomBar = {
            if (mostrarBottomNav && usuarioActual != null) {
                NavigationBar {
                    if (usuarioActual?.rol == Rol.CLIENTE) {
                        NavigationBarItem(
                            selected = currentRoute == Rutas.MisTickets.ruta,
                            onClick = { navController.navigate(Rutas.MisTickets.ruta) },
                            icon = { Icon(Icons.Default.ConfirmationNumber, contentDescription = "Mis Tickets") },
                            label = { Text("Mis Tickets") }
                        )
                        NavigationBarItem(
                            selected = currentRoute == Rutas.Perfil.ruta,
                            onClick = { navController.navigate(Rutas.Perfil.ruta) },
                            icon = { Icon(Icons.Default.Person, contentDescription = "Perfil") },
                            label = { Text("Perfil") }
                        )
                    } else if (usuarioActual?.rol == Rol.ADMIN) {
                        NavigationBarItem(
                            selected = currentRoute == Rutas.Panel.ruta,
                            onClick = { navController.navigate(Rutas.Panel.ruta) },
                            icon = { Icon(Icons.Default.Dashboard, contentDescription = "Panel") },
                            label = { Text("Panel") }
                        )
                        NavigationBarItem(
                            selected = currentRoute == Rutas.Bandeja.ruta,
                            onClick = { navController.navigate(Rutas.Bandeja.ruta) },
                            icon = { Icon(Icons.Default.Build, contentDescription = "Bandeja") },
                            label = { Text("Bandeja") }
                        )
                        NavigationBarItem(
                            selected = currentRoute == Rutas.Categorias.ruta,
                            onClick = { navController.navigate(Rutas.Categorias.ruta) },
                            icon = { Icon(Icons.Default.Category, contentDescription = "Categorías") },
                            label = { Text("Categorías") }
                        )
                        NavigationBarItem(
                            selected = currentRoute == Rutas.Ajustes.ruta,
                            onClick = { navController.navigate(Rutas.Ajustes.ruta) },
                            icon = { Icon(Icons.Default.Settings, contentDescription = "Ajustes") },
                            label = { Text("Ajustes") }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Rutas.Splash.ruta,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Rutas.Splash.ruta) {
                SplashScreen(
                    onTimeout = {
                        val usuario = sesionViewModel.usuarioActual.value
                        if (usuario != null) {
                            val destino = if (usuario.rol == Rol.ADMIN) Rutas.Panel.ruta else Rutas.MisTickets.ruta
                            navController.navigate(destino) {
                                popUpTo(Rutas.Splash.ruta) { inclusive = true }
                            }
                        } else {
                            navController.navigate(Rutas.Login.ruta) {
                                popUpTo(Rutas.Splash.ruta) { inclusive = true }
                            }
                        }
                    }
                )
            }

            composable(Rutas.Login.ruta) {
                LoginScreen(
                    viewModel = sesionViewModel,
                    onLoginExitoso = { rol ->
                        val destino = if (rol == Rol.ADMIN) Rutas.Panel.ruta else Rutas.MisTickets.ruta
                        navController.navigate(destino) {
                            popUpTo(Rutas.Login.ruta) { inclusive = true }
                        }
                    }
                )
            }

            // Cliente / Estudiante
            composable(Rutas.MisTickets.ruta) {
                MisTicketsScreen(
                    viewModel = misTicketsViewModel,
                    onNuevoTicket = { navController.navigate(Rutas.NuevoTicket.ruta) },
                    onVerDetalle = { ticketId ->
                        navController.navigate(Rutas.DetalleTicket.crearRuta(ticketId))
                    }
                )
            }

            composable(Rutas.NuevoTicket.ruta) {
                NuevoTicketScreen(
                    viewModel = nuevoTicketViewModel,
                    onBack = { navController.popBackStack() },
                    onTicketCreado = { navController.popBackStack() }
                )
            }

            composable(
                route = Rutas.DetalleTicket.ruta,
                arguments = listOf(navArgument("ticketId") { type = NavType.IntType })
            ) { backStackEntry ->
                val ticketId = backStackEntry.arguments?.getInt("ticketId") ?: 0
                DetalleTicketScreen(
                    ticketId = ticketId,
                    viewModel = ticketDetalleViewModel,
                    onBack = { navController.popBackStack() }
                )
            }

            composable(Rutas.Perfil.ruta) {
                PerfilScreen(
                    viewModel = sesionViewModel,
                    onCerrarSesion = {
                        navController.navigate(Rutas.Login.ruta) {
                            popUpTo(0) { inclusive = true }
                        }
                    }
                )
            }

            // Soporte TI
            composable(Rutas.Panel.ruta) {
                PanelScreen(viewModel = panelViewModel)
            }

            composable(Rutas.Bandeja.ruta) {
                BandejaScreen(
                    viewModel = bandejaViewModel,
                    onAtenderTicket = { ticketId ->
                        navController.navigate(Rutas.AtenderTicket.crearRuta(ticketId))
                    }
                )
            }

            composable(
                route = Rutas.AtenderTicket.ruta,
                arguments = listOf(navArgument("ticketId") { type = NavType.IntType })
            ) { backStackEntry ->
                val ticketId = backStackEntry.arguments?.getInt("ticketId") ?: 0
                AtenderTicketScreen(
                    ticketId = ticketId,
                    viewModel = atenderViewModel,
                    onBack = { navController.popBackStack() }
                )
            }

            composable(Rutas.Categorias.ruta) {
                CategoriasScreen(
                    viewModel = categoriasViewModel,
                    onNuevaCategoria = { navController.navigate(Rutas.FormularioCategoria.crearRuta()) },
                    onEditarCategoria = { categoriaId ->
                        navController.navigate(Rutas.FormularioCategoria.crearRuta(categoriaId))
                    }
                )
            }

            composable(
                route = Rutas.FormularioCategoria.ruta,
                arguments = listOf(navArgument("categoriaId") {
                    type = NavType.IntType
                    defaultValue = -1
                })
            ) { backStackEntry ->
                val idArg = backStackEntry.arguments?.getInt("categoriaId") ?: -1
                val categoriaId = if (idArg != -1) idArg else null
                FormularioCategoriaScreen(
                    categoriaId = categoriaId,
                    viewModel = categoriasViewModel,
                    onBack = { navController.popBackStack() }
                )
            }

            composable(Rutas.Ajustes.ruta) {
                AjustesScreen(
                    viewModel = sesionViewModel,
                    onCerrarSesion = {
                        navController.navigate(Rutas.Login.ruta) {
                            popUpTo(0) { inclusive = true }
                        }
                    }
                )
            }
        }
    }
}

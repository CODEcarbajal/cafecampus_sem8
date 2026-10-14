package com.carbajal.ayudacampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carbajal.ayudacampus.data.local.entity.Usuario
import com.carbajal.ayudacampus.data.prefs.PreferenciasStore
import com.carbajal.ayudacampus.data.repository.AyudaRepository
import com.carbajal.ayudacampus.model.Rol
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class SesionViewModel(
    private val repository: AyudaRepository,
    private val preferenciasStore: PreferenciasStore
) : ViewModel() {

    private val _usuarioActual = MutableStateFlow<Usuario?>(null)
    val usuarioActual: StateFlow<Usuario?> = _usuarioActual.asStateFlow()

    private val _temaOscuro = MutableStateFlow(false)
    val temaOscuro: StateFlow<Boolean> = _temaOscuro.asStateFlow()

    init {
        viewModelScope.launch {
            preferenciasStore.temaOscuroFlow.collect { _temaOscuro.value = it }
        }
        cargarSesionGuardada()
    }

    private fun cargarSesionGuardada() {
        viewModelScope.launch {
            val userId = preferenciasStore.usuarioIdFlow.firstOrNull()
            if (userId != null) {
                _usuarioActual.value = repository.obtenerUsuarioPorIdSync(userId)
            }
        }
    }

    fun login(correo: String, clave: String, onResult: (Boolean, String?) -> Unit) {
        viewModelScope.launch {
            val usuario = repository.login(correo, clave)
            if (usuario != null) {
                _usuarioActual.value = usuario
                preferenciasStore.guardarSesion(usuario.id, usuario.rol)
                onResult(true, null)
            } else {
                onResult(false, "Credenciales incorrectas")
            }
        }
    }

    fun cerrarSesion() {
        viewModelScope.launch {
            preferenciasStore.cerrarSesion()
            _usuarioActual.value = null
        }
    }

    fun toggleTemaOscuro(oscuro: Boolean) {
        viewModelScope.launch {
            preferenciasStore.guardarTemaOscuro(oscuro)
        }
    }
}

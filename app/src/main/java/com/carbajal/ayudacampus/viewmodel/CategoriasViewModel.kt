package com.carbajal.ayudacampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carbajal.ayudacampus.data.local.entity.Categoria
import com.carbajal.ayudacampus.data.repository.AyudaRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CategoriasViewModel(private val repository: AyudaRepository) : ViewModel() {

    val categorias: StateFlow<List<Categoria>> = repository.obtenerCategorias()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun guardarCategoria(id: Int, nombre: String, icono: String, activa: Boolean) {
        viewModelScope.launch {
            repository.guardarCategoria(
                Categoria(id = id, nombre = nombre, icono = icono, activa = activa)
            )
        }
    }

    fun eliminarCategoria(categoria: Categoria) {
        viewModelScope.launch {
            repository.eliminarCategoria(categoria)
        }
    }
}

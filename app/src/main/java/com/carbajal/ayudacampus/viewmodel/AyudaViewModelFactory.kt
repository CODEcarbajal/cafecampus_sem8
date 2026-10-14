package com.carbajal.ayudacampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.carbajal.ayudacampus.data.prefs.PreferenciasStore
import com.carbajal.ayudacampus.data.repository.AyudaRepository

class AyudaViewModelFactory(
    private val repository: AyudaRepository,
    private val preferenciasStore: PreferenciasStore
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(SesionViewModel::class.java) -> {
                SesionViewModel(repository, preferenciasStore) as T
            }
            modelClass.isAssignableFrom(CategoriasViewModel::class.java) -> {
                CategoriasViewModel(repository) as T
            }
            modelClass.isAssignableFrom(MisTicketsViewModel::class.java) -> {
                MisTicketsViewModel(repository, preferenciasStore) as T
            }
            modelClass.isAssignableFrom(NuevoTicketViewModel::class.java) -> {
                NuevoTicketViewModel(repository, preferenciasStore) as T
            }
            modelClass.isAssignableFrom(TicketDetalleViewModel::class.java) -> {
                TicketDetalleViewModel(repository, preferenciasStore) as T
            }
            modelClass.isAssignableFrom(PanelViewModel::class.java) -> {
                PanelViewModel(repository) as T
            }
            modelClass.isAssignableFrom(BandejaViewModel::class.java) -> {
                BandejaViewModel(repository) as T
            }
            modelClass.isAssignableFrom(AtenderViewModel::class.java) -> {
                AtenderViewModel(repository, preferenciasStore) as T
            }
            else -> throw IllegalArgumentException("ViewModel Desconocido: ${modelClass.name}")
        }
    }
}

package com.carbajal.ayudacampus

import android.app.Application
import com.carbajal.ayudacampus.data.local.AyudaDatabase
import com.carbajal.ayudacampus.data.prefs.PreferenciasStore
import com.carbajal.ayudacampus.data.repository.AyudaRepository
import com.carbajal.ayudacampus.data.repository.AyudaRepositoryRoom

class AyudaApp : Application() {
    val database: AyudaDatabase by lazy { AyudaDatabase.getDatabase(this) }
    val repository: AyudaRepository by lazy { AyudaRepositoryRoom(database.usuarioDao(), database.categoriaDao(), database.ticketDao()) }
    val preferenciasStore: PreferenciasStore by lazy { PreferenciasStore(this) }
}

package com.carbajal.ayudacampus.data.prefs

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.carbajal.ayudacampus.model.Rol
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "ayuda_prefs")

class PreferenciasStore(private val context: Context) {

    companion object {
        val USUARIO_ID = intPreferencesKey("usuario_id")
        val USUARIO_ROL = stringPreferencesKey("usuario_rol")
        val TEMA_OSCURO = booleanPreferencesKey("tema_oscuro")
    }

    val usuarioIdFlow: Flow<Int?> = context.dataStore.data.map { prefs ->
        prefs[USUARIO_ID]
    }

    val usuarioRolFlow: Flow<Rol?> = context.dataStore.data.map { prefs ->
        val rolStr = prefs[USUARIO_ROL]
        if (rolStr != null) {
            try { Rol.valueOf(rolStr) } catch (_: Exception) { null }
        } else null
    }

    val temaOscuroFlow: Flow<Boolean> = context.dataStore.data.map { prefs ->
        prefs[TEMA_OSCURO] ?: false
    }

    suspend fun guardarSesion(usuarioId: Int, rol: Rol) {
        context.dataStore.edit { prefs ->
            prefs[USUARIO_ID] = usuarioId
            prefs[USUARIO_ROL] = rol.name
        }
    }

    suspend fun cerrarSesion() {
        context.dataStore.edit { prefs ->
            prefs.remove(USUARIO_ID)
            prefs.remove(USUARIO_ROL)
        }
    }

    suspend fun guardarTemaOscuro(oscuro: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[TEMA_OSCURO] = oscuro
        }
    }
}

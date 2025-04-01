package com.example.lesantarosa.database.preferences

import android.content.Context
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "cart_prefs")

object CartPreferences {

    private val NOTE_KEY = stringPreferencesKey("cart_note")
    private val DISCOUNT_KEY = doublePreferencesKey("cart_discount")

    // ===================== CartNote Data =====================

    private suspend fun saveNote(context: Context, note: String) {
        context.applicationContext.dataStore.edit { prefs ->
            prefs[NOTE_KEY] = note
        }
    }

    private suspend fun clearNote(context: Context) {
        context.applicationContext.dataStore.edit { prefs ->
            prefs.remove(NOTE_KEY)
        }
    }

    suspend fun updateNote(context: Context, note: String?) {
        note?.takeIf { it.isNotEmpty() }?.let {
            saveNote(context, it)
        } ?: clearNote(context)
    }

    fun getCartNote(context: Context): Flow<String> {
        return context.applicationContext.dataStore.data
            .map { prefs -> prefs[NOTE_KEY] ?: "" }
    }

    // ===================== CartDiscount Data =====================

    private suspend fun saveDiscount(context: Context, discount: Double) {
        context.applicationContext.dataStore.edit { prefs ->
            prefs[DISCOUNT_KEY] = discount
        }
    }

    private suspend fun clearDiscount(context: Context) {
        context.applicationContext.dataStore.edit { prefs ->
            prefs.remove(DISCOUNT_KEY)
        }
    }

    suspend fun updateDiscount(context: Context, discount: Double?) {
        discount?.takeIf { it > 0 }?.let { saveDiscount(context, it) } ?: clearDiscount(context)
    }

    fun getCartDiscount(context: Context): Flow<Double> {
        return context.applicationContext.dataStore.data
            .map { prefs -> prefs[DISCOUNT_KEY] ?: 0.0 }
    }
}
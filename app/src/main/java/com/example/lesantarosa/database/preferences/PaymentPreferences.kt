package com.example.lesantarosa.database.preferences

import android.content.Context
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.lesantarosa.models.data.Payment
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "payment_prefs")

object PaymentPreferences {

    private val PAYMENTS_KEY = stringPreferencesKey("payments")
    private val gson = Gson()

    private var _totalPaidPrice = 0.0
    val totalPaidPrice get() = _totalPaidPrice

    private suspend fun savePayments(context: Context, payments: List<Payment>) {
        val jsonPayments = gson.toJson(payments)
        context.applicationContext.dataStore.edit { prefs ->
            prefs[PAYMENTS_KEY] = jsonPayments
        }

        _totalPaidPrice = payments.sumOf { it.totalPrice }
    }

    private suspend fun clearPayments(context: Context) {
        context.applicationContext.dataStore.edit { prefs ->
            prefs.remove(PAYMENTS_KEY)
            _totalPaidPrice = 0.0
        }
    }

    suspend fun addPayment(context: Context, payment: Payment) {
        val currentPayments = getPayments(context).first()
        val updatedPayments = currentPayments + payment
        savePayments(context, updatedPayments)
    }

    suspend fun removePayment(context: Context, payment: Payment?) {
        payment ?: let { clearPayments(context) ; return }

        val currentPayments = getPayments(context).first()
        val updatedPayments = currentPayments.filter { it != payment }
        savePayments(context, updatedPayments)
    }

    fun getPayments(context: Context): Flow<List<Payment>> {
        return context.applicationContext.dataStore.data
            .map { prefs ->
                val json = prefs[PAYMENTS_KEY] ?: "[]"
                val type = object : TypeToken<List<Payment>>() {}.type
                gson.fromJson(json, type) ?: emptyList()
            }
    }
}
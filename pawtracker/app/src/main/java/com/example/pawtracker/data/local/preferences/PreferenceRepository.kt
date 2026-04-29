package com.example.pawtracker.data.local.preferences

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class PreferenceRepository(private val context: Context) {

    val Context.dataStore by preferencesDataStore(name = "user_settings")

    companion object {
        val HAS_COMPLETED_ONBOARDING = booleanPreferencesKey("has_completed_onboarding")
    }

    val onboardingCompleted: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[HAS_COMPLETED_ONBOARDING] ?: false
        }

    suspend fun setOnboardingCompleted() {
        context.dataStore.edit { preferences ->
            preferences[HAS_COMPLETED_ONBOARDING] = true
        }
    }
}

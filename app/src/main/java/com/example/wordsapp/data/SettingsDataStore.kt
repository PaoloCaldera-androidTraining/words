package com.example.wordsapp.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

// Name of the preference datastore
private const val LAYOUT_PREFERENCES_NAME = "layout_preferences"

// Create the preferences datastore instance using property delegation
private val Context.datastore: DataStore<Preferences> by preferencesDataStore(
    name = LAYOUT_PREFERENCES_NAME
)

class SettingsDataStore(context: Context) {

    companion object {
        // Create a variable of type Preferences.Key<Boolean>, to store the key for a boolean value
        private val IS_LINEAR_LAYOUT_MANAGER = booleanPreferencesKey("is_linear_layout_manager")
    }

    /*  Preferences datastore exposes data in a Flow<Preferences> object, which emits the new
        value everytime a preference has changed; to just expose the Boolean value, use the map
        function which maps to preferenceFlow just the boolean value
     */
    val preferenceFlow: Flow<Boolean> = context.datastore.data
        // An IOException can occur when accessing the DataStore object: catch the error
        .catch {
            if (it is IOException) {
                it.printStackTrace()
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map {
            // On the first run, the boolean value is null, therefore map the default "true" value
            it[IS_LINEAR_LAYOUT_MANAGER] ?: true
        }

    // Make the method a suspend function since edit() is executed in the Dispatcher.IO coroutine
    suspend fun saveLayoutToPreferencesStore(isLinearLayoutManager: Boolean, context: Context) {

        // edit(): function that transactionally updates the data in the DataStore object
        context.datastore.edit {
            it[IS_LINEAR_LAYOUT_MANAGER] = isLinearLayoutManager
        }
    }

}


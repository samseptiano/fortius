package com.samseptiano.base.data

import android.content.Context
import androidx.datastore.DataStore
import androidx.datastore.preferences.Preferences
import androidx.datastore.preferences.clear
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.remove

/**
 * Created by samuel.septiano on 26/06/2023.
 */
abstract class BaseDataStore constructor(
    context: Context,
    private val dataStoreName: String
) {

     private val applicationContext: Context = context.applicationContext
     val dataStore: DataStore<androidx.datastore.preferences.Preferences> by lazy {
        applicationContext.createDataStore(
            name = dataStoreName
        )
    }

    suspend fun clearData(key: Preferences.Key<String>) {
        dataStore.edit { preferences ->
            preferences.remove(key)
        }
    }

    suspend fun clearAll() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}
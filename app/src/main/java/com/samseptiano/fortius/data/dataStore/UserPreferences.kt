package com.samseptiano.fortius.data.dataStore

import android.content.Context
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.preferencesKey
import com.samseptiano.base.data.BaseDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Created by samuel.septiano on 30/05/2023.
 */

data class UserPreferencesModel(
    var isLogin: Boolean? = false,
    val id:String,
    val name:String,
    val token:String,
    val email:String,
    val role:String,
    val company_id:String,
    val company_name:String,
    val employee_id:String){

    suspend fun saveToDataStore(userPreferences: UserPreferences){
        userPreferences.saveIsLogin(isLogin?:false)
        userPreferences.saveID(id)
        userPreferences.saveName(name)
        userPreferences.saveToken(token)
        userPreferences.saveEmail(email)
        userPreferences.saveCompanyID(company_id)
        userPreferences.saveRole(role)
        userPreferences.saveCompanyName(company_name)
        userPreferences.saveEmployeeId(employee_id)
    }
}

class UserPreferences(context: Context) : BaseDataStore(context, "user_preferences"){
    companion object {
        val KEY_ISLOGIN = preferencesKey<Boolean>("key_is_login")
        val KEY_ID = preferencesKey<String>("key_id")
        val KEY_NAME = preferencesKey<String>("key_name")
        val KEY_TOKEN = preferencesKey<String>("key_token")
        val KEY_EMAIL = preferencesKey<String>("key_email")
        val KEY_ROLE = preferencesKey<String>("key_role")
        val KEY_COMPANY_ID = preferencesKey<String>("key_company_id")
        val KEY_COMPANY_NAME = preferencesKey<String>("key_company_name")
        val KEY_EMPLOYEE_ID = preferencesKey<String>("key_employee_id")
    }

    //============================== IS LOGIN =================================
    val isLogin: Flow<Boolean?>
        get() = dataStore.data.map { preferences ->
            preferences[KEY_ISLOGIN]
        }

    suspend fun saveIsLogin(isLogin: Boolean) {
        dataStore.edit { preferences ->
            preferences[KEY_ISLOGIN] = isLogin
        }
    }

    //============================== ID =================================
    val id: Flow<String?>
        get() = dataStore.data.map { preferences ->
            preferences[KEY_ID]
        }

    suspend fun saveID(id: String) {
        dataStore.edit { preferences ->
            preferences[KEY_ID] = id
        }
    }


    //============================== NAME =================================
    val name: Flow<String?>
        get() = dataStore.data.map { preferences ->
            preferences[KEY_NAME]
        }

    suspend fun saveName(name: String) {
        dataStore.edit { preferences ->
            preferences[KEY_NAME] = name
        }
    }


    //============================== EMAIL =================================
    val email: Flow<String?>
        get() = dataStore.data.map { preferences ->
            preferences[KEY_EMAIL]
        }

    suspend fun saveEmail(email: String) {
        dataStore.edit { preferences ->
            preferences[KEY_EMAIL] = email
        }
    }

    //============================== TOKEN =================================

    val token: Flow<String?>
        get() = dataStore.data.map { preferences ->
            preferences[KEY_TOKEN]
        }

    suspend fun saveToken(token: String) {
        dataStore.edit { preferences ->
            preferences[KEY_TOKEN] = token
        }
    }

    //============================== ROLE =================================

    val role: Flow<String?>
        get() = dataStore.data.map { preferences ->
            preferences[KEY_ROLE]
        }

    suspend fun saveRole(role: String) {
        dataStore.edit { preferences ->
            preferences[KEY_ROLE] = role
        }
    }

    //============================== COMPANY ID =================================
    val companyID: Flow<String?>
        get() = dataStore.data.map { preferences ->
            preferences[KEY_COMPANY_ID]
        }

    suspend fun saveCompanyID(companyID: String) {
        dataStore.edit { preferences ->
            preferences[KEY_COMPANY_ID] = companyID
        }
    }



    //============================== COMPANY NAME =================================
    val companyName: Flow<String?>
        get() = dataStore.data.map { preferences ->
            preferences[KEY_COMPANY_NAME]
        }

    suspend fun saveCompanyName(companyName: String) {
        dataStore.edit { preferences ->
            preferences[KEY_COMPANY_NAME] = companyName
        }
    }


    //============================== EMPLOYEE ID =================================
    val employeeId: Flow<String?>
        get() = dataStore.data.map { preferences ->
            preferences[KEY_EMPLOYEE_ID]
        }

    suspend fun saveEmployeeId(employeeId: String) {
        dataStore.edit { preferences ->
            preferences[KEY_EMPLOYEE_ID] = employeeId
        }
    }
}
package com.samseptiano.fortius.data.repository

import android.util.Log
import com.samseptiano.fortius.data.domain.APIService
import com.samseptiano.fortius.data.model.request.LoginRequest
import com.samseptiano.fortius.data.model.response.LoginDataResponse
import com.samseptiano.fortius.data.repository.datasource.FortiusDataSource
import com.samseptiano.base.apihandler.ApiHandler
import com.samseptiano.base.data.ResultState
import javax.inject.Inject

/**
 * @author SamuelSep on 4/20/2021.
 */
class FortiusDataSourceImpl @Inject constructor(
    private val apiService: APIService,
) : FortiusDataSource {
    override suspend fun login(loginRequest: LoginRequest): ResultState<LoginDataResponse?> {
        return try {
            val result = ApiHandler.handleApi {
                apiService.login(loginRequest)
            }
            ResultState.Success(result)

        } catch (e: Exception) {
            Log.d("error login", e.toString())
            ResultState.Error(e)
        }
    }
}
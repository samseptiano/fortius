package com.samseptiano.fortius.data.repository.datasource

import com.samseptiano.fortius.data.model.request.LoginRequest
import com.samseptiano.fortius.data.model.response.LoginDataResponse
import com.samseptiano.base.data.ResultState

/**
 * @author SamuelSep on 4/20/2021.
 */

interface FortiusDataSource {
    suspend fun login(loginRequest: LoginRequest): ResultState<LoginDataResponse?>
}
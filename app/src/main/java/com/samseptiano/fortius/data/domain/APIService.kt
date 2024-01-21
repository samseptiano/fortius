package com.samseptiano.fortius.data.domain

import com.samseptiano.fortius.data.model.request.LoginRequest
import com.samseptiano.fortius.data.model.response.LoginDataResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST


/**
 * Created by samuel.septiano on 31/05/2023.
 */
interface APIService {
    @Headers("Content-Type: application/json")
    @POST("login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): Response<LoginDataResponse>


}
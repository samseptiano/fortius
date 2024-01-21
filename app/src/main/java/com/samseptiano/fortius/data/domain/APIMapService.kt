package com.samseptiano.fortius.data.domain

import com.samseptiano.fortius.data.model.response.MapResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


/**
 * Created by samuel.septiano on 31/05/2023.
 */
interface APIMapService {
    @GET("reverse-geocode-client")
    suspend fun getLocation(@Query("latitude") latitude:String, @Query("longitude") longitude:String, @Query("localityLanguage") language:String): Response<MapResponse>
}
package com.samseptiano.fortius.data.repository.datasource

import com.samseptiano.fortius.data.model.request.LoginRequest
import com.samseptiano.fortius.data.model.response.LoginDataResponse
import com.samseptiano.base.data.ResultState
import com.samseptiano.fortius.data.model.response.MapResponse

/**
 * @author SamuelSep on 4/20/2021.
 */

interface MapDataSource {
    suspend fun getLocation(lat:String, lon: String): ResultState<MapResponse?>
}
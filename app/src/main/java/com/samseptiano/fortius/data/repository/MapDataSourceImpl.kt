package com.samseptiano.fortius.data.repository

import android.util.Log
import com.samseptiano.base.apihandler.ApiHandler
import com.samseptiano.base.data.ResultState
import com.samseptiano.fortius.data.domain.APIMapService
import com.samseptiano.fortius.data.model.response.MapResponse
import com.samseptiano.fortius.data.repository.datasource.MapDataSource
import javax.inject.Inject

/**
 * @author SamuelSep on 4/20/2021.
 */
class MapDataSourceImpl @Inject constructor(
    private val apiService: APIMapService,
) : MapDataSource {
    override suspend fun getLocation(lat:String, lon:String): ResultState<MapResponse?> {
        return try {
            val result = ApiHandler.handleApi {
                apiService.getLocation(lat, lon, "en")
            }
            ResultState.Success(result)

        } catch (e: Exception) {
            Log.d("error login", e.toString())
            ResultState.Error(e)
        }
    }
}
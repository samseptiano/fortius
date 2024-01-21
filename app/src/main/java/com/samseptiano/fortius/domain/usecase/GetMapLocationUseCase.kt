package com.samseptiano.fortius.domain.usecase

import com.samseptiano.base.data.ResultState
import com.samseptiano.base.usecase.BaseUseCase
import com.samseptiano.fortius.data.model.response.MapResponse
import com.samseptiano.fortius.data.repository.datasource.MapDataSource
import javax.inject.Inject

/**
 * @author SamuelSep on 4/20/2021.
 */
class GetMapLocationUseCase @Inject constructor(
    private val repository: MapDataSource
) :
    BaseUseCase<ResultState<MapResponse?>, GetMapLocationUseCase.Params>() {

    class Params (val latitude:String, val longitude:String)

    override suspend fun run(params: Params): ResultState<MapResponse?> {
        return repository.getLocation(params.latitude, params.longitude)
    }

}
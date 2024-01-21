package com.samseptiano.fortius.domain.usecase.absensionUseCase

import android.util.Log
import com.samseptiano.fortius.data.model.response.AbsensionModel
import com.samseptiano.fortius.data.repository.datasource.RoomAbsensionDataSource
import com.samseptiano.fortius.data.roomModel.RoomAbsensionModel
import com.samseptiano.base.usecase.BaseUseCase
import javax.inject.Inject

/**
 * @author SamuelSep on 4/20/2021.
 */
class GetAbsensionUseCase @Inject constructor(
    private val roomAbsensionDataSource: RoomAbsensionDataSource,
) : BaseUseCase<ArrayList<AbsensionModel>, GetAbsensionUseCase.Params>() {

    class Params(val id: String?, val date: String?, val status: String?)

    override suspend fun run(params: Params): ArrayList<AbsensionModel> {
        val result = arrayListOf<RoomAbsensionModel>()
        val resultData = arrayListOf<AbsensionModel>()

                try {
                    if (!params.id.isNullOrEmpty()) {
                        result.add(roomAbsensionDataSource.getAllDataById(params.id.toLong()))
                    }
                    else if(!params.status.isNullOrEmpty() && !params.date.isNullOrEmpty()){
                        roomAbsensionDataSource.getAllDataByDateAndStatus(params.date?:"", params.status?:"")?.let { result.add(it) }
                    }
                    else if (!params.date.isNullOrEmpty()) {
                        result.addAll(roomAbsensionDataSource.getAllDataByDate(params.date))
                    }
                    else if (params.date.isNullOrEmpty() && params.id.isNullOrEmpty() && params.status.isNullOrEmpty()) {
                        result.addAll(roomAbsensionDataSource.getAllData())
                    }


                    if (result.isNotEmpty()) {
                        result.map {
                            resultData.add(
                                AbsensionModel.toAbsensionModel(
                                    id = it.Id,
                                    employeeId = it.employeeId,
                                    email = it.email,
                                    name = it.name,
                                    role = it.role,
                                    companyId = it.companyId,
                                    companyName = it.companyName,
                                    date = it.date,
                                    time = it.time,
                                    status = it.status,
                                    lat = it.lat,
                                    lon = it.lon,
                                    location = it.location,
                                    photoPath = it.photoPath
                                )
                            )
                        }
                    }
                } catch (e: Exception) {
                    Log.e("error get local", e.toString())
                }

        return resultData
    }


}
package com.samseptiano.fortius.domain.usecase.absensionUseCase

import com.samseptiano.fortius.data.model.response.AbsensionModel
import com.samseptiano.fortius.data.repository.datasource.RoomAbsensionDataSource
import com.samseptiano.fortius.data.roomModel.RoomAbsensionModel
import com.samseptiano.fortius.utils.DateUtil
import com.samseptiano.base.usecase.BaseUseCase
import javax.inject.Inject

/**
 * @author SamuelSep on 4/20/2021.
 */
class UpdateAbsensionUseCase @Inject constructor(
    private val roomAbsensionDataSource: RoomAbsensionDataSource
) :
    BaseUseCase<Boolean, UpdateAbsensionUseCase.Params>() {

    class Params(val listAbsension: ArrayList<AbsensionModel>)

    override suspend fun run(params: Params): Boolean {
        val listAbsension = arrayListOf<RoomAbsensionModel>()

        params.listAbsension.map {
            listAbsension.add(
                RoomAbsensionModel.toRoomAbsensionModel(
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
                    photoPath = it.photoPath,
                    isFGActive = "Y",
                    createdDate = DateUtil.getCurrentDateTime(),
                    createdUser = it.email,
                    updateDate = DateUtil.getCurrentDateTime(),
                    updateUser = it.email

                )
            )
        }

        roomAbsensionDataSource.updateDataBatch(params.listAbsension as List<RoomAbsensionModel>)

        return true
    }

}
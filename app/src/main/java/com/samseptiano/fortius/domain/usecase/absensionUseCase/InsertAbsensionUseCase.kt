package com.samseptiano.fortius.domain.usecase.absensionUseCase

import android.util.Log
import com.samseptiano.fortius.data.model.response.AbsensionModel
import com.samseptiano.fortius.data.repository.datasource.RoomAbsensionDataSource
import com.samseptiano.fortius.data.roomModel.RoomAbsensionModel
import com.samseptiano.fortius.utils.DateUtil
import com.samseptiano.base.usecase.BaseUseCase
import java.time.Instant
import javax.inject.Inject

/**
 * @author SamuelSep on 4/20/2021.
 */
class InsertAbsensionUseCase @Inject constructor(
    private val roomAbsensionDataSource: RoomAbsensionDataSource
) : BaseUseCase<Boolean, InsertAbsensionUseCase.Params>() {

    class Params(
        val listAbsension: List<AbsensionModel>
    )

    override suspend fun run(params: Params): Boolean {

        try {
            val listAbsension = arrayListOf<RoomAbsensionModel>()

            params.listAbsension.map {
                val now = Instant.now().epochSecond;
                listAbsension.add(
                    RoomAbsensionModel.toRoomAbsensionModel(
                        id = now,
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
                        updateDate = "",
                        updateUser = ""
                    )
                )
            }
            roomAbsensionDataSource.insertDataBatch(listAbsension)
            return true
        } catch (e: Exception) {
            Log.e("error insert room", e.toString())
            return false
        }

    }

}
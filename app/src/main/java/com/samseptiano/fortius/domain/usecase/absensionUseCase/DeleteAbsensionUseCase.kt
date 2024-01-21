package com.samseptiano.fortius.domain.usecase.absensionUseCase

import com.samseptiano.fortius.data.repository.datasource.RoomAbsensionDataSource
import com.samseptiano.base.usecase.BaseUseCase
import javax.inject.Inject

/**
 * @author SamuelSep on 4/20/2021.
 */
class DeleteAbsensionUseCase @Inject constructor(
    private val roomAbsensionDataSource: RoomAbsensionDataSource,
) : BaseUseCase<Boolean, DeleteAbsensionUseCase.Params>() {

    class Params(val id: String?, val date: String?)

    override suspend fun run(params: Params): Boolean {
        return try {
            if (!params.id.isNullOrEmpty()) {
                roomAbsensionDataSource.deleteDataById(params.id.toLong())
            }

            if (!params.date.isNullOrEmpty()) {
                roomAbsensionDataSource.deleteDataByDate(params.date)
            }

            if (params.date.isNullOrEmpty() && params.id.isNullOrEmpty()) {
                roomAbsensionDataSource.deleteAllData()
            }
            true
        } catch (e: Exception) {
            false
        }
    }

}
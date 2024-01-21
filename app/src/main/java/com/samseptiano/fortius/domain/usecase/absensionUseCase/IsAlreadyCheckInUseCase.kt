package com.samseptiano.fortius.domain.usecase.absensionUseCase

import com.samseptiano.fortius.data.repository.datasource.RoomAbsensionDataSource
import com.samseptiano.base.usecase.BaseUseCase
import javax.inject.Inject

/**
 * @author SamuelSep on 4/20/2021.
 */
class IsAlreadyCheckInUseCase @Inject constructor(
    private val roomAbsensionDataSource: RoomAbsensionDataSource,
) : BaseUseCase<Boolean, IsAlreadyCheckInUseCase.Params>() {

    class Params(val date: String)

    override suspend fun run(params: Params): Boolean {
        return roomAbsensionDataSource.isAlreadyCheckin(params.date)
    }


}
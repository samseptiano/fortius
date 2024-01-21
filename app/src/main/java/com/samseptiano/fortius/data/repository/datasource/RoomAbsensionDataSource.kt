package com.samseptiano.fortius.data.repository.datasource

import com.samseptiano.fortius.data.roomModel.RoomAbsensionModel

/**
 * @author SamuelSep on 4/20/2021.
 */
interface RoomAbsensionDataSource {
    suspend fun insertData(roomAbsensionModel: RoomAbsensionModel) : Boolean
    suspend fun insertDataBatch(listRoomAbsensionModel: List<RoomAbsensionModel>) : Boolean
    suspend fun updateData(roomAbsensionModel: RoomAbsensionModel): Boolean
    suspend fun updateDataBatch(listRoomAbsensionModel: List<RoomAbsensionModel>) : Boolean
    suspend fun deleteDataById(id: Long) : Boolean
    suspend fun deleteDataByDate(date:String) : Boolean
    suspend fun deleteAllData() : Boolean
    suspend fun getAllData(): List<RoomAbsensionModel>
    suspend fun getAllDataByDate(date:String): List<RoomAbsensionModel>
    suspend fun getAllDataByDateAndStatus(date: String, status:String): RoomAbsensionModel?
    suspend fun getAllDataById(id: Long): RoomAbsensionModel
    suspend fun isAlreadyCheckin(date:String): Boolean


}
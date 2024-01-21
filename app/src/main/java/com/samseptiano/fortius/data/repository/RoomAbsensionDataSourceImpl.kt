package com.samseptiano.fortius.data.repository

import com.samseptiano.fortius.data.repository.datasource.RoomAbsensionDataSource
import com.samseptiano.fortius.data.roomModel.RoomAbsensionModel
import com.samseptiano.fortius.domain.dao.AbsensionDao
import javax.inject.Inject

/**
 * @author SamuelSep on 4/20/2021.
 */
class RoomAbsensionDataSourceImpl @Inject constructor(
    private val absensionDao: AbsensionDao,
) : RoomAbsensionDataSource {
    override suspend fun insertData(roomAbsensionModel: RoomAbsensionModel): Boolean {
        return try {
            absensionDao.insertData(roomAbsensionModel)
            true
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun insertDataBatch(listRoomAbsensionModel: List<RoomAbsensionModel>): Boolean {
        return try {
            absensionDao.insertDataBatch(listRoomAbsensionModel)
            true
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun updateData(roomAbsensionModel: RoomAbsensionModel): Boolean {
        return try {
            absensionDao.updateData(roomAbsensionModel)
            true
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun updateDataBatch(listRoomAbsensionModel: List<RoomAbsensionModel>): Boolean {
        return try {
            absensionDao.updateDataBatch(listRoomAbsensionModel)
            true
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun deleteDataById(id: Long): Boolean {
        return try {
            absensionDao.deleteDataById(id)
            true
        } catch (e: Exception) {
            false
        }

    }

    override suspend fun deleteDataByDate(date: String): Boolean {
        return try {
            absensionDao.deleteDataByDate(date)
            true
        } catch (e: Exception) {
            false
        }

    }

    override suspend fun deleteAllData(): Boolean {
        return try {
            absensionDao.deleteAllData()
            true
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun getAllData(): List<RoomAbsensionModel> {
        return try {
            absensionDao.getAllData()
        } catch (e: Exception) {
            arrayListOf()
        }

    }

    override suspend fun getAllDataByDate(date: String): List<RoomAbsensionModel> {
        return try {
            absensionDao.getAllDataByDate(date)
        } catch (e: Exception) {
            arrayListOf()
        }
    }

    override suspend fun getAllDataByDateAndStatus(date: String, status:String): RoomAbsensionModel? {
        return try {
            return absensionDao.getAllDataByDateAndStatus(date, status)
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun getAllDataById(id: Long): RoomAbsensionModel {
        return absensionDao.getAllDataById(id)
    }

    override suspend fun isAlreadyCheckin(date: String): Boolean {
        return try {
            val result = absensionDao.isAlreadyCheckin(date)
            result != null
        } catch (e: Exception) {
            false
        }
    }

}
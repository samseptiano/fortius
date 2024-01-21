package com.samseptiano.fortius.domain.dao

import androidx.room.*
import com.samseptiano.fortius.data.roomModel.*

@Dao
interface AbsensionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertData(roomAbsensionModel: RoomAbsensionModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDataBatch(listRoomAbsensionModel: List<RoomAbsensionModel>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateData(roomAbsensionModel: RoomAbsensionModel)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateDataBatch(listRoomAbsensionModel: List<RoomAbsensionModel>)

    @Query("DELETE FROM absension WHERE id = :id")
    fun deleteDataById(id: Long)

    @Query("DELETE FROM absension WHERE date = :date")
    fun deleteDataByDate(date: String)

    @Query("DELETE FROM absension")
    fun deleteAllData()

    @Query("SELECT * FROM absension order by id, date")
    fun getAllData(): List<RoomAbsensionModel>

    @Query("SELECT * FROM absension where id = :id ")
    fun getAllDataById(id: Long): RoomAbsensionModel

    @Query("SELECT * FROM absension where date = :date order by id, date")
    fun getAllDataByDate(date: String): List<RoomAbsensionModel>

    @Query("SELECT * FROM absension where date = :date and status = :status order by id desc, date LIMIT 1")
    fun getAllDataByDateAndStatus(date: String, status: String): RoomAbsensionModel?

    @Query("SELECT * FROM absension where date = :date order by id DESC LIMIT 1")
    fun isAlreadyCheckin(date: String): RoomAbsensionModel?
}

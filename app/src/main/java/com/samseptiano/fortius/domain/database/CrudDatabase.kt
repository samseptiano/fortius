package com.samseptiano.fortius.domain.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.samseptiano.fortius.data.roomModel.RoomAbsensionModel
import com.samseptiano.fortius.domain.dao.AbsensionDao


@Database(entities = [RoomAbsensionModel::class], version = 2,exportSchema = false)
abstract class CrudDatabase : RoomDatabase() {
    abstract fun getAbsensiDao(): AbsensionDao
}
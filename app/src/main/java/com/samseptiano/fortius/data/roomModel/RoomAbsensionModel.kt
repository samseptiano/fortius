package com.samseptiano.fortius.data.roomModel

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "absension")
data class RoomAbsensionModel(
    @PrimaryKey(autoGenerate = true)
    var Id: Long,
    var employeeId: String,
    var email: String,
    var name: String,
    var role: String,
    var companyId: String,
    var companyName: String,
    var date: String,
    var time: String,
    var status: String,
    var lat: Double = 0.0,
    var lon: Double = 0.0,
    var location: String = "",
    var photoPath: String = "",
    var isFGActive: String = "Y",
    var createdDate: String,
    var createdUser: String,
    var updateDate: String,
    var updateUser: String
    ) {
    companion object {
        fun toRoomAbsensionModel(
            id:Long,
            employeeId: String,
            email:String,
            name: String,
            role: String,
            companyId: String,
            companyName: String,
            date: String,
            time: String,
            status: String,
            lat: Double,
            lon: Double,
            location: String,
            photoPath: String,
            isFGActive: String,
            createdDate: String,
            createdUser: String,
            updateDate: String,
            updateUser: String
        ): RoomAbsensionModel {

            return RoomAbsensionModel(
                Id = id,
                employeeId = employeeId,
                email = email,
                name = name,
                role = role,
                companyId = companyId,
                companyName = companyName,
                date = date,
                time = time,
                status = status,
                lat = lat,
                lon = lon,
                location = location,
                photoPath = photoPath,
                isFGActive = isFGActive,
                createdDate = createdDate,
                createdUser = createdUser,
                updateDate = updateDate,
                updateUser = updateUser
            )
        }
    }
}
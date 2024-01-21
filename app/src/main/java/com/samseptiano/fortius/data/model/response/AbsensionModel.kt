package com.samseptiano.fortius.data.model.response

/**
 * Created by samuel.septiano on 17/01/2024.
 */

data class AbsensionModel(
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
    var location:String = "",
    var photoPath: String = "",
) {
    companion object {
        fun toAbsensionModel(
            id: Long,
            employeeId: String,
            email: String,
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
        ): AbsensionModel {

            return AbsensionModel(
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
                photoPath = photoPath
            )
        }
    }
}
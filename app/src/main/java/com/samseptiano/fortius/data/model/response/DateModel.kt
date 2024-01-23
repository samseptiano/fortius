package com.samseptiano.fortius.data.model.response

/**
 * Created by samuel.septiano on 17/01/2024.
 */

data class DateModel(
    var isHeader: Boolean,
    var dateNumber: Int,
    var dateDay: String,
    var dateMonth: Int,
    var dateMonthString: String,
    var dateYear: String
)
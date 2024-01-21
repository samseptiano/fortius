package com.samseptiano.fortius.utils

import android.app.DatePickerDialog
import android.content.Context
import com.samseptiano.fortius.R
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*


object DateUtil {

    const val DATE_FORMAT = "yyyy-MM-dd"

    fun getDatePicker(context: Context, callBack: (String) -> Unit) {
        val picker: DatePickerDialog
        val cldr: Calendar = Calendar.getInstance()
        val day: Int = cldr.get(Calendar.DAY_OF_MONTH)
        val month: Int = cldr.get(Calendar.MONTH)
        val year: Int = cldr.get(Calendar.YEAR)
        picker = DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->

                val currMonth = month + 1
                val formattedCurMonth = if (currMonth < 10) "0$currMonth" else currMonth
                val formattedCurDay = if (dayOfMonth < 10) "0$dayOfMonth" else dayOfMonth

                val stringDate = "$year-${formattedCurMonth}-$formattedCurDay"
                callBack(stringDate)
            }, year, month, day
        )
        picker.show()

    }

    fun Context.convertToDateMonthString(dateString: String, format: String): String {
        var result = "-"
        val monthArray = resources.getStringArray(R.array.month_english)
        if (format == DATE_FORMAT) {
            val year = dateString.split("-")[0]
            val month = dateString.split("-")[1]
            val date = dateString.split("-")[2]
            result = "$date-${monthArray[month.toInt() - 1].substring(0, 3)}-$year"
        }
        return result
    }

    fun getCurrentDateTime(): String {
        val sdf =
            SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        return sdf.format(Date())
    }

    fun getCurrentDateTimeStamp(): String {
        val sdf =
            SimpleDateFormat("yyyMMdd_HHmmss", Locale.getDefault())
        return sdf.format(Date())
    }

    fun getCurrentDate(): String {
        val sdf =
            SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return sdf.format(Date())
    }

    fun getCurrentTime(): String {
        val sdf =
            SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        return sdf.format(Date())
    }


    fun getCurrentDateFormatted(): String {
        val currentDate = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy", Locale.ENGLISH)
        return currentDate.format(formatter)
    }

    fun compareTwoDate(startDate: String, endDate: String): Boolean {
        val start: Date = SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH)
            .parse(startDate) as Date
        val end: Date = SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH)
            .parse(endDate) as Date
        return start <= end
    }

    fun isFirstTimeisSmallerThanSecondTime(startTime: String, endTime: String): Boolean {
        val start: Date = SimpleDateFormat("HH:mm:ss",Locale.getDefault())
            .parse(startTime) as Date
        val end: Date = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
            .parse(endTime) as Date
        return start < end
    }

    fun isFirstTimeisBiggerThanSecondTime(startTime: String, endTime: String): Boolean {
        val start: Date = SimpleDateFormat("HH:mm:ss",Locale.getDefault())
            .parse(startTime) as Date
        val end: Date = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
            .parse(endTime) as Date
        return start > end
    }
    fun calculateTimeDifference(start: String, end: String): String {

        val format = SimpleDateFormat("HH:mm:ss")
        val date1 = format.parse(start)
        val date2 = format.parse(end)
        val difference = date2.time - date1.time
        val seconds = difference / 1000
        val hours = seconds / 3600
        val minutes = (seconds % 3600) / 60
        val remainingSeconds = seconds % 60

        return "$hours hrs $minutes mins"

    }

    fun calculateDaysDifference(dateStart:String, dateEnd:String):String{
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val date1 = LocalDate.parse(dateStart, formatter)
        val date2 = LocalDate.parse(dateEnd, formatter)

        // Calculate the difference in days

        // Calculate the difference in days
        val daysDifference = ChronoUnit.DAYS.between(date1, date2)
        return "$daysDifference day(s)"
    }
}
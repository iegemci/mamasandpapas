package com.enesgemci.mamasandpapas.util

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by enesgemci on 05/10/2017.
 */
object DateUtils {

    var START_IS_AFTER_END = 1
    var START_IS_BEFORE_END = 2
    var START_IS_EQUAL_END = 3

    val currentDay: Int
        get() = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)

    val currentMonth: Int
        get() = Calendar.getInstance().get(Calendar.MONTH) + 1

    val currentYear: Int
        get() = Calendar.getInstance().get(Calendar.YEAR)

    val currentDate: String
        get() = currentDay.toString() + "." + currentMonth + "." + currentYear

    /**
     * Verilen gun kadar geriye veya ileriye gider

     * @param day
     * *
     * @return
     */
    fun getChangedDate(day: Int): String {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, day)

        return calendar.get(Calendar.DAY_OF_MONTH).toString() + "." + (calendar.get(Calendar.MONTH) + 1) + "." + calendar.get(Calendar.YEAR)
    }

    /**
     * Verilen yil kadar geriye veya ileriye gider

     * @param year
     * *
     * @return
     */
    fun getChangedCalender(year: Int): Calendar {
        val now = Calendar.getInstance()

        now.set(Calendar.YEAR, now.get(Calendar.YEAR) + year)

        return now
    }

    fun isSameDay(calendar1: Calendar, calendar2: Calendar): Boolean {
        return calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR) &&
                calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH) &&
                calendar1.get(Calendar.DAY_OF_MONTH) == calendar2.get(Calendar.DAY_OF_MONTH)
    }

    fun stringDateToCalendar(date: String): Calendar? {
        val cal = Calendar.getInstance()
        val sdf = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        try {
            cal.time = sdf.parse(date)
            return cal
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return null
    }

    fun calendarToFormattedDate(calendar: Calendar): String {
        val sdf = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        return sdf.format(calendar.time)
    }


    fun getDate(sDate: String): Date? {
        val sdf = SimpleDateFormat("dd.MM.yyyy")
        var d: Date? = null

        try {
            d = sdf.parse(sDate)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return d
    }

    fun compareDate(startDate: String, endDate: String): Int {
        try {
            val sdf = SimpleDateFormat("dd.MM.yyyy")
            val date1 = sdf.parse(startDate)
            val date2 = sdf.parse(endDate)

            if (date1.compareTo(date2) > 0) {
                return START_IS_AFTER_END
            } else if (date1.compareTo(date2) < 0) {
                return START_IS_BEFORE_END
            } else if (date1.compareTo(date2) == 0) {
                return START_IS_EQUAL_END
            }
        } catch (e: Exception) {
        }

        return 0
    }

    val oneDayOfCurrentMonth: String
        get() {
            val oneOfMonth = currentDate
            val s = oneOfMonth.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            return "01." + s[1] + "." + s[2]
        }

    fun subDate(startDate: String, endDate: String): Int {
        val sdf = SimpleDateFormat("dd.MM.yyyy")
        var date1: Date?
        var date2: Date?
        var diffMonth = 0

        try {
            date2 = sdf.parse(endDate)
            date1 = sdf.parse(startDate)

            val diff = Math.abs(date2!!.time - date1!!.time)
            diffMonth = (diff / (24 * 60 * 60 * 1000)).toInt()

        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return diffMonth / 30
    }

    fun isInPeriod(period: Int, startDate: String, endDate: String): Boolean {
        val sdf = SimpleDateFormat("dd.MM.yyyy")
        var date1: Date?

        try {
            date1 = sdf.parse(startDate)

            val startDateCal = Calendar.getInstance()
            startDateCal.time = date1
            startDateCal.add(Calendar.MONTH, period)

            val requiredEndDate = startDateCal.get(Calendar.DAY_OF_MONTH).toString() + "." + (startDateCal.get(Calendar.MONTH) + 1) + "." + startDateCal.get(Calendar.YEAR)

            val result = compareDate(endDate, requiredEndDate)
            if (result == START_IS_AFTER_END) {
                return false
            }
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return true
    }

    /**
     * @param startDate
     * *
     * @param endDate
     * *
     * @return
     */
    fun isGivenDateInSameMonth(startDate: String, endDate: String): Boolean {
        try {
            val sdf = SimpleDateFormat("dd.MM.yyyy")
            val date1 = sdf.parse(startDate)
            val date2 = sdf.parse(endDate)

            val cal = Calendar.getInstance()
            cal.time = date1
            val year1 = cal.get(Calendar.YEAR)
            val month1 = cal.get(Calendar.MONTH)

            cal.time = date2
            val year2 = cal.get(Calendar.YEAR)
            val month2 = cal.get(Calendar.MONTH)

            if (year1 == year2 && month1 == month2) {
                return true
            }

        } catch (e: Exception) {
        }

        return false
    }

    val currentTime: String
        get() {
            val d = Date()
            val sdf = SimpleDateFormat("hh:mm a")
            val currentDateTimeString = sdf.format(d)
            return currentDateTimeString
        }


    fun getParsedDate(date: String): String {
        if (date.contains(" ")) {
            val list = date.split("\\s+".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            return list[0]
        } else {
            return ""
        }
    }

    fun getParsedTime(date: String): String {
        if (date.contains(" ")) {
            val list = date.split("\\s+".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            return list[1]
        } else {
            return ""
        }
    }

    val currentDateName: String
        get() = SimpleDateFormat("MMMM yyyy", Locale.getDefault()).format(Calendar.getInstance().time).toString()

    fun getMonth(month: Int): String {
        when (month) {
            1 -> return "January"
            2 -> return "February"
            3 -> return "March"
            4 -> return "April"
            5 -> return "May"
            6 -> return "June"
            7 -> return "July"
            8 -> return "August"
            9 -> return "September"
            10 -> return "October"
            11 -> return "November"
            12 -> return "December"
        }

        return ""
    }

//    fun parseIso8601Date(date: String): DateTime {
//        return DateTime(date, DateTimeZone.UTC)
//    }
//
//    fun parseIso8601Date(date: DateTime?): DateTime {
//        return date?.let { DateTime(date, DateTimeZone.UTC) } ?: DateTime()
//    }

    /**
     * Parametre ile gönderilen yıl ay gün bilgilerine göre formatlanmış tarihi dönderir

     * @param year
     * *
     * @param month
     * *
     * @param day
     * *
     * @return
     */
    fun getFormattedDate(year: Int, month: Int, day: Int): String {
        var d = day.toString() + ""
        var m = month.toString() + ""

        if (day < 10) {
            d = "0" + d
        }
        if (month < 10) {
            m = "0" + m
        }

        return "$m/$d/$year"
    }
}
package com.enesgemci.mamasandpapas.core.util.extensions

import com.enesgemci.mamasandpapas.util.DateUtils
import org.joda.time.DateTime

/**
 * Created by enesgemci on 04/10/2017.
 */
fun DateTime?.getFormattedDate(systemDate: DateTime? = null): String {
    this?.let {
        val day = this.dayOfMonth
        val month = this.monthOfYear
        val year = this.year

        return DateUtils.getFormattedDate(year, month, day)
    } ?: return ""
}

fun DateTime?.getFormattedDateTime(): String {
    return this?.let { this.toDateTimeISO().toString().getFormattedDateTime() } ?: ""
}
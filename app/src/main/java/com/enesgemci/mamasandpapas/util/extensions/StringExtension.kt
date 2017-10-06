package com.enesgemci.mamasandpapas.core.util.extensions

import android.text.SpannableString
import android.text.TextUtils
import android.text.style.UnderlineSpan
import org.joda.time.DateTime
import org.joda.time.DateTimeZone

/**
 * Created by enesgemci on 02/10/2017.
 */

/**
 * return given text's multilanguage value
 */
fun String?.getIsoFormattedDateTime(): DateTime? {
    try {
        this?.let {
            if (!TextUtils.isEmpty(this)) {
                val sp = this.split("/".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

                if (sp.size == 3) {
                    val year = sp[2].trim { it <= ' ' }.replace(" ", "")
                    var month = sp[0].trim { it <= ' ' }.replace(" ", "")
                    var day = sp[1].trim { it <= ' ' }.replace(" ", "")

                    month = if (month.length == 1) "0" + month else month
                    day = if (day.length == 1) "0" + day else day

                    return "$year-$month-$day".parseIso8601Date()
                }

                return DateTime(DateTimeZone.UTC)
            }
        }

        return DateTime(DateTimeZone.UTC)
    } catch (e: Exception) {
        return null
    }
}

fun String?.parseIso8601Date(): DateTime {
    return this?.let { DateTime(this, DateTimeZone.UTC) } ?: DateTime(DateTimeZone.UTC)
}

fun String?.getFormattedDate(now: DateTime? = null): String {
    this?.let {
        return this.parseIso8601Date().getFormattedDate(now)
    } ?: return ""
}

fun String?.getFormattedDateTime(): String {
    this?.let {
        val dateTime = this.parseIso8601Date()
        val date = dateTime.getFormattedDate()

        return date + " "
                .plus(if (dateTime.hourOfDay < 10) "0" + dateTime.hourOfDay else dateTime.hourOfDay)
                .plus(":")
                .plus(if (dateTime.minuteOfHour < 10) "0" + dateTime.minuteOfHour else dateTime.minuteOfHour)
    } ?: return ""
}

fun String.toDate(): DateTime? {
    if (!TextUtils.isEmpty(this)) {
        val spDate = this.split("/".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

        if (spDate.size == 3) {
            return DateTime(Integer.parseInt(spDate[2]), Integer.parseInt(spDate[0]), Integer.parseInt(spDate[1]), 0, 0)
        }

        return null
    }

    return null
}

fun String.underline(): SpannableString {
    val content = SpannableString(this)
    content.setSpan(UnderlineSpan(), 0, content.length, 0)

    return content
}

fun CharSequence.underline(): SpannableString {
    val content = SpannableString(this)
    content.setSpan(UnderlineSpan(), 0, content.length, 0)

    return content
}

fun String.toMoney(maxFraction: Int = 2): String {
    return this.replace(",", "").toDouble().toMoney(maxFraction)
}
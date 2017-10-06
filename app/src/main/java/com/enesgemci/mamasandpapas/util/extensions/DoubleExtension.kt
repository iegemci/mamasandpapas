package com.enesgemci.mamasandpapas.core.util.extensions

import java.text.NumberFormat
import java.util.*

/**
 * Created by enesgemci on 04/10/2017.
 */
fun Double.toMoney(maxFraction: Int = 2, locale: Locale = Locale.US): String {
    val formatter = NumberFormat.getInstance(locale)
    formatter.minimumFractionDigits = 2
    formatter.maximumFractionDigits = maxFraction

    return formatter.format(this)
}
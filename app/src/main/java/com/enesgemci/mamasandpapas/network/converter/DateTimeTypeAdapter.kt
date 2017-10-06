/*
 * Copyright (c) 2017.
 *
 *     "Therefore those skilled at the unorthodox
 *      are infinite as heaven and earth,
 *      inexhaustible as the great rivers.
 *      When they come to an end,
 *      they begin again,
 *      like the days and months;
 *      they die and are reborn,
 *      like the four seasons."
 *
 * - Sun Tsu, "The Art of War"
 *
 * Enes Gemci
 */

package com.enesgemci.mamasandpapas.network.converter

import android.support.annotation.Keep
import com.enesgemci.mamasandpapas.core.util.extensions.parseIso8601Date
import com.enesgemci.mamasandpapas.network.converter.base.MTypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import org.joda.time.DateTime
import java.io.IOException

/**
 * Created by enesgemci on 24/11/2016.
 */
@Keep
class DateTimeTypeAdapter : MTypeAdapter<DateTime?>() {

    @Throws(IOException::class)
    override fun read(reader: JsonReader): DateTime? {
        if (reader.peek() == JsonToken.NULL) {
            reader.nextNull()
            return null
        }

        val date = reader.nextString()
        return date.parseIso8601Date()
    }
}
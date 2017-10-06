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

package com.enesgemci.mamasandpapas.network.converter.base

import android.support.annotation.Keep

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter

import java.io.IOException

/**
 * Created by enesgemci on 05/12/2016.
 */
@Keep
abstract class MTypeAdapter<T> : TypeAdapter<T>() {

    @Keep
    @Throws(IOException::class)
    override fun write(writer: JsonWriter, value: T?) {
        if (value == null) {
            writer.nullValue()
            return
        }

        writer.value(value.toString())
    }

    @Keep
    @Throws(IOException::class)
    abstract override fun read(reader: JsonReader): T
}
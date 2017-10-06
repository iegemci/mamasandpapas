/*
 * Copyright (c) 2017.
 *
 *      "Therefore those skilled at the unorthodox
 *        are infinite as heaven and earth,
 *        inexhaustible as the great rivers.
 *        When they come to an end,
 *        they begin again,
 *        like the days and months;
 *        they die and are reborn,
 *        like the four seasons."
 *
 *     - Sun Tsu, "The Art of War"
 *
 * Enes Gemci
 */

package com.enesgemci.mamasandpapas.network

import com.google.gson.ExclusionStrategy
import com.google.gson.FieldAttributes


/**
 * Created by enesgemci on 13/06/2017.
 */
open class AnnotationExclusionStrategy : ExclusionStrategy {

    override fun shouldSkipField(f: FieldAttributes): Boolean {
        return f.getAnnotation(Exclude::class.java) != null
    }

    override fun shouldSkipClass(clazz: Class<*>): Boolean {
        return false
    }
}
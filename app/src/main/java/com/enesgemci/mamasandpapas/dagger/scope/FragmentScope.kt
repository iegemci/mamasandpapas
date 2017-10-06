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

package com.enesgemci.mamasandpapas.dagger.scope

import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

import javax.inject.Scope

/**
 * Created by enesgemci on 11/05/2017.
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
annotation class FragmentScope
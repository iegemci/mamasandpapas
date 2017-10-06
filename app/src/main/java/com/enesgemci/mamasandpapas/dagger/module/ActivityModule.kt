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

package com.enesgemci.mamasandpapas.dagger.module

import android.content.Context
import com.enesgemci.mamasandpapas.base.BaseActivity
import com.enesgemci.mamasandpapas.dagger.qualifier.ActivityContext
import dagger.Module
import dagger.Provides

/**
 * Created by enesgemci on 11/05/2017.
 */
@Module
internal class ActivityModule(private val baseActivity: BaseActivity<*, *>) {

    @Provides
    @ActivityContext
    internal fun provideActivityContext(): Context {
        return baseActivity
    }

    @Provides
    internal fun provideActivity(): BaseActivity<*, *> {
        return baseActivity
    }
}
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
import android.support.v4.app.FragmentManager
import com.enesgemci.mamasandpapas.base.BaseFragment
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by enesgemci on 11/05/2017.
 */
@Module
internal class FragmentModule(private val fragment: BaseFragment<*, *>, private val childFragmentManager: FragmentManager) {

    private val context: Context = fragment.getContext()

    @Singleton
    @Provides
    internal fun provideFragment(): BaseFragment<*, *> {
        return fragment
    }
}
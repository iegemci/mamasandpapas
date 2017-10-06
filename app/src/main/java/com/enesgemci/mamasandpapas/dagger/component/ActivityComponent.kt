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

package com.enesgemci.mamasandpapas.dagger.component

import com.enesgemci.mamasandpapas.dagger.module.ActivityModule
import com.enesgemci.mamasandpapas.dagger.scope.ActivityScope
import com.enesgemci.mamasandpapas.network.MResponseHandler
import com.enesgemci.mamasandpapas.ui.MainActivity
import dagger.Component

/**
 * Created by enesgemci on 11/05/2017.
 */
@ActivityScope
@Component(dependencies = arrayOf(AppComponent::class), modules = arrayOf(ActivityModule::class))
internal interface ActivityComponent {

    fun inject(responseHandler: MResponseHandler)

    fun inject(mainActivity: MainActivity)
}
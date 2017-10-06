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

import com.enesgemci.mamasandpapas.dagger.module.FragmentModule
import com.enesgemci.mamasandpapas.dagger.scope.FragmentScope
import com.enesgemci.mamasandpapas.ui.detail.FragmentProductDetail
import com.enesgemci.mamasandpapas.ui.list.FragmentProductList
import dagger.Component

/**
 * Created by enesgemci on 11/05/2017.
 */
@FragmentScope
@Component(dependencies = arrayOf(AppComponent::class), modules = arrayOf(FragmentModule::class))
internal interface FragmentComponent {

    fun inject(fragmentProductList: FragmentProductList)

    fun inject(fragmentProductDetail: FragmentProductDetail)
}
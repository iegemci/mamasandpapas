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

package com.enesgemci.mamasandpapas.widget.indicator

import android.support.v4.view.ViewPager

interface PageIndicator : ViewPager.OnPageChangeListener {
    /**
     * bind ViewPager
     */
    fun setViewPager(vp: ViewPager)

    /**
     * for special viewpager,such as LooperViewPager
     */
    fun setViewPager(vp: ViewPager, realCount: Int)

    fun setCurrentItem(item: Int)
}
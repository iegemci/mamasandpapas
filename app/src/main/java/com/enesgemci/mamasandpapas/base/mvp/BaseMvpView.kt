/*
 * Copyright (c) 2016.
 *
 * Enes Gemci
 */

package com.enesgemci.mamasandpapas.base.mvp

import com.enesgemci.mamasandpapas.network.MRequest
import com.enesgemci.mamasandpapas.util.fragment.FragmentBuilder
import com.enesgemci.mamasandpapas.util.fragment.Page
import com.hannesdorfmann.mosby3.mvp.MvpView

/**
 * Created by enesgemci on 29/11/2016.
 */

internal interface BaseMvpView : MvpView {

    fun sendRequest(request: MRequest<*>)

    fun showPage(page: Page, vararg obj: Any?)

    fun showPage(replacer: FragmentBuilder?)

    fun getPage(page: Page, vararg obj: Any?): FragmentBuilder

    fun isTablet(): Boolean
}
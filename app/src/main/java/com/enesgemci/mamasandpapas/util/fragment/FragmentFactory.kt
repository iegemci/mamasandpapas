/*
 * Copyright (c) 2016.
 *
 * Enes Gemci
 */

package com.enesgemci.mamasandpapas.util.fragment

import com.enesgemci.mamasandpapas.ui.list.FragmentProductList

/**
 * Created by enesgemci on 10/09/15.
 */
internal object FragmentFactory {

    fun getFragment(page: Page, vararg obj: Any?): FragmentBuilder? = when (page) {
        Page.PAGE_PRODUCT_LIST ->
            FragmentBuilder().setFragment(FragmentProductList.newInstance(*obj))
                    .setTransactionAnimation(TransactionAnimation.NO_ANIM)
        else -> {
            null
        }
    }
}
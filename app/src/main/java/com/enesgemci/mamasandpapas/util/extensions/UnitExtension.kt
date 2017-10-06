package com.enesgemci.mamasandpapas.core.util.extensions

import com.enesgemci.mamasandpapas.util.UIUtils

/**
 * Created by enesgemci on 03/10/2017.
 */
fun Unit.delayed(delatTime: Long) {
    UIUtils.postDelayed(Runnable { this }, delatTime)
}
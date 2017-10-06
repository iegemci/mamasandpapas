package com.enesgemci.mamasandpapas.core.util.extensions

import com.enesgemci.mamasandpapas.util.UIUtils

/**
 * Created by enesgemci on 03/10/2017.
 */
fun Runnable.delayed(delay: Long) {
    UIUtils.postDelayed(this, delay)
}

fun Runnable.post() {
    UIUtils.post(this)
}

fun Runnable.removeCallbacks() {
    UIUtils.removeCallbacks(this)
}
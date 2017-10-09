package com.enesgemci.mamasandpapas.util.extensions

import android.support.annotation.Keep
import android.support.design.widget.FloatingActionButton
import com.enesgemci.mamasandpapas.core.util.extensions.setColorFilter

/**
 * Created by enesgemci on 04/10/2017.
 */
@Keep
inline fun FloatingActionButton.setMColorFilter(color: Int) {
    this.drawable.setColorFilter(color)
}

fun FloatingActionButton.clearMColorFilter() {
    this.drawable.clearColorFilter()
}
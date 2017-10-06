package com.enesgemci.mamasandpapas.core.util.extensions

import android.support.annotation.Keep
import android.widget.ImageView

/**
 * Created by enesgemci on 04/10/2017.
 */
@Keep
inline fun ImageView.setMColorFilter(color: Int) {
    this.drawable.setColorFilter(color)
}

fun ImageView.clearMColorFilter() {
    this.drawable.clearColorFilter()
}
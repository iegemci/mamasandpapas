package com.enesgemci.mamasandpapas.core.util.extensions

import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.support.annotation.Keep

/**
 * Created by enesgemci on 04/10/2017.
 */
@Keep
inline fun Drawable.setColorFilter(color: Int) {
    this.mutate().setColorFilter(color, PorterDuff.Mode.SRC_IN)
}

fun Drawable.clearColorFilter() {
    this.mutate().colorFilter = null
}
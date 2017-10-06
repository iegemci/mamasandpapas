package com.enesgemci.mamasandpapas.core.util.extensions

import android.content.Context
import android.util.DisplayMetrics
import android.util.TypedValue

/**
 * Created by enesgemci on 04/10/2017.
 */
fun Float.dpToPx(context: Context): Float {
    val displayMetrics = context.resources.displayMetrics
    val px = this * (displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT)
    return px
}

fun Float.pxToDp(context: Context): Float {
    return this * context.resources.displayMetrics.density
}

fun Float.pxToSp(context: Context): Float {
    return this * context.resources.displayMetrics.scaledDensity
}

fun Float.spToPx(context: Context): Float {
    //        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
    //        int px = Math.round(sp * (displayMetrics.scaledDensity / DisplayMetrics.DENSITY_DEFAULT));
    //        return px;
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, this, context.resources.displayMetrics)
}
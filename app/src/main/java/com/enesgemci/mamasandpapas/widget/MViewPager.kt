package com.enesgemci.mamasandpapas.widget

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet

/**
 * Created by enesgemci on 09/10/2017.
 */
class MViewPager : ViewPager {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

//    override fun onMeasure(wMeasureSpec: Int, hMeasureSpec: Int) {
//        var heightMeasureSpec: Int
//        var height = 0
//
//        for (i in 0 until childCount) {
//            val child = getChildAt(i)
//            child.measure(wMeasureSpec, View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED))
//            val h = child.measuredHeight
//            if (h > height) height = h
//        }
//
//        heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY)
//
//        super.onMeasure(wMeasureSpec, heightMeasureSpec)
//    }
}
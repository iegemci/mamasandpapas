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

import android.annotation.TargetApi
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.View
import com.enesgemci.mamasandpapas.R
import java.util.*

/**
 * A pratice demo use GradientDrawable to realize the effect of JakeWharton's CirclePageIndicator
 */
class RoundCornerIndicator : View, PageIndicator {

    private var vp: ViewPager? = null
    private val unselectDrawables = ArrayList<GradientDrawable>()
    private val unselectRects = ArrayList<Rect>()
    private val selectDrawable = GradientDrawable()
    private val selectRect = Rect()
    var count: Int = 0
        private set
    private var currentItem: Int = 0
    private var positionOffset: Float = 0.toFloat()
    private var indicatorWidth: Int = 0
    private var indicatorHeight: Int = 0
    private var indicatorGap: Int = 0
    private var cornerRadius: Int = 0
    private var selectColor: Int = 0
    private var unselectColor: Int = 0
    private var strokeWidth: Int = 0
    private var strokeColor: Int = 0
    var isSnap: Boolean = false

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        init(context, attrs)
    }

    fun init(context: Context, attrs: AttributeSet?) {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.RoundCornerIndicator)
        indicatorWidth = ta.getDimensionPixelSize(R.styleable.RoundCornerIndicator_rci_width, dp2px(10f))
        indicatorHeight = ta.getDimensionPixelSize(R.styleable.RoundCornerIndicator_rci_height, dp2px(10f))
        indicatorGap = ta.getDimensionPixelSize(R.styleable.RoundCornerIndicator_rci_gap, dp2px(6f))
        cornerRadius = ta.getDimensionPixelSize(R.styleable.RoundCornerIndicator_rci_cornerRadius, dp2px(5f))
        strokeWidth = ta.getDimensionPixelSize(R.styleable.RoundCornerIndicator_rci_strokeWidth, dp2px(0.5f))
        selectColor = ta.getColor(R.styleable.RoundCornerIndicator_rci_selectColor, Color.parseColor("#ffffff"))
        unselectColor = ta.getColor(R.styleable.RoundCornerIndicator_rci_unselectColor, Color.parseColor("#88ffffff"))
        strokeColor = ta.getColor(R.styleable.RoundCornerIndicator_rci_strokeColor, Color.parseColor("#ffffff"))
        isSnap = ta.getBoolean(R.styleable.RoundCornerIndicator_rci_isSnap, false)

        ta.recycle()
    }

    override fun setViewPager(vp: ViewPager) {
        if (isValid(vp)) {
            this.vp = vp
            this.count = vp.adapter.count
            vp.removeOnPageChangeListener(this)
            vp.addOnPageChangeListener(this)

            unselectDrawables.clear()
            unselectRects.clear()
            for (i in 0 until count) {
                unselectDrawables.add(GradientDrawable())
                unselectRects.add(Rect())
            }

            invalidate()
        }
    }

    override fun setViewPager(vp: ViewPager, realCount: Int) {
        if (isValid(vp)) {
            this.vp = vp
            this.count = realCount
            vp.removeOnPageChangeListener(this)
            vp.addOnPageChangeListener(this)

            unselectDrawables.clear()
            unselectRects.clear()

            for (i in 0 until count) {
                unselectDrawables.add(GradientDrawable())
                unselectRects.add(Rect())
            }

            invalidate()
        }
    }

    override fun setCurrentItem(item: Int) {
        if (isValid(vp)) {
            vp!!.currentItem = item
        }
    }

    fun setIndicatorWidth(indicatorWidth: Int) {
        this.indicatorWidth = indicatorWidth
        invalidate()
    }

    fun setIndicatorHeight(indicatorHeight: Int) {
        this.indicatorHeight = indicatorHeight
        invalidate()
    }

    fun setIndicatorGap(indicatorGap: Int) {
        this.indicatorGap = indicatorGap
        invalidate()
    }

    fun setCornerRadius(cornerRadius: Int) {
        this.cornerRadius = cornerRadius
        invalidate()
    }

    fun setSelectColor(selectColor: Int) {
        this.selectColor = selectColor
        invalidate()
    }

    fun setUnselectColor(unselectColor: Int) {
        this.unselectColor = unselectColor
        invalidate()
    }

    fun setStrokeWidth(strokeWidth: Int) {
        this.strokeWidth = strokeWidth
        invalidate()
    }

    fun setStrokeColor(strokeColor: Int) {
        this.strokeColor = strokeColor
        invalidate()
    }

    fun getCurrentItem(): Int {
        return currentItem
    }

    fun getIndicatorWidth(): Int {
        return indicatorWidth
    }

    fun getIndicatorHeight(): Int {
        return indicatorHeight
    }

    fun getIndicatorGap(): Int {
        return indicatorGap
    }

    fun getCornerRadius(): Int {
        return cornerRadius
    }

    fun getSelectColor(): Int {
        return selectColor
    }

    fun getUnselectColor(): Int {
        return unselectColor
    }

    fun getStrokeWidth(): Int {
        return strokeWidth
    }

    fun getStrokeColor(): Int {
        return strokeColor
    }


    private fun isValid(vp: ViewPager?): Boolean {
        if (vp == null) {
            throw IllegalStateException("ViewPager can not be NULL!")
        }

        if (vp.adapter == null) {
            throw IllegalStateException("ViewPager adapter can not be NULL!")
        }

        return true
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

        if (!isSnap) {
            currentItem = position
            this.positionOffset = positionOffset
            invalidate()
        }
    }

    override fun onPageSelected(position: Int) {
        if (isSnap) {
            currentItem = position
            invalidate()
        }
    }

    override fun onPageScrollStateChanged(state: Int) {}

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (count <= 0) {
            return
        }

        val verticalOffset = paddingTop + (height - paddingTop - paddingBottom) / 2 - indicatorHeight / 2
        val indicatorLayoutWidth = indicatorWidth * count + indicatorGap * (count - 1)
        val horizontalOffset = paddingLeft + (width - paddingLeft - paddingRight) / 2 - indicatorLayoutWidth / 2

        drawUnselect(canvas, count, verticalOffset, horizontalOffset)
        drawSelect(canvas, verticalOffset, horizontalOffset)
    }

    private fun drawUnselect(canvas: Canvas, count: Int, verticalOffset: Int, horizontalOffset: Int) {
        for (i in 0 until count) {
            val rect = unselectRects[i]
            rect.left = horizontalOffset + (indicatorWidth + indicatorGap) * i
            rect.top = verticalOffset
            rect.right = rect.left + indicatorWidth
            rect.bottom = rect.top + indicatorHeight

            val unselectDrawable = unselectDrawables[i]
            unselectDrawable.cornerRadius = cornerRadius.toFloat()
            unselectDrawable.setColor(unselectColor)
            unselectDrawable.setStroke(strokeWidth, strokeColor)
            unselectDrawable.bounds = rect
            unselectDrawable.draw(canvas)
        }
    }

    private fun drawSelect(canvas: Canvas, verticalOffset: Int, horizontalOffset: Int) {
        val delta = ((indicatorGap + indicatorWidth) * if (isSnap) 0 else positionOffset.toInt())

        selectRect.left = horizontalOffset + (indicatorWidth + indicatorGap) * currentItem + delta
        selectRect.top = verticalOffset
        selectRect.right = selectRect.left + indicatorWidth
        selectRect.bottom = selectRect.top + indicatorHeight

        selectDrawable.cornerRadius = cornerRadius.toFloat()
        selectDrawable.setColor(selectColor)
        selectDrawable.bounds = selectRect
        selectDrawable.draw(canvas)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec))
    }

    private fun measureWidth(widthMeasureSpec: Int): Int {
        var result: Int
        val specMode = View.MeasureSpec.getMode(widthMeasureSpec)
        val specSize = View.MeasureSpec.getSize(widthMeasureSpec)
        if (specMode == View.MeasureSpec.EXACTLY || count == 0) {//大小确定,直接使用
            result = specSize
        } else {
            val padding = paddingLeft + paddingRight
            result = padding + indicatorWidth * count + indicatorGap * (count - 1)

            if (specMode == View.MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize)
            }
        }

        return result
    }

    private fun measureHeight(heightMeasureSpec: Int): Int {
        var result: Int
        val specMode = View.MeasureSpec.getMode(heightMeasureSpec)
        val specSize = View.MeasureSpec.getSize(heightMeasureSpec)
        if (specMode == View.MeasureSpec.EXACTLY) {
            result = specSize
        } else {
            val padding = paddingTop + paddingBottom
            result = padding + indicatorHeight

            if (specMode == View.MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize)
            }
        }

        return result
    }

    override fun onSaveInstanceState(): Parcelable? {
        val bundle = Bundle()
        bundle.putParcelable("instanceState", super.onSaveInstanceState())
        bundle.putInt("currentItem", currentItem)
        return bundle
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        var state = state
        if (state is Bundle) {
            val bundle = state as Bundle?
            currentItem = bundle!!.getInt("currentItem")
            state = bundle.getParcelable("instanceState")
        }
        super.onRestoreInstanceState(state)
    }

    private fun dp2px(dp: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dp * scale + 0.5f).toInt()
    }
}
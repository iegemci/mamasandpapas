package com.enesgemci.mamasandpapas.widget

import android.content.Context
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.view.animation.FastOutLinearInInterpolator
import android.support.v4.view.animation.FastOutSlowInInterpolator
import android.support.v4.view.animation.LinearOutSlowInInterpolator
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ImageView
import com.enesgemci.mamasandpapas.R
import com.enesgemci.mamasandpapas.core.util.extensions.bindView
import com.enesgemci.mamasandpapas.core.util.extensions.delayed
import com.enesgemci.mamasandpapas.core.util.extensions.removeCallbacks
import com.enesgemci.mamasandpapas.core.util.extensions.setMColorFilter

/**
 * Created by enesgemci on 05/10/2017.
 */
class MLoadingView : FrameLayout {

    private val mIconIv: ImageView by bindView(R.id.loading_ivIcon)
    private val mOuterCircleIv: ImageView by bindView(R.id.loading_ivOuterCircle)
    private val mThinCircleIv: ImageView by bindView(R.id.loading_ivThinCircle)
    private val mThinCircleSecondIv: ImageView by bindView(R.id.loading_ivThinCircleSecond)

    internal var indicatorColor: Int = 0
        private set

    private var resetted: Boolean = false

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        layoutInflater.inflate(R.layout.layout_loading_view, this, true)

        if (attrs != null) {
            val typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.MLoadingView)

            try {
                val color = typedArray.getResourceId(R.styleable.MLoadingView_loading_color, R.color.white)
                val iconVisible = typedArray.getBoolean(R.styleable.MLoadingView_icon_visible, true)

                mIconIv.visibility = if (iconVisible) VISIBLE else INVISIBLE

                setIndicatorColorResource(color)
            } finally {
                typedArray.recycle()
            }
        }

        startAnims()
    }

    fun startAnims() {
        resetted = false

        startIconScaleAnim()
        startOuterCircleAnimation()
        startThinCircleAnimation()
        runnableThinSecondCircleAnimation.delayed(300)
    }

    private fun startIconScaleAnim() {
        try {
            if (!resetted && mIconIv != null) {
                mIconIv.animate()
                        .setInterpolator(LinearOutSlowInInterpolator())
                        .scaleX(0.6f)
                        .setDuration(800)
                        .scaleY(0.6f)
                        .withLayer()
                        .withEndAction {
                            if (!resetted) {
                                startIconReverseScaleAnim()
                            } else {
                                reset()
                            }
                        }.start()
            } else {
                reset()
            }
        } catch (e: Exception) {
            // Animation exception, just ignore it
        }

    }

    private fun startIconReverseScaleAnim() {
        try {
            if (!resetted && mIconIv != null) {
                mIconIv.animate()
                        .setInterpolator(FastOutSlowInInterpolator())
                        .scaleX(0.8f)
                        .scaleY(0.8f)
                        .setDuration(1200)
                        .withLayer()
                        .withEndAction {
                            if (!resetted) {
                                startIconScaleAnim()
                            } else {
                                reset()
                            }
                        }.start()
            } else {
                reset()
            }
        } catch (e: Exception) {
            // Animation exception, just ignore it
        }

    }

    private fun startOuterCircleAnimation() {
        try {
            if (!resetted && mOuterCircleIv != null) {
                mOuterCircleIv.animate()
                        .setInterpolator(FastOutLinearInInterpolator())
                        .rotation(180f)
                        .scaleX(0.6f)
                        .scaleY(0.6f)
                        .setDuration(800)
                        .withLayer()
                        .withEndAction {
                            if (!resetted) {
                                startOuterCircleReverseAnimation()
                                startThinCircleAnimation()
                                runnableThinSecondCircleAnimation.delayed(300)
                            } else {
                                reset()
                            }
                        }.start()
            } else {
                reset()
            }
        } catch (e: Exception) {
            // Animation exception, just ignore it
        }
    }

    private val runnableThinSecondCircleAnimation = Runnable { startThinSecondCircleAnimation() }

    private fun startOuterCircleReverseAnimation() {
        try {
            if (!resetted && mOuterCircleIv != null) {
                mOuterCircleIv.animate()
                        .setInterpolator(FastOutSlowInInterpolator())
                        .rotation(0f)
                        .scaleX(0.8f)
                        .scaleY(0.8f)
                        .setDuration(1200)
                        .withLayer()
                        .withEndAction {
                            if (!resetted) {
                                startOuterCircleAnimation()
                            } else {
                                reset()
                            }
                        }.start()
            } else {
                reset()
            }
        } catch (e: Exception) {
            // Animation exception, just ignore it
        }

    }

    private fun startThinCircleAnimation() {
        try {
            if (!resetted && mThinCircleIv != null) {
                mThinCircleIv.animate()
                        .setInterpolator(FastOutSlowInInterpolator())
                        .scaleX(0.8f)
                        .scaleY(0.8f)
                        .setDuration(1000)
                        .withLayer()
                        .withEndAction {
                            if (!resetted && mThinCircleIv != null) {
                                mThinCircleIv.animate()
                                        .alpha(0f)
                                        .withLayer()
                                        .setDuration(200)
                                        .withEndAction {
                                            if (!resetted && mThinCircleIv != null) {
                                                mThinCircleIv.scaleX = 0f
                                                mThinCircleIv.scaleY = 0f
                                                mThinCircleIv.alpha = 1f

                                                startThinCircleAnimation()
                                            }
                                        }
                            } else {
                                reset()
                            }
                        }.start()
            } else {
                reset()
            }
        } catch (e: Exception) {
            // Animation exception, just ignore it
        }

    }

    private fun startThinSecondCircleAnimation() {
        try {
            if (!resetted && mThinCircleSecondIv != null) {
                mThinCircleSecondIv.animate()
                        .setInterpolator(FastOutSlowInInterpolator())
                        .scaleX(1f)
                        .scaleY(1f)
                        .withLayer()
                        .setDuration(1000)
                        .withEndAction {
                            if (!resetted && mThinCircleSecondIv != null) {
                                mThinCircleSecondIv.animate()
                                        .alpha(0f)
                                        .withLayer()
                                        .setDuration(200)
                                        .withEndAction {
                                            if (!resetted && mThinCircleSecondIv != null) {
                                                mThinCircleSecondIv.scaleX = 0f
                                                mThinCircleSecondIv.scaleY = 0f
                                                mThinCircleSecondIv.alpha = 1f

                                                startThinSecondCircleAnimation()
                                            }
                                        }
                            } else {
                                reset()
                            }
                        }.start()
            } else {
                reset()
            }
        } catch (e: Exception) {
            // Animation exception, just ignore it
        }

    }

    fun setIndicatorColorResource(indicatorColor: Int) {
        this.indicatorColor = indicatorColor

        mIconIv.setMColorFilter(indicatorColor)
        mOuterCircleIv.setMColorFilter(indicatorColor)
        mThinCircleIv.setMColorFilter(indicatorColor)
        mThinCircleSecondIv.setMColorFilter(indicatorColor)
    }

    fun reset() {
        runnableThinSecondCircleAnimation.removeCallbacks()
        resetted = true

        try {
            mIconIv.clearAnimation()
            mOuterCircleIv.clearAnimation()
            mThinCircleIv.clearAnimation()
            mThinCircleSecondIv.clearAnimation()

            mIconIv.postInvalidateOnAnimation()
            mOuterCircleIv.postInvalidateOnAnimation()
            mThinCircleIv.postInvalidateOnAnimation()
            mThinCircleSecondIv.postInvalidateOnAnimation()
        } catch (e: Exception) {
            // If view destroyed, dont need to check if null
        }

    }
}
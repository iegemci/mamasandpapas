package com.enesgemci.mamasandpapas.core.util.extensions

import android.app.Activity
import android.app.Dialog
import android.app.DialogFragment
import android.app.Fragment
import android.content.res.Resources
import android.support.v7.widget.RecyclerView
import android.util.DisplayMetrics
import android.view.View
import android.view.ViewPropertyAnimator
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.enesgemci.mamasandpapas.R
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty


fun <V : View> View.bindView(id: Int)
        : ReadOnlyProperty<View, V> = required(id, viewFinder)

fun <V : View> Activity.bindView(id: Int)
        : ReadOnlyProperty<Activity, V> = required(id, viewFinder)

fun <V : View> Dialog.bindView(id: Int)
        : ReadOnlyProperty<Dialog, V> = required(id, viewFinder)

fun <V : View> DialogFragment.bindView(id: Int)
        : ReadOnlyProperty<DialogFragment, V> = required(id, viewFinder)

fun <V : View> android.support.v4.app.DialogFragment.bindView(id: Int)
        : ReadOnlyProperty<android.support.v4.app.DialogFragment, V> = required(id, viewFinder)

fun <V : View> Fragment.bindView(id: Int)
        : ReadOnlyProperty<Fragment, V> = required(id, viewFinder)

fun <V : View> android.support.v4.app.Fragment.bindView(id: Int)
        : ReadOnlyProperty<android.support.v4.app.Fragment, V> = required(id, viewFinder)

fun <V : View> RecyclerView.ViewHolder.bindView(id: Int)
        : ReadOnlyProperty<RecyclerView.ViewHolder, V> = required(id, viewFinder)

fun <V : View> View.bindOptionalView(id: Int)
        : ReadOnlyProperty<View, V?> = optional(id, viewFinder)

fun <V : View> Activity.bindOptionalView(id: Int)
        : ReadOnlyProperty<Activity, V?> = optional(id, viewFinder)

fun <V : View> Dialog.bindOptionalView(id: Int)
        : ReadOnlyProperty<Dialog, V?> = optional(id, viewFinder)

fun <V : View> DialogFragment.bindOptionalView(id: Int)
        : ReadOnlyProperty<DialogFragment, V?> = optional(id, viewFinder)

fun <V : View> android.support.v4.app.DialogFragment.bindOptionalView(id: Int)
        : ReadOnlyProperty<android.support.v4.app.DialogFragment, V?> = optional(id, viewFinder)

fun <V : View> Fragment.bindOptionalView(id: Int)
        : ReadOnlyProperty<Fragment, V?> = optional(id, viewFinder)

fun <V : View> android.support.v4.app.Fragment.bindOptionalView(id: Int)
        : ReadOnlyProperty<android.support.v4.app.Fragment, V?> = optional(id, viewFinder)

fun <V : View> RecyclerView.ViewHolder.bindOptionalView(id: Int)
        : ReadOnlyProperty<RecyclerView.ViewHolder, V?> = optional(id, viewFinder)

fun <V : View> View.bindViews(vararg ids: Int)
        : ReadOnlyProperty<View, List<V>> = required(ids, viewFinder)

fun <V : View> Activity.bindViews(vararg ids: Int)
        : ReadOnlyProperty<Activity, List<V>> = required(ids, viewFinder)

fun <V : View> Dialog.bindViews(vararg ids: Int)
        : ReadOnlyProperty<Dialog, List<V>> = required(ids, viewFinder)

fun <V : View> DialogFragment.bindViews(vararg ids: Int)
        : ReadOnlyProperty<DialogFragment, List<V>> = required(ids, viewFinder)

fun <V : View> android.support.v4.app.DialogFragment.bindViews(vararg ids: Int)
        : ReadOnlyProperty<android.support.v4.app.DialogFragment, List<V>> = required(ids, viewFinder)

fun <V : View> Fragment.bindViews(vararg ids: Int)
        : ReadOnlyProperty<Fragment, List<V>> = required(ids, viewFinder)

fun <V : View> android.support.v4.app.Fragment.bindViews(vararg ids: Int)
        : ReadOnlyProperty<android.support.v4.app.Fragment, List<V>> = required(ids, viewFinder)

fun <V : View> RecyclerView.ViewHolder.bindViews(vararg ids: Int)
        : ReadOnlyProperty<RecyclerView.ViewHolder, List<V>> = required(ids, viewFinder)

fun <V : View> View.bindOptionalViews(vararg ids: Int)
        : ReadOnlyProperty<View, List<V>> = optional(ids, viewFinder)

fun <V : View> Activity.bindOptionalViews(vararg ids: Int)
        : ReadOnlyProperty<Activity, List<V>> = optional(ids, viewFinder)

fun <V : View> Dialog.bindOptionalViews(vararg ids: Int)
        : ReadOnlyProperty<Dialog, List<V>> = optional(ids, viewFinder)

fun <V : View> DialogFragment.bindOptionalViews(vararg ids: Int)
        : ReadOnlyProperty<DialogFragment, List<V>> = optional(ids, viewFinder)

fun <V : View> android.support.v4.app.DialogFragment.bindOptionalViews(vararg ids: Int)
        : ReadOnlyProperty<android.support.v4.app.DialogFragment, List<V>> = optional(ids, viewFinder)

fun <V : View> Fragment.bindOptionalViews(vararg ids: Int)
        : ReadOnlyProperty<Fragment, List<V>> = optional(ids, viewFinder)

fun <V : View> android.support.v4.app.Fragment.bindOptionalViews(vararg ids: Int)
        : ReadOnlyProperty<android.support.v4.app.Fragment, List<V>> = optional(ids, viewFinder)

fun <V : View> RecyclerView.ViewHolder.bindOptionalViews(vararg ids: Int)
        : ReadOnlyProperty<RecyclerView.ViewHolder, List<V>> = optional(ids, viewFinder)

private val View.viewFinder: View.(Int) -> View?
    get() = { findViewById(it) }
private val Activity.viewFinder: Activity.(Int) -> View?
    get() = { findViewById(it) }
private val Dialog.viewFinder: Dialog.(Int) -> View?
    get() = { findViewById(it) }
private val DialogFragment.viewFinder: DialogFragment.(Int) -> View?
    get() = { dialog?.findViewById(it) ?: view?.findViewById(it) }
private val android.support.v4.app.DialogFragment.viewFinder: android.support.v4.app.DialogFragment.(Int) -> View?
    get() = { dialog?.findViewById(it) ?: view?.findViewById(it) }
private val Fragment.viewFinder: Fragment.(Int) -> View?
    get() = { view.findViewById(it) }
private val android.support.v4.app.Fragment.viewFinder: android.support.v4.app.Fragment.(Int) -> View?
    get() = { view!!.findViewById(it) }
private val RecyclerView.ViewHolder.viewFinder: RecyclerView.ViewHolder.(Int) -> View?
    get() = { itemView.findViewById(it) }

private fun viewNotFound(id: Int, desc: KProperty<*>): Nothing =
        throw IllegalStateException("View ID $id for '${desc.name}' not found.")

@Suppress("UNCHECKED_CAST")
private fun <T, V : View> required(id: Int, finder: T.(Int) -> View?)
        = Lazy { t: T, desc -> t.finder(id) as V? ?: viewNotFound(id, desc) }

@Suppress("UNCHECKED_CAST")
private fun <T, V : View> optional(id: Int, finder: T.(Int) -> View?)
        = Lazy { t: T, desc -> t.finder(id) as V? }

@Suppress("UNCHECKED_CAST")
private fun <T, V : View> required(ids: IntArray, finder: T.(Int) -> View?)
        = Lazy { t: T, desc -> ids.map { t.finder(it) as V? ?: viewNotFound(it, desc) } }

@Suppress("UNCHECKED_CAST")
private fun <T, V : View> optional(ids: IntArray, finder: T.(Int) -> View?)
        = Lazy { t: T, desc -> ids.map { t.finder(it) as V? }.filterNotNull() }

// Like Kotlin's lazy delegate but the initializer gets the target and metadata passed to it
private class Lazy<T, V>(private val initializer: (T, KProperty<*>) -> V) : ReadOnlyProperty<T, V> {
    private object EMPTY

    private var value: Any? = EMPTY

    override fun getValue(thisRef: T, property: KProperty<*>): V {
        if (value == EMPTY) {
            value = initializer(thisRef, property)
        }
        @Suppress("UNCHECKED_CAST")
        return value as V
    }
}

var View.isVisible: Boolean
    get() {
        return this.visibility == View.VISIBLE
    }
    set(value) {
        this.visibility = if (value) View.VISIBLE else View.GONE
    }

/**
 * Sets the view's visibility to GONE
 */
inline fun View.hide() {
    visibility = View.GONE
}

/**
 * Sets the view's visibility to VISIBLE
 */
inline fun View.show() {
    visibility = View.VISIBLE
}

/**
 * Sets the view's visibility to INVISIBLE
 */
inline fun View.invisible() {
    visibility = View.INVISIBLE
}

/**
 * Toggle's view's visibility. If View is visible, then sets to gone. Else sets Visible
 */
inline fun View.toggle() {
    visibility = if (visibility == View.VISIBLE) View.GONE else View.VISIBLE
}

/**
 * Fades in the View
 */
inline fun View.fadeIn(duration: Long = 400): ViewPropertyAnimator? {
    return animate()
            .alpha(1.0f)
            .setDuration(duration)
}

/**
 * Fades out the View
 */
inline fun View.fadeOut(duration: Long = 400): ViewPropertyAnimator? {
    return animate()
            .alpha(0.0f)
            .setDuration(duration)
}

/**
 * Fades to a specific alpha between 0 to 1
 */
inline fun View.fadeTo(alpha: Float, duration: Long = 400): ViewPropertyAnimator? {
    return animate()
            .alpha(alpha)
            .setDuration(duration)
}

/**
 * Animation: Enter from left
 */
inline fun View.enterFromLeft(duration: Long = 400): ViewPropertyAnimator? {
    val x = this.x    // store initial x
    this.x = 0f - this.width    // move to left

    return animate()
            .x(x)
            .setDuration(duration)
}

/**
 * Animation: Enter from right
 */
inline fun View.enterFromRight(duration: Long = 400): ViewPropertyAnimator? {
    val widthPixels = Resources.getSystem().displayMetrics.widthPixels    // get device width
    val x = this.x    // store initial x
    this.x = widthPixels.toFloat()    // move to right

    return animate()
            .x(x)
            .setDuration(duration)
}

/**
 * Animation: Enter from top
 */
inline fun View.enterFromTop(duration: Long = 400): ViewPropertyAnimator? {
    val y = this.y    // store initial y
    this.y = 0f - this.height    // move to top

    return animate()
            .y(y)
            .setDuration(duration)
}

/**
 * Animation: Enter from bottom
 */
inline fun View.enterFromBottom(duration: Long = 400): ViewPropertyAnimator? {
    val heightPixels = Resources.getSystem().displayMetrics.heightPixels    // get device height

    val y = this.y    // store initial y
    this.y = heightPixels.toFloat()   // move to bottom

    return animate()
            .y(y)
            .setDuration(duration)
}

/**
 * Animation: Exit to left
 */
inline fun View.exitToLeft(duration: Long = 400): ViewPropertyAnimator? {
    return animate()
            .x(0f - this.width)
            .setDuration(duration)
}

/**
 * Animation: Exit to right
 */
inline fun View.exitToRight(duration: Long = 400): ViewPropertyAnimator? {
    val widthPixels = Resources.getSystem().displayMetrics.widthPixels    // get device width

    return animate()
            .x(widthPixels.toFloat())
            .setDuration(duration)
}

/**
 * Animation: Exit to top
 */
inline fun View.exitToTop(duration: Long = 400): ViewPropertyAnimator? {
    return animate()
            .y(0f - this.height)
            .setDuration(duration)
}

/**
 * Animation: Exit to bottom
 */
inline fun View.exitToBottom(duration: Long = 400): ViewPropertyAnimator? {
    val heightPixels = Resources.getSystem().displayMetrics.heightPixels    // get device height

    return animate()
            .y(heightPixels.toFloat())
            .setDuration(duration)
}

fun View?.setViewVisibilityWithAlpha(isVisible: Boolean, listener: Animation.AnimationListener? = null) {
    this?.let {
        if (isVisible) {
            if (this.isVisible) {
                return
            }

            show()
        } else {
            if (!this.isVisible) {
                return
            }

            hide()
        }

        val animation = AnimationUtils.loadAnimation(context,
                if (isVisible)
                    R.anim.abc_fade_in
                else
                    R.anim.abc_fade_out)

        listener?.let { animation.setAnimationListener(listener) }

        this.startAnimation(animation)
    }
}

fun View?.setViewVisibilityWithGrowShirink(isVisible: Boolean, listener: Animation.AnimationListener? = null) {
    this?.let {
        if (isVisible) {
            if (this.isVisible) {
                return
            }

            show()
        } else {
            if (!this.isVisible) {
                return
            }

            hide()
        }

        val animation = AnimationUtils.loadAnimation(context,
                if (isVisible)
                    R.anim.abc_grow_fade_in_from_bottom
                else
                    R.anim.abc_shrink_fade_out_from_bottom)

        listener?.let { animation.setAnimationListener(listener) }

        this.startAnimation(animation)
    }
}

fun View?.getViewSize(): DisplayMetrics? {
    this?.let {
        val metrics = DisplayMetrics()
        val visibility = it.visibility

        if (visibility == View.GONE) {
            it.visibility = View.VISIBLE
        }

        val measuredWidth = View.MeasureSpec.makeMeasureSpec(it.width, View.MeasureSpec.UNSPECIFIED)
        val measuredHeight = View.MeasureSpec.makeMeasureSpec(it.height, View.MeasureSpec.UNSPECIFIED)
        it.measure(measuredWidth, measuredHeight)

        metrics.widthPixels = it.measuredWidth
        metrics.heightPixels = it.measuredHeight

        it.visibility = visibility
        return metrics
    } ?: return null
}
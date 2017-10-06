package com.enesgemci.mamasandpapas.util

import android.animation.Keyframe
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.SparseArray
import android.util.TypedValue
import android.view.View
import android.view.ViewParent
import android.view.Window
import com.enesgemci.mamasandpapas.core.util.extensions.dpToPx
import com.google.gson.Gson
import timber.log.Timber
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.util.*

/**
 * Created by enesgemci on 05/10/2017.
 */
object UIUtils {

    val XML_NAMESPACE_ANDROID = "http://schemas.android.com/apk/res/android"
    val mainThreadHandler = Handler(Looper.getMainLooper())

    private val mHandler = Handler()
    private var lastClickTimes: SparseArray<Long>? = null
    private var lastClickedViews: MutableMap<View, Long>? = null
    val gson = Gson()

    val isJellybeanOrLater: Boolean
        get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN

    fun postDelayed(runnable: Runnable, delay: Long) {
        try {
            mHandler.postDelayed(runnable, delay)
        } catch (e: Exception) {
            Timber.e(e)
        }
    }

    fun post(runnable: Runnable) {
        try {
            mHandler.post(runnable)
        } catch (e: Exception) {
            Timber.e(e)
        }
    }

    fun removeCallbacks(runnable: Runnable) {
        try {
            mHandler.removeCallbacks(runnable)
        } catch (e: Exception) {
            Timber.e(e)
        }
    }

    fun getScreenWidth(context: Context): Int {
        return context.resources.displayMetrics.widthPixels
    }

    fun getScreenHeight(context: Context): Int {
        return context.resources.displayMetrics.heightPixels
    }

    fun getStatusBarHeight(context: Context): Int {
        return try {
            var result = 0
            val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
            if (resourceId > 0) {
                result = context.resources.getDimensionPixelSize(resourceId)
            }
            result
        } catch (ex: Exception) {
            25.dpToPx(context)
        }

    }

    fun multiClickPrevent(v: View?): Boolean {
        if (v == null) {
            return false
        }
        return multiClickPrevent(v.id, 1000)
    }

    fun multiClickPrevent(id: Int?, minTime: Int): Boolean {
        if (lastClickTimes == null) {
            lastClickTimes = SparseArray()
        }

        if (lastClickTimes!!.get(id!!, -1L) >= 0) {
            val lastClickTime = lastClickTimes!!.get(id)

            if (System.currentTimeMillis() - lastClickTime < minTime) {
                return true
            } else {
                lastClickTimes!!.remove(id)
                return false
            }
        } else {
            lastClickTimes!!.put(id, System.currentTimeMillis())
        }
        return false
    }

    fun multiClickPrevent(view: View, minTime: Int): Boolean {
        if (lastClickedViews == null) {
            lastClickedViews = HashMap()
        }

        if (lastClickedViews!!.containsKey(view)) {
            val lastClickTime = lastClickedViews!![view] as Long

            return if (System.currentTimeMillis().minus(lastClickTime) < minTime) {
                true
            } else {
                lastClickedViews!!.remove(view)
                false
            }
        } else {
            lastClickedViews!!.put(view, System.currentTimeMillis())
        }

        return false
    }

    fun getPulseAnimator(labelToAnimate: View, decreaseRatio: Float,
                         increaseRatio: Float): ObjectAnimator {
        val k0 = Keyframe.ofFloat(0f, 1f)
        val k1 = Keyframe.ofFloat(0.275f, decreaseRatio)
        val k2 = Keyframe.ofFloat(0.69f, increaseRatio)
        val k3 = Keyframe.ofFloat(1f, 1f)

        val scaleX = PropertyValuesHolder.ofKeyframe("scaleX", k0, k1, k2, k3)
        val scaleY = PropertyValuesHolder.ofKeyframe("scaleY", k0, k1, k2, k3)
        val pulseAnimator = ObjectAnimator.ofPropertyValuesHolder(labelToAnimate, scaleX, scaleY)
        pulseAnimator.duration = 544

        return pulseAnimator
    }

    @SuppressLint("NewApi")
    fun tryAccessibilityAnnounce(view: View?, text: CharSequence?) {
        if (isJellybeanOrLater && view != null && text != null) {
            view.announceForAccessibility(text)
        }
    }

    fun darkenColor(color: Int): Int {
        val hsv = FloatArray(3)
        Color.colorToHSV(color, hsv)
        hsv[2] = hsv[2] * 0.8f // value component
        return Color.HSVToColor(hsv)
    }

    /**
     * Gets the colorAccent from the current context, if possible/available

     * @return -1 if accent color invalid, otherwise the accent color of the current context
     */
    fun getAccentColorFromThemeIfAvailable(context: Context): Int {
        val typedValue = TypedValue()
        //First, try the android:colorAccent
        if (Build.VERSION.SDK_INT >= 21) {
            context.theme.resolveAttribute(android.R.attr.colorAccent, typedValue, true)
            return typedValue.data
        }
        //Next, try colorAccent from support lib
        val colorAccentResId = context.resources.getIdentifier("colorAccent", "attr", context.packageName)
        if (colorAccentResId == 0) {
            return -1
        }

        if (!context.theme.resolveAttribute(colorAccentResId, typedValue, true)) {
            return -1
        }
        return typedValue.data
    }

    fun getScreenHeight(activity: Activity): Int {
        val content = activity.window.decorView.findViewById<View>(Window.ID_ANDROID_CONTENT)
        return content.height
    }

    fun getToolbarHeight(activity: Activity): Int {
        val tv = TypedValue()
        if (activity.theme.resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            return TypedValue.complexToDimensionPixelSize(tv.data, activity.resources.displayMetrics)
        }
        return 0
    }

    fun <P> getParent(clazz: Class<P>?, parent: ViewParent?): P? {
        if (parent == null || clazz == null) {
            return null
        }

        return if (parent.javaClass == clazz || parent.javaClass.superclass == clazz) {
            parent as P?
        } else {
            getParent(clazz, parent.parent)
        }
    }

    fun checkParentEquality(clazz: Class<*>?, viewParent: ViewParent?): Boolean {
        if (viewParent == null || clazz == null) {
            return false
        }

        return if (viewParent.javaClass == clazz) {
            true
        } else {
            checkParentEquality(clazz, viewParent.parent)
        }
    }

    fun readFromAssets(context: Context, filename: String): String {
        val reader: BufferedReader
        val sb = StringBuilder()

        try {
            reader = BufferedReader(InputStreamReader(context.assets.open(filename)))
            var mLine: String? = reader.readLine()

            while (mLine != null) {
                sb.append(mLine)
                mLine = reader.readLine()
            }

            reader.close()
        } catch (e: Exception) {
        }

        return sb.toString()
    }

    fun serializeObject(objToSerialize: Any): String {
        return gson.toJson(objToSerialize)
    }

    inline fun <reified T> deserializeObject(json: String): T {
        return gson.fromJson<T>(json, T::class.java)
    }

    val isDeviceRooted: Boolean
        get() {
            var rooted = false
            val places = arrayOf("/sbin/", "/system/bin/", "/system/xbin/", "/ProductListResponse/local/xbin/",
                    "/ProductListResponse/local/bin/", "/system/sd/xbin/", "/system/bin/failsafe/", "/ProductListResponse/local/",
                    "/system/app/Superuser.apk", "/sbin/su", "/system/bin/su", "/system/xbin/su",
                    "/ProductListResponse/local/xbin/su", "/ProductListResponse/local/bin/su", "/system/sd/xbin/su",
                    "/system/bin/failsafe/su", "/ProductListResponse/local/su", "/su/bin/su", "/su/bin/")

            for (where in places) {
                if (File(where + "su").exists()) {
                    rooted = true
                    break
                }
            }

            return rooted
        }
}
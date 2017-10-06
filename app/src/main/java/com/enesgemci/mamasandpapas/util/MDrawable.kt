package com.enesgemci.mamasandpapas.util

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.*
import android.graphics.drawable.shapes.OvalShape
import android.graphics.drawable.shapes.RoundRectShape
import android.os.Build
import android.support.annotation.ColorRes
import android.support.annotation.Keep
import android.support.annotation.RequiresApi
import android.support.v4.content.ContextCompat
import android.util.TypedValue
import com.enesgemci.mamasandpapas.R
import com.enesgemci.mamasandpapas.core.util.extensions.dpToPx
import java.util.*

/**
 * Created by enesgemci on 06/10/2017.
 */
class MDrawable private constructor(context: Context) {

    var pressedColor: Int = 0
    var backgroundColor: Int
    var borderColor: Int = 0
    var topLeftRadius: Float = 0f
    var topRightRadius: Float = 0f
    var bottomRightRadius: Float = 0f
    var bottomLeftRadius: Float = 0f
    var types: MutableList<Type>
    var shape: Shape
    var borderWidth: Float
    var gradientOrientation: GradientDrawable.Orientation? = null
    var gradientEndColor: Int = 0

    init {

        val typedValue = TypedValue()

        pressedColor = try {
            if (context.theme.resolveAttribute(android.R.attr.colorPrimary, typedValue, true)) {
                ContextCompat.getColor(context, typedValue.resourceId)
            } else {
                ContextCompat.getColor(context, R.color.colorPrimaryDark)
            }
        } catch (e: Exception) {
            ContextCompat.getColor(context, R.color.colorPrimaryDark)
        }

        backgroundColor = -1
        shape = Shape.RECTANGLE
        types = ArrayList(2)
        borderWidth = 1f.dpToPx(context)
    }

    internal fun get(): Drawable {
        val drawables = ArrayList<Drawable>()
        val outerRadii = floatArrayOf(topLeftRadius, topLeftRadius, topRightRadius, topRightRadius, bottomRightRadius, bottomRightRadius, bottomLeftRadius, bottomLeftRadius)

        types.forEach {
            when (it) {
                MDrawable.Type.BACKGROUND -> {
                    val shapeDrawable = getDrawable(shape, backgroundColor, outerRadii, gradientEndColor, gradientOrientation)

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        drawables.add(MRippleDrawable(ColorStateList.valueOf(pressedColor), shapeDrawable, null))
                    } else {
                        val pressed = getDrawable(shape, pressedColor, outerRadii, gradientEndColor, gradientOrientation)

                        val stateListDrawable = StateListDrawable()
                        stateListDrawable.addState(intArrayOf(android.R.attr.state_pressed), pressed)
                        stateListDrawable.addState(intArrayOf(android.R.attr.state_focused), pressed)
                        stateListDrawable.addState(intArrayOf(android.R.attr.state_activated), pressed)
                        stateListDrawable.addState(intArrayOf(), shapeDrawable)

                        drawables.add(stateListDrawable)
                    }
                }
                MDrawable.Type.BORDER -> drawables.add(getBorderDrawable(shape, borderColor, outerRadii, borderWidth, gradientEndColor, gradientOrientation))
            }
        }

        return LayerDrawable(drawables.toTypedArray())
    }

    class Builder(val context: Context) {

        private var mDrawable: MDrawable = MDrawable(context)

        /**
         * @param topLeftRadius top left corner radius of view
         */
        @Keep
        fun setTopLeftRadius(topLeftRadius: Float): Builder {
            mDrawable.topLeftRadius = topLeftRadius
            return this
        }

        /**
         * @param topRightRadius top right corner of view
         */
        @Keep
        fun setTopRightRadius(topRightRadius: Float): Builder {
            mDrawable.topRightRadius = topRightRadius
            return this
        }

        /**
         * @param bottomRightRadius bottom right corner radius of view
         */
        @Keep
        fun setBottomRightRadius(bottomRightRadius: Float): Builder {
            mDrawable.bottomRightRadius = bottomRightRadius
            return this
        }


        /**
         * @param bottomLeftRadius bottom left corner radius of view
         */
        @Keep
        fun setBottomLeftRadius(bottomLeftRadius: Float): Builder {
            mDrawable.bottomLeftRadius = bottomLeftRadius
            return this
        }

        /**
         * @param radius corners radius of view
         */
        @Keep
        fun setRadius(radius: Float): Builder {
            mDrawable.topLeftRadius = radius
            mDrawable.topRightRadius = radius
            mDrawable.bottomLeftRadius = radius
            mDrawable.bottomRightRadius = radius
            return this
        }

        /**
         * @param type drawable type.
         * *
         *
         *see [Type]
         */
        @Keep
        fun addType(type: Type): Builder {
            if (!mDrawable.types.contains(type)) {
                mDrawable.types.add(type)
            }
            return this
        }

        /**
         * @param types drawable types.
         * *
         *
         *see [Type]
         */
        @Keep
        fun addType(types: List<Type>): Builder {
            for (type in types) {
                if (!mDrawable.types.contains(type)) {
                    mDrawable.types.add(type)
                }
            }
            return this
        }

        /**
         * @param width with of border background
         */
        @Keep
        fun setBorderWidth(width: Float): Builder {
            mDrawable.borderWidth = width
            return this
        }

        /**
         * @param shape shape of drawable.
         * *
         *
         *see [Shape]
         */
        @Keep
        fun setShape(shape: Shape): Builder {
            mDrawable.shape = shape
            return this
        }

        /**
         * @param orientation orientation of gradient drawable.
         * *
         *
         *see [GradientDrawable.Orientation]
         */
        @Keep
        fun setGradientOrientation(orientation: Int): Builder {
            mDrawable.gradientOrientation = getGradientOrientation(orientation)
            return this
        }

        /**
         * Creates default background drawable
         * if [Build.VERSION] Lollipop or greater, then creates [MRippleDrawable]
         * otherwise creates [StateListDrawable]
         */
        @Keep
        fun build(): Drawable {
            return mDrawable.get()
        }

        /**
         * @return [MDrawable]
         */
        @Keep
        fun raw(): MDrawable {
            return mDrawable
        }

        private fun getGradientOrientation(i: Int): GradientDrawable.Orientation {
            when (i) {
                0 -> return GradientDrawable.Orientation.BL_TR
                1 -> return GradientDrawable.Orientation.BOTTOM_TOP
                2 -> return GradientDrawable.Orientation.BR_TL
                3 -> return GradientDrawable.Orientation.LEFT_RIGHT
                4 -> return GradientDrawable.Orientation.RIGHT_LEFT
                5 -> return GradientDrawable.Orientation.TL_BR
                6 -> return GradientDrawable.Orientation.TOP_BOTTOM
                7 -> return GradientDrawable.Orientation.TR_BL
                else -> return GradientDrawable.Orientation.BOTTOM_TOP
            }
        }

        /**
         * @param pressedColor ripple color resource or pressed color resource
         */
        @Keep
        fun setPressedColor(pressedColor: Int): Builder {
            if (pressedColor > -1) {
                mDrawable.pressedColor = pressedColor
            }
            return this
        }

        /**
         * @param backgroundColor main color resource
         */
        @Keep
        fun setBackgroundColor(backgroundColor: Int): Builder {
            mDrawable.backgroundColor = backgroundColor
            return this
        }

        /**
         * @param borderColor border color resource
         */
        @Keep
        fun setBorderColor(borderColor: Int): Builder {
            mDrawable.borderColor = borderColor
            return this
        }

        /**
         * @param gradientEndColor end color of gradient
         */
        @Keep
        fun setGradientEndColor(gradientEndColor: Int): Builder {
            mDrawable.borderColor = gradientEndColor
            return this
        }

        /**
         * @param pressedColorResId ripple color resource or pressed color resource
         */
        @Keep
        fun setPressedColorResId(@ColorRes pressedColorResId: Int): Builder {
            if (pressedColorResId > -1) {
                mDrawable.pressedColor = ContextCompat.getColor(context, pressedColorResId)
            }
            return this
        }

        /**
         * @param backgroundColorResId main color resource
         */
        @Keep
        fun setBackgroundColorResId(@ColorRes backgroundColorResId: Int): Builder {
            mDrawable.backgroundColor = ContextCompat.getColor(context, backgroundColorResId)
            return this
        }

        /**
         * @param borderColorResId border color resource
         */
        @Keep
        fun setBorderColorResId(@ColorRes borderColorResId: Int): Builder {
            mDrawable.borderColor = ContextCompat.getColor(context, borderColorResId)
            return this
        }

        /**
         * @param gradientEndColorResId end color resource of gradient
         */
        @Keep
        fun setGradientEndColorResId(@ColorRes gradientEndColorResId: Int): Builder {
            mDrawable.borderColor = ContextCompat.getColor(context, gradientEndColorResId)
            return this
        }
    }

    private fun getDrawable(shape: Shape, color: Int, outerRadii: FloatArray, endColor: Int, orientation: GradientDrawable.Orientation?): Drawable {
        when (shape) {
            MDrawable.Shape.RECTANGLE -> return getRectDrawable(color, outerRadii)
            MDrawable.Shape.OVAL -> return getOvalDrawable(color)
        //            case GRADIENT:
        //                return getGradientDrawable(color, outerRadii, endColor, orientation);
            else -> return getRectDrawable(color, outerRadii)
        }
    }

    //    private Drawable getGradientDrawable(int color, float[] outerRadii, int endColor, GradientDrawable.Orientation orientation) {
    //        val drawable = (GradientDrawable) new GradientDrawable(orientation, new int[]{color, endColor}).mutate();
    //        drawable.setGradientType(GradientDrawable.LINEAR_GRADIENT);
    //        drawable.setCornerRadii(outerRadii);
    //
    //        val r = new RoundRectShape(outerRadii, null, null);
    //        ShapeDrawable shapeDrawable = new ShapeDrawable(r);
    //        shapeDrawable.getPaint().setStyle(Paint.Style.FILL);
    //        shapeDrawable.getPaint().setColor(color);
    //
    //        return new LayerDrawable(new Drawable[]{shapeDrawable, drawable});
    //    }

    private fun getBorderDrawable(shape: Shape, color: Int, outerRadii: FloatArray, borderWidth: Float, endColor: Int, orientation: GradientDrawable.Orientation?): Drawable {
        when (shape) {
            MDrawable.Shape.RECTANGLE -> return getRectBorderDrawable(color, outerRadii, borderWidth)
            MDrawable.Shape.OVAL -> return getOvalBorderDrawable(color, borderWidth)
        //            case GRADIENT:
        //                return getGradientBorderDrawable(color, outerRadii, borderWidth, endColor, orientation);
            else -> return getRectBorderDrawable(color, outerRadii, borderWidth)
        }
    }

    private fun getRectDrawable(color: Int, outerRadii: FloatArray): Drawable {
        //        val drawable = (GradientDrawable) new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP, new int[]{color, color}).mutate();
        //        drawable.setShape(GradientDrawable.RECTANGLE);
        //        drawable.setCornerRadii(outerRadii);

        val r = RoundRectShape(outerRadii, null, null)
        val drawable = ShapeDrawable(r)
        drawable.paint.style = Paint.Style.FILL
        drawable.paint.color = color

        return drawable
    }

    private fun getOvalDrawable(color: Int): Drawable {
        val o = OvalShape()

        val drawable = ShapeDrawable(o)
        drawable.paint.style = Paint.Style.FILL
        drawable.paint.color = color

        return drawable
    }

    private fun getRectBorderDrawable(color: Int, outerRadii: FloatArray, width: Float): Drawable {
        //        val r = new RoundRectShape(outerRadii, null, null);
        //        ShapeDrawable drawable = new ShapeDrawable(r);
        //        drawable.getPaint().setColor(color);
        //        drawable.getPaint().setStyle(Paint.Style.STROKE);
        //        drawable.getPaint().setStrokeWidth(width);

        val drawable = GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP, intArrayOf(Color.TRANSPARENT, Color.TRANSPARENT)).mutate() as GradientDrawable
        drawable.shape = GradientDrawable.RECTANGLE
        drawable.cornerRadii = outerRadii
        drawable.setStroke(width.toInt(), color)

        return drawable
    }

    private fun getOvalBorderDrawable(color: Int, width: Float): Drawable {
        val drawable = GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP, intArrayOf(Color.TRANSPARENT, Color.TRANSPARENT)).mutate() as GradientDrawable
        drawable.shape = GradientDrawable.OVAL
        drawable.setStroke(width.toInt(), color)

        return drawable
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    internal inner class MRippleDrawable
    /**
     * Creates a new ripple drawable with the specified ripple color and
     * optional content and mask drawables.

     * @param color   The ripple color
     * *
     * @param content The content drawable, may be `null`
     * *
     * @param mask    The mask drawable, may be `null`
     */
    (color: ColorStateList, content: Drawable?, mask: Drawable?) : RippleDrawable(color, content, mask)

    enum class Shape constructor(val index: Int, val shape: String) {

        RECTANGLE(0, "rectangle"),
        OVAL(1, "oval");

        companion object {

            fun parse(i: Int): Shape {
                return values().firstOrNull { it.index == i }
                        ?: RECTANGLE
            }

            fun parse(shape: String): Shape {
                when (shape) {
                    "rectangle" -> return RECTANGLE
                    "oval" -> return OVAL
                //                case "gradient":
                //                    return GRADIENT;
                    else -> return RECTANGLE
                }
            }
        }
    }

    enum class Type constructor(val index: Int, val type: String) {

        BACKGROUND(0, "background"),
        BORDER(1, "border");

        companion object {

            fun parse(i: Int): Type {
                return values().firstOrNull { it.index == i }
                        ?: BACKGROUND
            }

            fun parse(type: String): Type {
                when (type) {
                    "background" -> return BACKGROUND
                    "border" -> return BORDER
                    else -> return BACKGROUND
                }
            }
        }
    }
}
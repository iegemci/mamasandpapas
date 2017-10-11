package com.enesgemci.mamasandpapas.widget

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.enesgemci.mamasandpapas.R
import com.enesgemci.mamasandpapas.core.util.extensions.dpToPx
import com.enesgemci.mamasandpapas.util.MDrawable
import kotlinx.android.synthetic.main.layout_counter_view.view.*

/**
 * Created by enesgemci on 11/10/2017.
 */
class MCountView : LinearLayout {

    var maxCount: Int = 9999999
        set(value) {
            field = value
            isEnabled = isEnabled && true
        }
    var minCount: Int = 1

    private var increaseStatus: Boolean = true
    private var decreaseStatus: Boolean = false

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)

        if (enabled) {
            increaseEnabled = currentQuantity < maxCount && currentQuantity in 1..maxCount
            decreaseEnabled = currentQuantity > minCount
        } else {
            increaseStatus = increaseEnabled
            decreaseStatus = decreaseEnabled

            increaseEnabled = false
            decreaseEnabled = false
        }
    }

    var currentQuantity: Int = 1
        get() = quantityTv.text.toString().toInt()
        private set

    var increaseEnabled: Boolean = true
        set(value) {
            field = value
            increaseTv.isEnabled = value
        }
    var decreaseEnabled: Boolean = false
        set(value) {
            field = value
            decreaseTv.isEnabled = value
        }

    var onQuantityChangedListener: OnQuantityChangedListener? = null

    constructor(context: Context) : super(context)

    constructor(context: Context, attr: AttributeSet) : super(context, attr)

    constructor(context: Context, attr: AttributeSet, defStyle: Int) : super(context, attr, defStyle)

    init {
        View.inflate(context, R.layout.layout_counter_view, this)

        quantityArea.background = MDrawable.Builder(context)
                .setBorderColorResId(R.color.text_color_light)
                .addType(MDrawable.Type.BORDER)
                .setRadius(5f.dpToPx(context))
                .build()

        decreaseTv.isEnabled = false

        decreaseTv.setOnClickListener {

            if (currentQuantity > 1) {
                quantityTv.text = "${currentQuantity - 1}"
            }
        }
        increaseTv.setOnClickListener {
            if (currentQuantity <= maxCount) {
                quantityTv.text = "${currentQuantity + 1}"
            }
        }
        quantityTv.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable?) {
                isEnabled = true

                onQuantityChangedListener?.onQuantityChanged(currentQuantity)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
    }

    interface OnQuantityChangedListener {

        fun onQuantityChanged(quantity: Int)
    }
}
package com.enesgemci.mamasandpapas.adapter

import android.content.Context
import android.graphics.Color
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.enesgemci.mamasandpapas.R
import com.enesgemci.mamasandpapas.core.util.extensions.dpToPx
import com.enesgemci.mamasandpapas.data.ConfigurableAttributeOptionModel
import com.enesgemci.mamasandpapas.util.MDrawable

/**
 * Created by enesgemci on 09/10/2017.
 */
class AdapterProductSize(private var context: Context, var selectedSize: ConfigurableAttributeOptionModel?, var sizes: List<String>, var onClickListener: View.OnClickListener) : RecyclerView.Adapter<AdapterProductSize.ViewHolder>() {

    private var layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private var clickedPosition: Int? = -1
    private var disabledList: List<String>? = null

    private val active = MDrawable.Builder(context)
            .setBackgroundColorResId(R.color.black)
            .setPressedColorResId(R.color.text_color_light)
            .addType(MDrawable.Type.BACKGROUND)
            .setRadius(5f.dpToPx(context))
            .build()
    private val passive = MDrawable.Builder(context)
            .setBorderColorResId(R.color.text_color_light)
            .addType(MDrawable.Type.BORDER)
            .setRadius(5f.dpToPx(context))
            .build()
    private val disabled = MDrawable.Builder(context)
            .setBackgroundColorResId(R.color.text_color_light)
            .setPressedColorResId(R.color.transparent)
            .addType(MDrawable.Type.BACKGROUND)
            .setRadius(5f.dpToPx(context))
            .build()

    override fun getItemCount(): Int {
        return sizes.size
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.setData(sizes[position], position)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(layoutInflater.inflate(R.layout.layout_product_size, null))
    }

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {

        private val root: ConstraintLayout = v.findViewById(R.id.detail_root)
        private val sizeTv: TextView = v.findViewById(R.id.sizeTv)

        fun setData(size: String, position: Int) {
            sizeTv.text = size
            root.setOnClickListener {
                if (disabledList == null || !disabledList!!.contains(size)) {
                    clickedPosition = if (root.background == active) {
                        -1
                    } else {
                        position
                    }

                    root.tag = clickedPosition
                    onClickListener.onClick(root)
                    notifyDataSetChanged()
                }
            }

            if (disabledList != null && disabledList!!.contains(size)) {
                sizeTv.setTextColor(Color.WHITE)
                root.background = disabled
            } else {
                if (selectedSize != null && selectedSize!!.label == size) {
                    selectedSize = null
                    clickedPosition = position
                    root.tag = clickedPosition
//                onClickListener.onClick(root)
                }

                if (clickedPosition == position) {
                    if (root.background == active) {
                        sizeTv.setTextColor(Color.BLACK)
                        root.background = passive
                    } else {
                        sizeTv.setTextColor(Color.WHITE)
                        root.background = active
                    }
                } else {
                    sizeTv.setTextColor(Color.BLACK)
                    root.background = passive
                }
            }
        }
    }

    fun disabledSizes(keys: Set<String>) {
        if (disabledList != keys.toList()) {
            this.disabledList = keys.toList()
            notifyDataSetChanged()
        }
    }
}
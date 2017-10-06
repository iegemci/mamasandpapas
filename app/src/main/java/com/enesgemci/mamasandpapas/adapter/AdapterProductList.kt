package com.enesgemci.mamasandpapas.adapter

import android.content.Context
import android.os.Build
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.enesgemci.mamasandpapas.BuildConfig
import com.enesgemci.mamasandpapas.R
import com.enesgemci.mamasandpapas.core.util.extensions.toMoney
import com.enesgemci.mamasandpapas.data.ProductModel
import com.enesgemci.mamasandpapas.util.GlideApp
import com.enesgemci.mamasandpapas.util.MDrawable

/**
 * Created by enesgemci on 06/10/2017.
 */
internal class AdapterProductList(private var context: Context, private var productList: List<ProductModel?>, var onClickListener: View.OnClickListener) : RecyclerView.Adapter<AdapterProductList.ViewHolder>() {

    private var layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getItemCount(): Int {
        return productList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(layoutInflater.inflate(R.layout.layout_product_item, null))
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.setData(productList[position], position)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val root: ConstraintLayout = view.findViewById(R.id.product_root)
        private val image: ImageView = view.findViewById(R.id.product_image)
        private val title: TextView = view.findViewById(R.id.product_title)
        private val desc: TextView = view.findViewById(R.id.product_description)
        private val price: TextView = view.findViewById(R.id.product_price)

        fun setData(product: ProductModel?, position: Int) {
            root.tag = position
            root.setOnClickListener { onClickListener.onClick(root) }
            root.background = MDrawable.Builder(context)
                    .setBackgroundColorResId(R.color.white)
                    .setPressedColorResId(R.color.colorPrimary)
                    .addType(MDrawable.Type.BACKGROUND)
                    .build()

            product?.image?.let {
                GlideApp.with(context)
                        .asDrawable()
                        .placeholder(R.drawable.bg_placeholder)
                        .load(BuildConfig.HOST_IMAGE + product.image)
                        .into(image)
            }

            title.text = product?.name ?: ""

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                desc.text = Html.fromHtml(product?.description, Html.FROM_HTML_MODE_COMPACT)
            } else {
                desc.text = Html.fromHtml(product?.description)
            }

            price.text = product?.price!!.toMoney() + " AED"
        }
    }
}
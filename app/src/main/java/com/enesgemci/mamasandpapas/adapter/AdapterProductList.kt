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
internal class AdapterProductList(private var context: Context, var onClickListener: View.OnClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    var productList: ArrayList<ProductModel?> = ArrayList()

    fun loadData(list: ArrayList<ProductModel>?) {
        list?.let { productList.addAll(list) } ?: productList.clear()
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(layoutInflater.inflate(R.layout.layout_product_item, null))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        (holder as ViewHolder)?.setData(productList[position])
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val root: ConstraintLayout = view.findViewById(R.id.product_root)
        private val image: ImageView = view.findViewById(R.id.product_image)
        private val title: TextView = view.findViewById(R.id.product_title)
        private val desc: TextView = view.findViewById(R.id.product_description)
        private val price: TextView = view.findViewById(R.id.product_price)

        fun setData(product: ProductModel?) {
            root.tag = product
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
                        .load(BuildConfig.HOST_IMAGE + (product.smallImage ?: (product.thumbnail ?: product.image)))
                        .timeout(30000)
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
package com.enesgemci.mamasandpapas.adapter

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.enesgemci.mamasandpapas.BuildConfig
import com.enesgemci.mamasandpapas.R
import com.enesgemci.mamasandpapas.data.MediaModel
import com.enesgemci.mamasandpapas.util.GlideApp

/**
 * Created by enesgemci on 11/10/2017.
 */
class MPagerAdapter(var context: Context, var media: List<MediaModel?>?) : PagerAdapter() {

    private var layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun isViewFromObject(view: View?, obj: Any?): Boolean {
        return view == obj
    }

    override fun getCount(): Int {
        return media?.size ?: 0
    }

    override fun instantiateItem(container: ViewGroup?, position: Int): Any {
        var imageView = layoutInflater.inflate(R.layout.layout_product_image, null) as ImageView
        GlideApp.with(context)
                .asDrawable()
                .placeholder(R.drawable.bg_placeholder)
                .load(BuildConfig.HOST_IMAGE + media!![position]!!.src)
                .timeout(30000)
                .into(imageView)
        container?.addView(imageView)

        return imageView
    }

    override fun destroyItem(container: ViewGroup?, position: Int, obj: Any?) {
        container?.removeView(obj as View)
    }
}
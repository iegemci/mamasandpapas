package com.enesgemci.mamasandpapas.ui.detail

import android.content.res.ColorStateList
import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v4.view.PagerAdapter
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.Html
import android.text.TextWatcher
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.enesgemci.mamasandpapas.BuildConfig
import com.enesgemci.mamasandpapas.R
import com.enesgemci.mamasandpapas.adapter.AdapterProductSize
import com.enesgemci.mamasandpapas.base.BaseFragment
import com.enesgemci.mamasandpapas.core.util.extensions.dpToPx
import com.enesgemci.mamasandpapas.core.util.extensions.hide
import com.enesgemci.mamasandpapas.core.util.extensions.show
import com.enesgemci.mamasandpapas.core.util.extensions.toMoney
import com.enesgemci.mamasandpapas.dagger.component.FragmentComponent
import com.enesgemci.mamasandpapas.data.ConfigurableAttributeOptionModel
import com.enesgemci.mamasandpapas.data.ProductModel
import com.enesgemci.mamasandpapas.util.GlideApp
import com.enesgemci.mamasandpapas.util.MDrawable
import com.enesgemci.mamasandpapas.util.extensions.setMColorFilter
import com.enesgemci.mamasandpapas.util.fragment.Page
import kotlinx.android.synthetic.main.fragment_product_detail.*
import javax.inject.Inject


/**
 * Created by enesgemci on 06/10/2017.
 */
internal class FragmentProductDetail : BaseFragment<FragmentProductDetailView, FragmentProductDetailPresenter>(), FragmentProductDetailView {

    @Inject
    lateinit var mPresenter: FragmentProductDetailPresenter

    private var product: ProductModel? = null
        set(value) {
            field = value
            presenter.onFragmentStarted(product)
            setData()
            maxQuantity = value?.stock?.maxAvailableQty ?: 0
        }

    override var sizeQuantities = HashMap<String, Int>()

    private var adapter: AdapterProductSize? = null
    override var products: ArrayList<ProductModel> = ArrayList()
    private var selectedSize: ConfigurableAttributeOptionModel? = null

    private var maxQuantity: Int = 0
        set(value) {
            field = value
            if (value < 1) {
                fab.enabled(false)
            }

            var currentQUantity = quantityTv.text.toString().toInt()
            adapter?.disabledSizes(sizeQuantities.filterValues { it < currentQUantity }.keys)
        }

    override fun createPresenter(): FragmentProductDetailPresenter {
        return mPresenter
    }

    override fun onFragmentStarted() {
        setup()
        arguments?.let { product = arguments.getParcelable(PRODUCT) }
    }

    private fun setup() {
        fab.setMColorFilter(baseActivity.getCompatColor(R.color.white))

        sizeList.layoutManager = GridLayoutManager(context, 3)
        sizeList.addItemDecoration(GridSpacingItemDecoration(3, 10.dpToPx(context), false))

        quantityArea.background = MDrawable.Builder(context)
                .setBorderColorResId(R.color.text_color_light)
                .addType(MDrawable.Type.BORDER)
                .setRadius(5f.dpToPx(context))
                .build()

        fab.setOnClickListener { Snackbar.make(fab, "Product added to basket", Snackbar.LENGTH_SHORT).show() }
        decreaseTv.setOnClickListener {
            var currentQUantity = quantityTv.text.toString().toInt()

            if (currentQUantity > 1) {
                quantityTv.text = "${currentQUantity - 1}"
            }
        }
        increaseTv.setOnClickListener {
            var currentQUantity = quantityTv.text.toString().toInt()

            if (currentQUantity <= maxQuantity) {
                quantityTv.text = "${currentQUantity + 1}"
            }
        }
        quantityTv.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable?) {
                var currentQUantity = quantityTv.text.toString().toInt()
                increaseTv.isEnabled = currentQUantity != maxQuantity
                decreaseTv.isEnabled = currentQUantity > 1

                adapter?.disabledSizes(sizeQuantities.filterValues { it < s.toString().toInt() }.keys)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
    }

    private fun setData() {
        viewPager.adapter = MPagerAdapter()

        if (viewPager.adapter.count > 1) {
            indicator.setViewPager(viewPager)
        }

        if (!product!!.isAreAnyOptionsInStock) {
            fab.enabled(false)
        }

        descriptionText.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(product?.description, Html.FROM_HTML_MODE_COMPACT)
        } else {
            Html.fromHtml(product?.description)
        }

        nameTv.text = product!!.name ?: ""
        priceTv.text = "AED " + product?.price!!.toMoney()

        if (product!!.sizesInStock!!.size == 1 && product!!.sizesInStock!![0] == "N/A") {
            sizeLabel.hide()
            sizeList.hide()
            fab.enabled(true)
        } else {
            sizeLabel.show()
            sizeList.show()
            var list = product!!.sizesInStock!!.filter { it != "N/A" }.sortedWith(compareBy({ it }))
            adapter = AdapterProductSize(
                    context,
                    selectedSize,
                    list,
                    View.OnClickListener { v ->
                        var position = v.tag as Int

                        if (position >= 0) {
                            if (product!!.configurableAttributes != null) {
                                product!!.configurableAttributes?.forEach {
                                    selectedSize = it.options!!.firstOrNull { it.label == list[position] }

                                    if (selectedSize != null) {
                                        return@forEach
                                    }
                                }

                                selectedSize?.let {
                                    decreaseTv.isEnabled = true

                                    var option: ConfigurableAttributeOptionModel? = null
                                    products.forEach {
                                        it.configurableAttributes!!.find {
                                            option = it.options!!.first { it.optionId == selectedSize!!.optionId }
                                            return@forEach
                                        }
                                    }

                                    var pr = products.first { it.sku == option!!.simpleProductSkus.first() }
                                    maxQuantity = pr.stock!!.maxAvailableQty

                                    fab.enabled(it.isInStock)
                                    increaseTv.isEnabled = it.isInStock && quantityTv.text.toString().toInt() < maxQuantity

                                    it.simpleProductSkus?.let {
                                        if (it.isNotEmpty()) {
//                                            presenter.updateProduct(it.first())
                                        }
                                    }
                                }
                            } else {
                                fab.enabled(true)
                            }
                        } else {
                            fab.enabled(false)
                            selectedSize = null
                            decreaseTv.isEnabled = false
                        }
                    })
            sizeList.adapter = adapter
            sizeList.setHasFixedSize(false)

            if (list.size > 3) {
                sizeList.minimumHeight = if (list.size % 3 == 0) {
                    (list.size / 3 * 50.dpToPx(context)) + (2 * 10.dpToPx(context))
                } else {
                    ((((list.size.toDouble() / 3.0) + 1) * 50.dpToPx(context)) + 10.dpToPx(context)).toInt()
                }
            }
        }
    }

    override fun getPage(): Page {
        return Page.PAGE_PRODUCT_DETAIL
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_product_detail
    }

    override fun injectFragment(component: FragmentComponent) {
        component.inject(this)
    }

    private inner class MPagerAdapter : PagerAdapter() {

        override fun isViewFromObject(view: View?, obj: Any?): Boolean {
            return view == obj
        }

        override fun getCount(): Int {
            return product!!.media!!.size
        }

        override fun instantiateItem(container: ViewGroup?, position: Int): Any {
            var imageView = layoutInflater.inflate(R.layout.layout_product_image, null) as ImageView
            GlideApp.with(this@FragmentProductDetail)
                    .asDrawable()
                    .placeholder(R.drawable.bg_placeholder)
                    .load(BuildConfig.HOST_IMAGE + product!!.media!![position]!!.src)
                    .timeout(30000)
                    .into(imageView)
            container?.addView(imageView)

            return imageView
        }

        override fun destroyItem(container: ViewGroup?, position: Int, obj: Any?) {
            container?.removeView(obj as View)
        }
    }

    private fun FloatingActionButton.enabled(value: Boolean) {
        this.isEnabled = value
        increaseTv.isEnabled = value

        if (value) {
            this.backgroundTintList = ColorStateList.valueOf(baseActivity.getCompatColor(R.color.colorAccent))
        } else {
            this.backgroundTintList = ColorStateList.valueOf(baseActivity.getCompatColor(R.color.text_color_light))
        }
    }

    internal inner class GridSpacingItemDecoration(private val spanCount: Int, private val spacing: Int, private val includeEdge: Boolean) : RecyclerView.ItemDecoration() {

        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
            val position = parent.getChildAdapterPosition(view) // item position
            val column = position % spanCount // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing
                }
                outRect.bottom = spacing // item bottom
            } else {
                outRect.left = column * spacing / spanCount // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing // item top
                }
            }
        }
    }

    companion object {

        private val PRODUCT = "PRODUCT"

        fun newInstance(vararg objects: Any?): FragmentProductDetail {
            val args = Bundle()

            objects?.let { args.putParcelable(PRODUCT, objects[0] as Parcelable) }

            val fragment = FragmentProductDetail()
            fragment.arguments = args
            return fragment
        }
    }
}
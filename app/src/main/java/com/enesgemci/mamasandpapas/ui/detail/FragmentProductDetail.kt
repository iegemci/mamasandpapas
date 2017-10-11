package com.enesgemci.mamasandpapas.ui.detail

import android.content.res.ColorStateList
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.widget.GridLayoutManager
import android.text.Html
import android.view.View
import com.enesgemci.mamasandpapas.R
import com.enesgemci.mamasandpapas.adapter.AdapterProductSize
import com.enesgemci.mamasandpapas.adapter.MPagerAdapter
import com.enesgemci.mamasandpapas.base.BaseFragment
import com.enesgemci.mamasandpapas.core.util.extensions.dpToPx
import com.enesgemci.mamasandpapas.core.util.extensions.hide
import com.enesgemci.mamasandpapas.core.util.extensions.show
import com.enesgemci.mamasandpapas.core.util.extensions.toMoney
import com.enesgemci.mamasandpapas.dagger.component.FragmentComponent
import com.enesgemci.mamasandpapas.data.ConfigurableAttributeOptionModel
import com.enesgemci.mamasandpapas.data.ProductModel
import com.enesgemci.mamasandpapas.util.GridSpacingItemDecoration
import com.enesgemci.mamasandpapas.util.extensions.setMColorFilter
import com.enesgemci.mamasandpapas.util.fragment.Page
import com.enesgemci.mamasandpapas.widget.MCountView
import kotlinx.android.synthetic.main.fragment_product_detail.*
import javax.inject.Inject


/**
 * Created by enesgemci on 06/10/2017.
 */
internal class FragmentProductDetail : BaseFragment<FragmentProductDetailView, FragmentProductDetailPresenter>(), FragmentProductDetailView {

    @Inject
    lateinit var mPresenter: FragmentProductDetailPresenter

    override var product: ProductModel? = null
        set(value) {
            field = value
            presenter.onFragmentStarted(product)
            fillUI()
            maxQuantity = value?.stock?.maxAvailableQty ?: 0
        }

    override var sizeQuantities = HashMap<String, Int>()

    override var products: ArrayList<ProductModel> = ArrayList()

    override var selectedSize: ConfigurableAttributeOptionModel? = null
        set(value) {
            field = value

            fab.enabled(value != null)
        }

    override val sortedList: List<String>
        get() {
            var list = ArrayList(product!!.sizesInStock!!.filter { it != "N/A" })
            disabledList?.let { list.addAll(disabledList!!) }
            return list.sortedWith(compareBy({ it }))
        }

    override var maxQuantity: Int = 0
        set(value) {
            field = value

            counterView.maxCount = value

            if (value < 1) {
                fab.enabled(false)
            }

            adapter?.disabledSizes(sizeQuantities.filterValues { it < currentQuantity }.keys)
        }

    private var currentQuantity: Int = 1
        get() = counterView.currentQuantity

    private val disabledList: ArrayList<String>?
        get() {
            var list = product!!.configurableAttributes?.first { it.code == "sizeCode" }?.options?.filterNot { it.isInStock }?.map { it.label }
            return list?.let { ArrayList(list) } ?: ArrayList()
        }

    private var adapter: AdapterProductSize? = null

    override fun onFragmentStarted() {
        setup()
        arguments?.let { product = arguments.getParcelable(PRODUCT) }
    }

    override fun createPresenter(): FragmentProductDetailPresenter {
        return mPresenter
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

    private fun setup() {
        fab.setMColorFilter(baseActivity.getCompatColor(R.color.white))

        sizeList.layoutManager = GridLayoutManager(context, 3)
        sizeList.addItemDecoration(GridSpacingItemDecoration(3, 10.dpToPx(context), false))

        fab.setOnClickListener { Snackbar.make(fab, "Product added to basket", Snackbar.LENGTH_SHORT).show() }

        counterView.onQuantityChangedListener = object : MCountView.OnQuantityChangedListener {

            override fun onQuantityChanged(quantity: Int) {
                adapter?.disabledSizes(sizeQuantities.filterValues { it < quantity }.keys)
            }
        }
    }

    private fun fillUI() {
        viewPager.adapter = MPagerAdapter(context, product?.media)

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
        priceTv.text = "AED " + product?.price.toMoney()

        if (product!!.sizesInStock!!.size == 1 && product!!.sizesInStock!![0] == "N/A") {
            sizeLabel.hide()
            sizeList.hide()
            fab.enabled(true)
        } else {
            sizeLabel.show()
            sizeList.show()

            adapter = AdapterProductSize(
                    context,
                    selectedSize,
                    sortedList,
                    disabledList ?: ArrayList(),
                    View.OnClickListener { v ->
                        presenter.onSizeSelected(v.tag as Int)
                    })

            sizeList.adapter = adapter
            sizeList.setHasFixedSize(false)

            // UI related
            if (sortedList.size > 3) {
                sizeList.minimumHeight = if (sortedList.size % 3 == 0) {
                    (sortedList.size / 3 * 50.dpToPx(context)) + (2 * 10.dpToPx(context))
                } else {
                    ((((sortedList.size.toDouble() / 3.0) + 1) * 50.dpToPx(context)) + 10.dpToPx(context)).toInt()
                }
            }
        }
    }

    override fun addToBasketEnabled(value: Boolean) {
        fab.enabled(value)
    }

    private fun FloatingActionButton.enabled(value: Boolean) {
        this.isEnabled = value
        counterView.isEnabled = value

        if (value) {
            this.backgroundTintList = ColorStateList.valueOf(baseActivity.getCompatColor(R.color.colorAccent))
        } else {
            this.backgroundTintList = ColorStateList.valueOf(baseActivity.getCompatColor(R.color.text_color_light))
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
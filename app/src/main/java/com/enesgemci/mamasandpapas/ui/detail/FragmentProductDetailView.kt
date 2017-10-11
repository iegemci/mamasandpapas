package com.enesgemci.mamasandpapas.ui.detail

import com.enesgemci.mamasandpapas.base.mvp.BaseMvpView
import com.enesgemci.mamasandpapas.data.ConfigurableAttributeOptionModel
import com.enesgemci.mamasandpapas.data.ProductModel

/**
 * Created by enesgemci on 06/10/2017.
 */
internal interface FragmentProductDetailView : BaseMvpView {

    var product: ProductModel?

    var products: ArrayList<ProductModel>

    var sizeQuantities: HashMap<String, Int>

    var maxQuantity: Int

    var selectedSize: ConfigurableAttributeOptionModel?

    val sortedList: List<String>

    fun addToBasketEnabled(value: Boolean)
}
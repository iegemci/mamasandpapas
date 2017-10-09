package com.enesgemci.mamasandpapas.ui.detail

import com.enesgemci.mamasandpapas.base.mvp.BaseMvpView
import com.enesgemci.mamasandpapas.data.ProductModel

/**
 * Created by enesgemci on 06/10/2017.
 */
internal interface FragmentProductDetailView : BaseMvpView {

    var products: ArrayList<ProductModel>

    var sizeQuantities: HashMap<String, Int>
}
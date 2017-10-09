package com.enesgemci.mamasandpapas.ui.detail

import com.enesgemci.mamasandpapas.base.mvp.BaseMvpPresenter
import com.enesgemci.mamasandpapas.data.ProductModel
import com.enesgemci.mamasandpapas.network.MRequestGenerator
import com.squareup.otto.Subscribe
import javax.inject.Inject

/**
 * Created by enesgemci on 06/10/2017.
 */
internal class FragmentProductDetailPresenter @Inject constructor() : BaseMvpPresenter<FragmentProductDetailView>() {

    @Inject
    lateinit var requestGenerator: MRequestGenerator

    @Subscribe
    fun onResponse(product: ProductModel) {
        if (isViewAttached) {
            view.products.add(product)

            product.configurableAttributes?.forEach {
                it.options?.forEach {
                    if (product.sku == it.simpleProductSkus.first()) {
                        view.sizeQuantities.put(it.label, product.stock!!.maxAvailableQty)
                        return
                    }
                }
            }
        }
    }

    fun onFragmentStarted(product: ProductModel?) {
        product?.configurableAttributes?.forEach {
            it.options?.forEach {
                it.simpleProductSkus.forEach {
                    view.sendRequest(requestGenerator.getProductDetailRequest(it))
                }
            }
        }
    }
}
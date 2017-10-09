package com.enesgemci.mamasandpapas.ui.list

import com.enesgemci.mamasandpapas.base.mvp.BaseMvpPresenter
import com.enesgemci.mamasandpapas.data.ProductListResponse
import com.enesgemci.mamasandpapas.data.ProductModel
import com.enesgemci.mamasandpapas.network.MRequestGenerator
import com.enesgemci.mamasandpapas.util.fragment.Page
import com.squareup.otto.Subscribe
import javax.inject.Inject

/**
 * Created by enesgemci on 06/10/2017.
 */
internal class FragmentProductListPresenter @Inject constructor() : BaseMvpPresenter<FragmentProductListView>() {

    private var response: ProductListResponse? = null

    @Inject
    lateinit var requestGenerator: MRequestGenerator

    var page = 0
    private var hitsPerPage = 10

    @Subscribe
    fun onResponse(response: ProductListResponse) {
        if (isViewAttached) {
            this.response = response
            view.setResponse(response)
        }
    }

    fun onItemClicked(product: ProductModel) {
        view.showPage(Page.PAGE_PRODUCT_DETAIL, product)
    }

    fun loadMore() {
        view.sendRequest(requestGenerator.getProductListRequest(page = page++, hitsPerPage = hitsPerPage).setBehind(true))
    }
}
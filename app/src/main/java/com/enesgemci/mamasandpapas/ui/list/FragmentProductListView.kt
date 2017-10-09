package com.enesgemci.mamasandpapas.ui.list

import com.enesgemci.mamasandpapas.base.mvp.BaseMvpView
import com.enesgemci.mamasandpapas.data.ProductListResponse

/**
 * Created by enesgemci on 06/10/2017.
 */
internal interface FragmentProductListView : BaseMvpView {

    fun setResponse(response: ProductListResponse)
}
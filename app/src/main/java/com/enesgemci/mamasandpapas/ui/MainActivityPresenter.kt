package com.enesgemci.mamasandpapas.ui

import com.enesgemci.mamasandpapas.base.mvp.BaseMvpPresenter
import com.enesgemci.mamasandpapas.util.fragment.Page
import javax.inject.Inject

/**
 * Created by enesgemci on 06/10/2017.
 */
internal class MainActivityPresenter @Inject constructor() : BaseMvpPresenter<MainActivityView>() {

    fun onActivityStarted() {
        view.showPage(Page.PAGE_PRODUCT_LIST)
    }
}
package com.enesgemci.mamasandpapas.ui.detail

import android.os.Bundle
import android.os.Parcelable
import com.enesgemci.mamasandpapas.R
import com.enesgemci.mamasandpapas.base.BaseFragment
import com.enesgemci.mamasandpapas.dagger.component.FragmentComponent
import com.enesgemci.mamasandpapas.util.fragment.Page
import javax.inject.Inject

/**
 * Created by enesgemci on 06/10/2017.
 */
internal class FragmentProductDetail : BaseFragment<FragmentProductDetailView, FragmentProductDetailPresenter>(), FragmentProductDetailView {

    @Inject
    lateinit var mPresenter: FragmentProductDetailPresenter

    override fun createPresenter(): FragmentProductDetailPresenter {
        return mPresenter
    }

    override fun onFragmentStarted() {

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

    companion object {

        fun newInstance(vararg objects: Any?): FragmentProductDetail {
            val args = Bundle()

            objects?.let { args.putParcelable(PRODUCT, objects[0] as Parcelable) }

            val fragment = FragmentProductDetail()
            fragment.arguments = args
            return fragment
        }
    }
}
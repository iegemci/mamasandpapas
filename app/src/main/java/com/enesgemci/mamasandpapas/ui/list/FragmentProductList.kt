package com.enesgemci.mamasandpapas.ui.list

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.enesgemci.mamasandpapas.R
import com.enesgemci.mamasandpapas.adapter.AdapterProductList
import com.enesgemci.mamasandpapas.base.BaseFragment
import com.enesgemci.mamasandpapas.dagger.component.FragmentComponent
import com.enesgemci.mamasandpapas.data.ProductListResponse
import com.enesgemci.mamasandpapas.util.fragment.Page
import kotlinx.android.synthetic.main.fragment_product_list.*
import javax.inject.Inject

/**
 * Created by enesgemci on 06/10/2017.
 */
internal class FragmentProductList : BaseFragment<FragmentProductListView, FragmentProductListPresenter>(), FragmentProductListView {

    override val searchString: String
        get() = searchEditText.text.toString()

    @Inject
    lateinit var mPresenter: FragmentProductListPresenter

    private var adapter: AdapterProductList? = null

    override fun getPage(): Page {
        return Page.PAGE_PRODUCT_LIST
    }

    override fun createPresenter(): FragmentProductListPresenter {
        return mPresenter
    }

    override fun onFragmentStarted() {
        productsRecyclerView.layoutManager = LinearLayoutManager(context)
        productsRecyclerView.setHasFixedSize(false)

        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                s?.let { if (s.length > 2) presenter.refresh() else productsRecyclerView.adapter = null }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
    }

    override fun setResponse(response: ProductListResponse) {
        response.products?.let {
            adapter = AdapterProductList(context, response.products!!, View.OnClickListener { v ->
                presenter.onItemClicked(v.tag as Int)
            })
        }
        productsRecyclerView.adapter = adapter
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_product_list
    }

    override fun injectFragment(component: FragmentComponent) {
        component.inject(this)
    }

    companion object {

        fun newInstance(vararg objects: Any?): FragmentProductList {
            val args = Bundle()

            val fragment = FragmentProductList()
            fragment.arguments = args
            return fragment
        }
    }
}
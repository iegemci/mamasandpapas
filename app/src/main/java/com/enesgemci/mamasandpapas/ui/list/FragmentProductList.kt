package com.enesgemci.mamasandpapas.ui.list

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import com.enesgemci.mamasandpapas.R
import com.enesgemci.mamasandpapas.adapter.AdapterProductList
import com.enesgemci.mamasandpapas.base.BaseFragment
import com.enesgemci.mamasandpapas.dagger.component.FragmentComponent
import com.enesgemci.mamasandpapas.data.ProductListResponse
import com.enesgemci.mamasandpapas.util.fragment.Page
import com.enesgemci.mamasandpapas.widgets.endlessrecyclerview.Endless
import kotlinx.android.synthetic.main.fragment_product_list.*
import timber.log.Timber
import javax.inject.Inject


/**
 * Created by enesgemci on 06/10/2017.
 */
internal class FragmentProductList : BaseFragment<FragmentProductListView, FragmentProductListPresenter>(), FragmentProductListView {

    override val searchString: String
        get() = searchEditText.text.toString()

    val ITEM_PER_PAGE = 10

    @Inject
    lateinit var mPresenter: FragmentProductListPresenter

    private var adapter: AdapterProductList? = null
    private var endless: Endless? = null
    private var addAll: Boolean = true

    override fun getPage(): Page {
        return Page.PAGE_PRODUCT_LIST
    }

    override fun createPresenter(): FragmentProductListPresenter {
        return mPresenter
    }

    override fun onFragmentStarted() {
        val layoutManager: RecyclerView.LayoutManager

        if (isTablet()) {
            layoutManager = GridLayoutManager(context, 2)
        } else {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }

        productsRecyclerView.layoutManager = layoutManager
        productsRecyclerView.setHasFixedSize(false)

        val progressBar = View.inflate(context, R.layout.layout_loader_item, null) as ProgressBar
        val layoutParams = RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        progressBar.layoutParams = layoutParams

        this.adapter = AdapterProductList(context, View.OnClickListener { v ->
            presenter.onItemClicked(v.tag as Int)
        })

        endless = Endless.applyTo(productsRecyclerView, progressBar)
        endless!!.setAdapter(adapter)
        endless!!.setLoadMoreListener(object : Endless.LoadMoreListener {
            override fun onLoadMore(page: Int) {
                presenter.loadMore()
            }
        })

        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                addAll = false
                s?.let { if (s.length > 2) presenter.loadMore() else productsRecyclerView.adapter = null }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
    }

    override fun setResponse(response: ProductListResponse) {
        response.products?.let {
            endless!!.loadMoreComplete()
            adapter!!.loadData(response.products, addAll)
            endless!!.isLoadMoreAvailable = response.pagination!!.totalPages != presenter.page

            Timber.e("isLoadMoreAvailable : ${endless!!.isLoadMoreAvailable}")
        }

        addAll = true
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

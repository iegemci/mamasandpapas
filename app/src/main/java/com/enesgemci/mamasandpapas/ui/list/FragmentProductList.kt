package com.enesgemci.mamasandpapas.ui.list

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import com.enesgemci.mamasandpapas.R
import com.enesgemci.mamasandpapas.adapter.AdapterProductList
import com.enesgemci.mamasandpapas.base.BaseFragment
import com.enesgemci.mamasandpapas.dagger.component.FragmentComponent
import com.enesgemci.mamasandpapas.data.ProductListResponse
import com.enesgemci.mamasandpapas.data.ProductModel
import com.enesgemci.mamasandpapas.util.fragment.Page
import com.github.yasevich.endlessrecyclerview.EndlessRecyclerView
import kotlinx.android.synthetic.main.fragment_product_list.*
import javax.inject.Inject


/**
 * Created by enesgemci on 06/10/2017.
 */
internal class FragmentProductList : BaseFragment<FragmentProductListView, FragmentProductListPresenter>(), FragmentProductListView, EndlessRecyclerView.Pager {

    @Inject
    lateinit var mPresenter: FragmentProductListPresenter

    private val adapter: AdapterProductList by lazy {
        AdapterProductList(context, View.OnClickListener { v ->
            loading = false
            swipeRefreshView.isRefreshing = false

            var product = v.tag as ProductModel
            selectedPosition = adapter.productList.indexOf(product)
            presenter.onItemClicked(product)
        })
    }

    private var selectedPosition = 0
    private var clear: Boolean = false
    private var loading: Boolean = false
        set(value) {
            field = value
            productsRecyclerView.isRefreshing = value
        }
    private var response: ProductListResponse? = null

    override fun getPage(): Page {
        return Page.PAGE_PRODUCT_LIST
    }

    override fun createPresenter(): FragmentProductListPresenter {
        return mPresenter
    }

    override fun onFragmentStarted() {
        swipeRefreshView.setOnRefreshListener {
            swipeRefreshView.isRefreshing = true
            presenter.page = 0
            adapter?.loadData(null)
            clear = true
            presenter.loadMore()
        }

        val layoutManager: RecyclerView.LayoutManager = if (isTablet()) {
            GridLayoutManager(context, 2)
        } else {
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }

        productsRecyclerView.layoutManager = layoutManager
        productsRecyclerView.setHasFixedSize(false)

        val progressBar = View.inflate(context, R.layout.layout_loader_item, null) as ProgressBar
        val layoutParams = RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        progressBar.layoutParams = layoutParams

        productsRecyclerView.setProgressView(progressBar)
        productsRecyclerView.adapter = adapter
        productsRecyclerView.setPager(this)

        if (adapter.itemCount == 0) {
            swipeRefreshView.isRefreshing = true
            presenter.loadMore()
        } else {
            setItemCount()
            productsRecyclerView.scrollToPosition(selectedPosition)
        }
    }

    private fun setItemCount() {
        countTextView.text = "${adapter.itemCount} out of ${response!!.pagination!!.totalHits}"
    }

    override fun loadNextPage() {
        if (!clear) {
            loading = true
            presenter.loadMore()
        }
    }

    override fun shouldLoad(): Boolean {
        return !loading && response!!.pagination!!.totalPages != presenter.page
    }

    override fun setResponse(response: ProductListResponse) {
        loading = false
        swipeRefreshView.isRefreshing = false
        this.response = response

        if (clear) {
            adapter.loadData(null)
        }

        response.products?.let {
            adapter.loadData(response.products)
            setItemCount()
        }

        clear = false
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

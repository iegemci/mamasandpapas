package com.enesgemci.mamasandpapas.widgets.endlessrecyclerview

import android.support.v7.widget.RecyclerView
import android.view.View
import java.lang.ref.WeakReference
import java.util.*

class Endless private constructor(val recyclerView: RecyclerView, private val loadMoreView: View) {

    private val listener: EndlessScrollListener? = null

    var isLoadMoreAvailable = true
    private var loadMoreListener: LoadMoreListener? = null
    private var mAdapter: EndlessAdapter<*>? = null

    init {

        val adapter = recyclerView.adapter

        if (adapter !is EndlessAdapter<*>) {
            setAdapter(adapter)
        }

        recyclerView.addOnScrollListener(object : EndlessScrollListener() {
            override fun onLoadMore(currentPage: Int) {
                recyclerView.post {
                    if (isLoadMoreAvailable && loadMoreListener != null && !mAdapter!!.isLoading) {
                        mAdapter!!.isLoading = true
                        loadMoreListener!!.onLoadMore(currentPage)
                    }
                }

            }
        })

    }

    fun setLoadMoreListener(loadMoreListener: LoadMoreListener) {
        this.loadMoreListener = loadMoreListener
    }


    fun loadMoreComplete() {
        mAdapter?.isLoading = false
        listener?.isLoading = false
    }


    fun setAdapter(adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>?) {
        if (adapter == null) {
            return
        }

        if (adapter is EndlessAdapter<*>) {
            recyclerView.adapter = adapter
        }

        recyclerView.adapter = EndlessAdapter.wrap(adapter, loadMoreView)
        mAdapter = recyclerView.adapter as EndlessAdapter<*>
    }

    interface LoadMoreListener {
        fun onLoadMore(page: Int)
    }

    companion object {
        private var mLoadMoreEntries: ArrayList<WeakReference<Endless>>? = null

        fun remove(recyclerView: RecyclerView) {
            if (mLoadMoreEntries != null) {
                var i = 0
                while (i < mLoadMoreEntries!!.size) {
                    val weakReference = mLoadMoreEntries!![i]
                    val endless = weakReference.get()
                    if (endless?.recyclerView == null || endless.recyclerView == recyclerView) {
                        mLoadMoreEntries!!.removeAt(i)
                        i--
                    }
                    i++
                }
            }
        }

        fun applyTo(recyclerView: RecyclerView, loadMoreView: View): Endless {
            var endless: Endless?
            if (mLoadMoreEntries == null) {
                mLoadMoreEntries = ArrayList()
            } else {
                var i = 0
                while (i < mLoadMoreEntries!!.size) {
                    val weakReference = mLoadMoreEntries!![i]
                    endless = weakReference.get()
                    if (endless == null || endless.recyclerView == null) {
                        mLoadMoreEntries!!.removeAt(i)
                        i--
                    } else if (endless.recyclerView == recyclerView) {
                        return endless
                    }
                    i++
                }
            }
            endless = Endless(recyclerView, loadMoreView)
            mLoadMoreEntries!!.add(WeakReference(endless))
            val adapter = recyclerView.adapter
            if (adapter != null) {
                endless.setAdapter(adapter)
            }
            return endless
        }
    }
}
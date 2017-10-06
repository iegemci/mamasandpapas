package com.enesgemci.mamasandpapas.widgets.endlessrecyclerview

import android.support.v7.widget.RecyclerView

/**
 * https://gist.github.com/mipreamble/b6d4b3d65b0b4775a22e
 */

abstract class EndlessScrollListener : RecyclerView.OnScrollListener() {

    private val previousTotal = 0 // The total number of items in the dataset after the last load
    var isLoading: Boolean = false // True if we are still waiting for the last set of data to load.
    private val visibleThreshold = 2 // The minimum amount of items to have below your current scroll position before loading more.
    internal var firstVisibleItem: Int = 0
    internal var visibleItemCount: Int = 0
    internal var totalItemCount: Int = 0

    private var currentPage = 1

    internal var mRecyclerViewHelper: RecyclerViewPositionHelper? = null

    override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        mRecyclerViewHelper = RecyclerViewPositionHelper.createHelper(recyclerView)
        visibleItemCount = recyclerView!!.childCount
        totalItemCount = mRecyclerViewHelper!!.itemCount
        firstVisibleItem = mRecyclerViewHelper!!.findFirstVisibleItemPosition()

        //        if (loading) {
        //            if (totalItemCount > previousTotal) {
        //                loading = false;
        //                previousTotal = totalItemCount;
        //            }
        //        }
        if (!isLoading && totalItemCount - visibleItemCount <= firstVisibleItem + visibleThreshold) {
            // End has been reached
            // Do something
            currentPage++
            isLoading = true
            onLoadMore(currentPage)
        }
    }

    abstract fun onLoadMore(currentPage: Int)
}
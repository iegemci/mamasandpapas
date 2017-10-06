package com.enesgemci.mamasandpapas.widgets.endlessrecyclerview

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup

abstract class EndlessAdapter<LVH : RecyclerView.ViewHolder>(private val loadMoreView: View) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TYPE_LOAD_MORE = 101
    var isLoading: Boolean = false
        set(loading) {
            field = loading
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (isLoading && viewType == TYPE_LOAD_MORE) {
            LoadMoreHolder(loadMoreView)
        } else onCreateHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (isLoading && getItemViewType(position) == TYPE_LOAD_MORE) {
        } else {
            onBindHolder(holder as LVH, position)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (isLoading && position == itemCount - 1) {
            TYPE_LOAD_MORE
        } else getViewType(position)
    }

    override fun getItemCount(): Int {

        return if (isLoading) {
            count + 1
        } else {
            count
        }
    }

    abstract fun getViewType(position: Int): Int

    abstract val count: Int

    abstract fun onCreateHolder(parent: ViewGroup, viewType: Int): LVH

    abstract fun onBindHolder(holder: LVH, position: Int)

    internal inner class LoadMoreHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    companion object {

        fun wrap(adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>, loadMoreView: View): EndlessAdapter<*> {
            return adapter as? EndlessAdapter<RecyclerView.ViewHolder> ?: object : EndlessAdapter<RecyclerView.ViewHolder>(loadMoreView) {

                override fun getViewType(position: Int): Int {
                    return adapter.getItemViewType(position)
                }

                override val count: Int
                    get() = adapter.itemCount

                override fun onCreateHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
                    return adapter.onCreateViewHolder(parent, viewType)
                }

                override fun onBindHolder(holder: RecyclerView.ViewHolder, position: Int) {
                    adapter.onBindViewHolder(holder, position)
                }

                override fun getItemId(position: Int): Long {
                    return adapter.getItemId(position)
                }


                override fun onDetachedFromRecyclerView(recyclerView: RecyclerView?) {
                    adapter.onDetachedFromRecyclerView(recyclerView)
                }

                override fun registerAdapterDataObserver(observer: RecyclerView.AdapterDataObserver) {
                    super.registerAdapterDataObserver(observer)
                    adapter.registerAdapterDataObserver(observer)
                }

                override fun unregisterAdapterDataObserver(observer: RecyclerView.AdapterDataObserver) {
                    super.unregisterAdapterDataObserver(observer)
                    adapter.unregisterAdapterDataObserver(observer)
                }

                override fun setHasStableIds(hasStableIds: Boolean) {
                    super.setHasStableIds(hasStableIds)
                    adapter.setHasStableIds(hasStableIds)
                }

                override fun onViewRecycled(holder: RecyclerView.ViewHolder?) {
                    super.onViewRecycled(holder)
                    adapter.onViewRecycled(holder)
                }

                override fun onFailedToRecycleView(holder: RecyclerView.ViewHolder?): Boolean {
                    return adapter.onFailedToRecycleView(holder)
                }

                override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder?) {
                    adapter.onViewAttachedToWindow(holder)
                }

                override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder?) {
                    adapter.onViewDetachedFromWindow(holder)
                }

                override fun onAttachedToRecyclerView(recyclerView: RecyclerView?) {
                    adapter.onAttachedToRecyclerView(recyclerView)
                }
            }

        }
    }
}
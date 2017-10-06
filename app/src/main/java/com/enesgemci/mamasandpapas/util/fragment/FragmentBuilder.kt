/*
 * Copyright (c) 2016.
 *
 * Enes Gemci
 */

package com.enesgemci.mamasandpapas.util.fragment

import android.os.Parcel
import android.os.Parcelable
import android.support.v4.app.FragmentManager
import com.enesgemci.mamasandpapas.base.BaseFragment
import java.io.Serializable

/**
 * Created by emesgemci on 09/09/15.
 */
internal class FragmentBuilder : Serializable, Parcelable {

    @Transient
    var fragment: BaseFragment<*, *>? = null
        private set

    private
    var mTransactionType: FragmentTransactionType = FragmentTransactionType.REPLACE

    private var mContainerId = -1

    private var mAddToBackStack = false

    private var transactionAnimation: TransactionAnimation = TransactionAnimation.ENTER_FROM_RIGHT

    var pageType: PageType = PageType.NORMAL
        private set

    @Transient private var fragmentManager: FragmentManager? = null

    private var mClearBackStack = false

    var isUseDialogContainer = true

    constructor()

    fun setFragment(fragment: BaseFragment<*, *>): FragmentBuilder {
        this.fragment = fragment
        pageType = PageType.NORMAL
        return this
    }

    fun setAddToBackStack(addToBackStack: Boolean): FragmentBuilder {
        this.mAddToBackStack = addToBackStack
        return this
    }

    fun setTransactionType(transactionType: FragmentTransactionType): FragmentBuilder {
        this.mTransactionType = transactionType
        return this
    }

    fun setContainerId(mContainerId: Int): FragmentBuilder {
        this.mContainerId = mContainerId
        return this
    }

    val isSettedContainer: Boolean
        get() = mContainerId != -1

    fun getContainerId(): Int {
        return mContainerId
    }

    fun getTransactionType(): FragmentTransactionType {
        return mTransactionType
    }

    fun isAddToBackStack(): Boolean {
        return mAddToBackStack
    }

    fun getTransactionAnimation(): TransactionAnimation {
        return transactionAnimation
    }

    fun setTransactionAnimation(transactionAnimation: TransactionAnimation): FragmentBuilder {
        this.transactionAnimation = transactionAnimation
        return this
    }

    fun getFragmentManager(): FragmentManager? {
        return fragmentManager
    }

    fun setFragmentManager(fragmentManager: FragmentManager): FragmentBuilder {
        this.fragmentManager = fragmentManager
        return this
    }

    fun isClearBackStack(): Boolean {
        return mClearBackStack
    }

    fun setClearBackStack(mClearBackStack: Boolean): FragmentBuilder {
        this.mClearBackStack = mClearBackStack
        return this
    }

    constructor(source: Parcel) : this(
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {}

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<FragmentBuilder> = object : Parcelable.Creator<FragmentBuilder> {
            override fun createFromParcel(source: Parcel): FragmentBuilder = FragmentBuilder(source)
            override fun newArray(size: Int): Array<FragmentBuilder?> = arrayOfNulls(size)
        }
    }
}
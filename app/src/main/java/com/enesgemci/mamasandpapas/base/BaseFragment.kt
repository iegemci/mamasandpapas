/*
 * Copyright (c) 2016.
 *
 * Enes Gemci
 */

package com.enesgemci.mamasandpapas.base

import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import com.enesgemci.mamasandpapas.R
import com.enesgemci.mamasandpapas.base.mvp.BaseMvpPresenter
import com.enesgemci.mamasandpapas.base.mvp.BaseMvpView
import com.enesgemci.mamasandpapas.dagger.component.DaggerFragmentComponent
import com.enesgemci.mamasandpapas.dagger.component.FragmentComponent
import com.enesgemci.mamasandpapas.dagger.module.FragmentModule
import com.enesgemci.mamasandpapas.network.MRequest
import com.enesgemci.mamasandpapas.util.KeyboardUtils
import com.enesgemci.mamasandpapas.util.fragment.FragmentBuilder
import com.enesgemci.mamasandpapas.util.fragment.Page
import com.hannesdorfmann.mosby3.mvp.MvpFragment
import com.squareup.otto.Bus
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber
import java.util.*

/**
 * Created by enesgemci on 09/09/15.
 */
internal abstract class BaseFragment<V : BaseMvpView, P : BaseMvpPresenter<V>> : MvpFragment<V, P>(), BaseMvpView {

    lateinit protected var mContainer: ViewGroup

    private val fragmentComponent: FragmentComponent by lazy {
        DaggerFragmentComponent.builder()
                .fragmentModule(FragmentModule(this, childFragmentManager))
                .appComponent(MApp.appComponent)
                .build()
    }

    lateinit private var currentRequests: MutableList<MRequest<*>>
    private val disposables = CompositeDisposable()

    abstract fun onFragmentStarted()

    internal abstract fun getPage(): Page

    /**
     * @return Fragment'in layout xml id'si
     */
    abstract fun getLayoutId(): Int

    open fun getToolbarLayoutId(): Int {
        return -1
    }

    abstract fun injectFragment(component: FragmentComponent)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectFragment(fragmentComponent)
        currentRequests = ArrayList()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mContainer = inflater!!.inflate(getLayoutId(), container, false) as ViewGroup

        if (mContainer.background == null) {
            mContainer.setBackgroundColor(ActivityCompat.getColor(context, R.color.white))
        }

        return mContainer
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        if (!isTablet()) {
//            if (!supportsLandscape()) {
//                if (activity.requestedOrientation != ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT) {
//                    activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT
//                }
//            } else {
//                if (activity.requestedOrientation != ActivityInfo.SCREEN_ORIENTATION_USER) {
//                    activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_USER
//                }
//            }
//        }

        if (mContainer.viewTreeObserver.isAlive) {
            mContainer.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    mContainer.viewTreeObserver.removeOnGlobalLayoutListener(this)

                    if (activity != null && presenter.isViewAttached) {
                        fragmentStarted()
                    }
                }
            })
        } else {
            if (activity != null && presenter.isViewAttached) {
                fragmentStarted()
            }
        }
    }

    private fun fragmentStarted() {
        onFragmentStarted()
    }

    val bus: Bus
        get() = baseActivity.bus

    override fun sendRequest(request: MRequest<*>) {
        currentRequests.add(request)
        baseActivity.sendRequest(request)
    }

    val baseActivity: BaseActivity<*, *>
        get() = activity as BaseActivity<*, *>

    val lifeCycleEventData: Any?
        get() = null

    override fun onResume() {
        super.onResume()

        try {
            bus.register(getPresenter())
        } catch (e: Exception) {
            Timber.e(e.message)
        }

        try {
            bus.register(this)
        } catch (e: Exception) {
            Timber.e(e.message)
        }
    }

    override fun onDestroy() {
        try {
            bus.unregister(getPresenter())
        } catch (e: Exception) {
            Timber.e(e.message)
        }

        try {
            bus.unregister(this)
        } catch (e: Exception) {
            Timber.e(e.message)
        }

        super.onDestroy()
    }

    override fun onDestroyView() {
        KeyboardUtils.hide(baseActivity)
        disposables.clear()

        super.onDestroyView()
    }

    override fun showPage(page: Page, vararg obj: Any?) {
        val fragmentBuilder = getPage(page, *obj)
        showPage(fragmentBuilder)
    }

    override fun showPage(replacer: FragmentBuilder?) {
        baseActivity.showPage(replacer)
    }

    override fun getPage(page: Page, vararg obj: Any?): FragmentBuilder {
        return baseActivity.getPage(page, *obj)
    }

    fun stopServiceListener() {
        try {
            cancelRequests()
            bus.unregister(this)
        } catch (ex: Exception) {
            Timber.e(ex.message)
        }
    }

    private fun cancelRequests() {
        currentRequests
                .filter { request -> !request.isCanceled }
                .forEach { request ->
                    try {
                        request.cancel()
                        currentRequests.remove(request)
                    } catch (e: Exception) {
                        Timber.e(e)
                    }
                }
    }

    override fun isTablet(): Boolean {
        return resources.getBoolean(R.bool.is_tablet)
    }

    open fun showLoadingOnContainer(): Boolean {
        return false
    }

    open fun getAnalyticsTag(): String {
        return "Page Name"
    }
}
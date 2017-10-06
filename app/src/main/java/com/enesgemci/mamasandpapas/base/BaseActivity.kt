/*
 * Copyright (c) 2016.
 *
 * Enes Gemci
 */

package com.enesgemci.mamasandpapas.base

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.support.annotation.AnyThread
import android.support.annotation.UiThread
import android.support.constraint.ConstraintLayout
import android.support.v4.app.ActivityCompat
import android.support.v4.app.FragmentTransaction
import android.support.v7.widget.Toolbar
import android.view.ViewGroup
import android.view.ViewTreeObserver
import com.enesgemci.mamasandpapas.R
import com.enesgemci.mamasandpapas.base.mvp.BaseMvpPresenter
import com.enesgemci.mamasandpapas.base.mvp.BaseMvpView
import com.enesgemci.mamasandpapas.core.util.extensions.bindView
import com.enesgemci.mamasandpapas.dagger.component.ActivityComponent
import com.enesgemci.mamasandpapas.dagger.component.DaggerActivityComponent
import com.enesgemci.mamasandpapas.dagger.module.ActivityModule
import com.enesgemci.mamasandpapas.data.ErrorResponse
import com.enesgemci.mamasandpapas.data.base.JsonData
import com.enesgemci.mamasandpapas.network.*
import com.enesgemci.mamasandpapas.util.KeyboardUtils
import com.enesgemci.mamasandpapas.util.fragment.*
import com.google.gson.Gson
import com.hannesdorfmann.mosby3.mvp.MvpActivity
import com.squareup.otto.Bus
import dagger.Lazy
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by enesgemci on 09/09/15.
 */
internal abstract class BaseActivity<V : BaseMvpView, P : BaseMvpPresenter<V>> : MvpActivity<V, P>(), BaseMvpView, BaseRequestHandler.RequestListener {

    protected val mToolBar: Toolbar by bindView(R.id.activityBase_tbToolbar)
    private val mContainer: ConstraintLayout by bindView(R.id.activityBase_vsContainer)

    lateinit private var responseHandler: BaseResponseHandler
    lateinit private var requestHandler: BaseRequestHandler

    @Inject
    lateinit internal var bus: Bus

    @Inject
    lateinit protected var gson: Lazy<Gson>

    @Inject
    lateinit protected var requestGenerator: Lazy<MRequestGenerator>

    protected val disposables = CompositeDisposable()

    abstract fun onActivityStarted(intent: Intent)

    abstract fun injectActivity(component: ActivityComponent)

    val activityComponent: ActivityComponent by lazy {
        DaggerActivityComponent.builder()
                .activityModule(ActivityModule(this))
                .appComponent(MApp.appComponent)
                .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        injectActivity(activityComponent)
        super.onCreate(savedInstanceState)

        requestHandler = MRequestHandler(this, this)
        responseHandler = MResponseHandler(this)

        if (isTablet()) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE)
        } else {
            // setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        }

        setContentView(R.layout.activity_base)
        setSupportActionBar(mToolBar)

        val rootView = window.decorView.findViewById<ViewGroup>(android.R.id.content)

        if (rootView != null) {
            if (rootView.viewTreeObserver.isAlive) {
                rootView.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {

                    override fun onGlobalLayout() {
                        rootView.viewTreeObserver.removeOnGlobalLayoutListener(this)
                        activityStarted()
                    }
                })
            }
        } else {
            activityStarted()
        }
    }

    override fun isTablet(): Boolean {
        return resources.getBoolean(R.bool.is_tablet)
    }

    fun getCompatColor(resId: Int): Int {
        return ActivityCompat.getColor(this, resId)
    }

    private fun activityStarted() {
        onActivityStarted(intent)
    }

    override fun getPage(page: Page, vararg obj: Any?): FragmentBuilder {
        return FragmentFactory.getFragment(page, *obj)!!
    }

    override fun showPage(page: Page, vararg obj: Any?) {
        showPage(getPage(page, *obj))
    }

    override fun showPage(replacer: FragmentBuilder?) {
        replacer?.let {
            var page: Page?

            page = replacer.fragment!!.getPage()

            val TAG = page.name

            val fManager = if (replacer.getFragmentManager() != null) replacer.getFragmentManager() else supportFragmentManager
            val fTransaction = fManager!!.beginTransaction()

            var containerId = if (replacer.isSettedContainer) replacer.getContainerId() else mContainer.id

            replacer.fragment?.let {
                when (replacer.getTransactionAnimation()) {
                    TransactionAnimation.ENTER_FROM_RIGHT ->
                        fTransaction.setCustomAnimations(
                                R.anim.anim_horizontal_fragment_in, R.anim.anim_horizontal_fragment_out,
                                R.anim.anim_horizontal_fragment_in_from_pop, R.anim.anim_horizontal_fragment_out_from_pop)

                    TransactionAnimation.ENTER_FROM_LEFT ->
                        fTransaction.setCustomAnimations(
                                R.anim.anim_horizontal_fragment_in_from_pop, R.anim.anim_horizontal_fragment_out_from_pop,
                                R.anim.anim_horizontal_fragment_in, R.anim.anim_horizontal_fragment_out)

                    TransactionAnimation.ENTER_FROM_BOTTOM ->
                        fTransaction.setCustomAnimations(
                                R.anim.anim_vertical_fragment_in, R.anim.anim_vertical_fragment_out,
                                R.anim.anim_vertical_fragment_in_from_pop, R.anim.anim_vertical_fragment_out_from_pop)

                    TransactionAnimation.ENTER_FROM_RIGHT_STACK ->
                        fTransaction.setCustomAnimations(
                                R.anim.anim_open_next, R.anim.anim_close_main,
                                R.anim.anim_open_main, R.anim.anim_close_next)

                    TransactionAnimation.ENTER_FROM_RIGHT_NO_ENTRANCE ->
                        fTransaction.setCustomAnimations(
                                0, R.anim.anim_horizontal_fragment_out,
                                R.anim.anim_horizontal_fragment_in_from_pop, R.anim.anim_horizontal_fragment_out_from_pop)

                    TransactionAnimation.ENTER_WITH_ALPHA ->
                        //                        fTransaction.setCustomAnimations(
                        //                                R.anim.anim_alphain, R.anim.anim_alphaout,
                        //                                R.anim.anim_alphain, R.anim.anim_alphaout);
                        fTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)

                    TransactionAnimation.NO_ANIM -> {
                    }
                    else -> {
                    }
                }

                if (containerId == -1) {
                    throw RuntimeException()
                }

                if (replacer.getTransactionType() === FragmentTransactionType.REPLACE) {
                    fTransaction.replace(containerId, replacer.fragment!!, TAG)
                } else {
                    fTransaction.add(containerId, replacer.fragment!!, TAG)
                }

                if (replacer.isAddToBackStack()) {
                    fTransaction.addToBackStack(TAG)
                }

                fTransaction.commitAllowingStateLoss()
            } ?: throw RuntimeException()
        }
    }

    override fun onBackPressed() {
        KeyboardUtils.hide(this)

        if (supportFragmentManager.backStackEntryCount > 0) {
            try {
                /**
                 * Stop Otto listeners
                 */
                val backEntry = supportFragmentManager.getBackStackEntryAt(supportFragmentManager.backStackEntryCount - 1)
                val str = backEntry.name

                (supportFragmentManager.findFragmentByTag(str) as BaseFragment<*, *>).stopServiceListener()
            } catch (ex: Exception) {
                Timber.e(ex.message)
            }

            supportFragmentManager.popBackStack()
        } else {
            super.onBackPressed()
        }
    }

    @UiThread
    fun lockScreen(isDialogRequest: Boolean) {
        runOnUiThread {
            responseHandler.lockScreen(isDialogRequest)
        }
    }

    @UiThread
    @Synchronized
    fun unlockScreen() {
        if (!requestHandler.isOnAction) {
            runOnUiThread {
                responseHandler.unlockScreen()
            }
        }
    }

    override fun sendRequest(request: MRequest<*>) {
        if (isConnectedToNet) {
            if (!request.isBehind) {
                lockScreen(request.isDialogRequest)
            }

            disposables.add(requestHandler.getResponse(request))
        }
    }

    private val isConnectedToNet: Boolean
        @AnyThread
        get() {
            val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            var connected = false

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val networkInfoList = connectivityManager.allNetworks

                if (networkInfoList == null || networkInfoList.size == 0) {
                    return false
                }

                networkInfoList.forEach { network ->
                    val networkInfo = connectivityManager.getNetworkInfo(network)

                    connected = networkInfo?.isConnected!!

                    if (connected) {
                        return true
                    }
                }
            } else {
                val networkInfoList = connectivityManager.allNetworkInfo

                if (networkInfoList == null || networkInfoList.isEmpty()) {
                    return false
                }

                networkInfoList.forEach { networkInfo ->
                    if (networkInfo != null) {
                        connected = networkInfo.isConnected

                        if (connected) {
                            return true
                        }
                    }
                }
            }

            return connected
        }

    override fun onError(request: MRequest<*>, response: JsonData?, error: ErrorResponse?) {
        onUserInteraction()

        if (error != null) {
            responseHandler.handleErrorPopup(request, response, error)
        } else {
            responseHandler.broadcastError(request, response)
        }

        unlockScreen()
    }

    override fun onResponse(request: MRequest<*>, response: JsonData) {
        onUserInteraction()
        responseHandler.broadcastData(request, response)
        unlockScreen()
    }

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
        disposables.clear()

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

        requestHandler.destroy()

        super.onDestroy()
    }

    override fun onPause() {
        if (isFinishing) {
            disposables.clear()
        }

        try {
            bus.unregister(this)
        } catch (ex: Exception) {
        }

        super.onPause()
    }

    protected open fun animateToolbar(): Boolean {
        return true
    }

    open protected val isSecure: Boolean
        get() = false

    open fun getPageName(): String? = null
}
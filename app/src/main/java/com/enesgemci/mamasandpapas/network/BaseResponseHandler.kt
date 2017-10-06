/*
 * Copyright (c) 2016.
 *
 * Enes Gemci
 */

package com.enesgemci.mamasandpapas.network

import android.support.annotation.Keep
import com.enesgemci.mamasandpapas.base.BaseActivity
import com.enesgemci.mamasandpapas.data.ErrorResponse
import com.enesgemci.mamasandpapas.data.base.JsonData
import com.enesgemci.mamasandpapas.widget.MProgressDialog

/**
 * Created by enesgemci on 01/08/16.
 */
@Keep
internal abstract class BaseResponseHandler(val activity: BaseActivity<*, *>) {

    protected var progressDialog: MProgressDialog? = null

    abstract fun broadcastData(request: MRequest<*>, response: JsonData)

    abstract fun broadcastError(request: MRequest<*>, response: JsonData?)

    abstract fun handleErrorPopup(request: MRequest<*>, response: JsonData?, error: ErrorResponse?)

    /**
     * toggle loading screen, user {link:removeLoading} to remove it
     */
    abstract fun lockScreen(isDialogRequest: Boolean)

    /**
     * removes loading view
     */
    abstract fun unlockScreen()
}
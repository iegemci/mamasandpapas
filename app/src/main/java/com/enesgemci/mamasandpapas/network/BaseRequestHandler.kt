/*
 * Copyright (c) 2016.
 *
 * Enes Gemci
 */

package com.enesgemci.mamasandpapas.network

import android.os.Handler
import android.os.Looper
import android.support.annotation.Keep
import com.enesgemci.mamasandpapas.base.BaseActivity
import com.enesgemci.mamasandpapas.data.ErrorResponse
import com.enesgemci.mamasandpapas.data.base.JsonData
import io.reactivex.disposables.Disposable
import java.util.*

/**
 * Created by enesgemci on 28/11/2016.
 */
@Keep
internal abstract class BaseRequestHandler(protected var activity: BaseActivity<*, *>, protected val requestListener: BaseRequestHandler.RequestListener) {

    protected val mainThreadHandler: Handler = Handler(Looper.getMainLooper())
    @Volatile protected var currentRequests = ArrayList<MRequest<*>>()

    abstract fun getResponse(request: MRequest<*>): Disposable?

    fun destroy() {
        try {
            if (currentRequests != null && !currentRequests.isEmpty()) {
                currentRequests
                        .filter { request -> !request.isCanceled }
                        .forEach { request -> request.cancel() }
            }
        } catch (e: Exception) {
        }
    }

    val isOnAction: Boolean
        get() = currentRequests != null && !currentRequests.isEmpty()

    interface RequestListener {

        fun onError(request: MRequest<*>, response: JsonData?, error: ErrorResponse?)

        fun onResponse(request: MRequest<*>, response: JsonData)
    }
}
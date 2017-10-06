/*
 * Copyright (c) 2016.
 *
 * Enes Gemci
 */

package com.enesgemci.mamasandpapas.network

import android.text.TextUtils
import com.enesgemci.mamasandpapas.base.BaseActivity
import com.enesgemci.mamasandpapas.base.MApp
import com.enesgemci.mamasandpapas.core.util.extensions.delayed
import com.enesgemci.mamasandpapas.data.ErrorResponse
import com.enesgemci.mamasandpapas.data.base.JsonData
import com.enesgemci.mamasandpapas.util.MExecutor
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by enesgemci on 28/11/2016.
 */
internal class MRequestHandler(activity: BaseActivity<*, *>, requestListener: BaseRequestHandler.RequestListener) : BaseRequestHandler(activity, requestListener) {

    @Inject
    lateinit internal var converterGson: Gson

    @Inject
    lateinit internal var executor: MExecutor

    init {
        MApp.appComponent!!.inject(this)
    }

    override fun getResponse(request: MRequest<*>): Disposable? {
        if (!request.isCanceled) {
            synchronized(request) {
                currentRequests.add(request)
            }

            request.requestTrialCount = request.requestTrialCount + 1

            if (request.requestTrialCount <= request.requestMaxTrial) {
                val key = if (request.requestTrialCount == 1) "" else "Connection Problem"
                Timber.e("[MATELI] " + (if (!TextUtils.isEmpty(key)) "[$key]" else "") + " -> [" + request.serviceConstant.name + " Service] REQUEST TRIAL] : " + request.requestTrialCount)
            }

            val t1 = System.nanoTime()

            return request.observable
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.newThread())
                    .subscribeWith(object : MObserver<Response<out JsonData>>() {

                        override fun onError(t: Throwable) {
                            super.onError(t)
                            currentRequests.remove(request)

                            /**
                             * something went wrong while sending request
                             */
                            requestListener.onError(request, null, getError(request, null))
                        }

                        override fun onNext(response: Response<out JsonData>) {
                            currentRequests.remove(request)
                            val elapsedTime = (System.nanoTime() - t1) / 1e6

                            // wait for UI animation
                            if (request.isWaitForUIAnimation && elapsedTime < 400) {
                                val duration = 400 - elapsedTime
                                handleResponse(request, response).delayed(duration.toLong())
                                Timber.e("Response posted for " + duration.toLong() + " ms for UI")
                            } else {
                                handleResponse(request, response)
                            }
                        }
                    })
        }

        return null
    }

    private fun getError(request: MRequest<*>, errorMessage: String?): ErrorResponse {
        val error = ErrorResponse()
//        error.isRequestBehind = request.isBehind
//        error.serviceConstant = request.serviceConstant
//
//        val errorModel = ErrorModel()
//        errorModel.message = if (!TextUtils.isEmpty(errorMessage)) errorMessage else "Something went wrong"
//
//        error.errors.add(errorModel)

        return error
    }

    private fun trySendRequest(request: MRequest<*>, t: Throwable?) {
        /**
         * timeout or connection error occured, try [MRequest].getRequestMaxTrial times
         */
        if (request.requestTrialCount <= request.requestMaxTrial) {
            getResponse(request)
        } else {
            /**
             * connection error occured, tried [MRequest].getRequestMaxTrial times
             */
            if (t != null) {
                Timber.e(t)
            }

            currentRequests.remove(request)
            requestListener.onError(request, null, getError(request, "connection_error"))
        }
    }

    private fun handleResponse(request: MRequest<*>, response: Response<out JsonData>?) {
        if (response != null) {
            Timber.i(response.raw().toString())
            Timber.i("<--- [MRESPONSE RAW DATA] : [" + request.serviceConstant.name + " Service] -> " + converterGson.toJson(response))

            /**
             * response arrived successfully, but error appeared.
             */
            if (response.errorBody() != null) {
                if (response.raw().request().method() == "GET" && (response.code() == 408
                        || response.code() == 504
                        || response.code() == 598
                        || response.code() == 599)) {
//                    mainThreadHandler.post {
//                        MDialog.create(activity)
//                                .setCancelable(false)
//                                .setPositiveButtonText(CommonTexts.TryAgain)
//                                .setNegativeButtonText(CommonTexts.Cancel)
//                                .setTitle(CommonTexts.Info)
//                                .setMessage(CommonTexts.RequestTimeoutMessage)
//                                .setOnPositiveButtonClickListener(object : BaseDialog.OnDialogButtonClickListener {
//                                    override fun onClick() {
//                                        trySendRequest(request, null)
//                                    }
//                                })
//                                .setOnNegativeButtonClickListener(object : BaseDialog.OnDialogButtonClickListener {
//                                    override fun onClick() {
//                                        requestListener.onError(request, null, null)
//                                    }
//                                }).show()
//                    }
                } else {
                    try {
                        val errorJson = response.errorBody()!!.string()

                        if (!TextUtils.isEmpty(errorJson)) {
                            Timber.e("[MERROR RESPONSE] : " + errorJson)
//                            val mDialog = MDialog.create(activity)
//                            mDialog.setTitle(request.serviceConstant.name)
//
//                            mainThreadHandler.post {
//                                mDialog.setMessage(errorJson)
//                                        .setPositiveButtonText(CommonTexts.Ok)
//                                        .setDialogType(DialogType.ERROR)
//                                        .setCancelable(false)
//                                        .setOnPositiveButtonClickListener(object : BaseDialog.OnDialogButtonClickListener {
//                                            override fun onClick() {
//                                                requestListener.onError(request, response.body(), null)
//                                            }
//                                        }).show()
//                            }
                        } else {
                            /**
                             * error appeared but there is no error body, broadcast generic error
                             */
//                            mainThreadHandler.post {
//                                MDialog.create(activity)
//                                        .setMessage(/*if (BuildConfig.DEVELOPMENT_MODE) response.raw().toString() else */ "Something went wrong")
//                                        .setPositiveButtonText(CommonTexts.Ok)
//                                        .setDialogType(DialogType.ERROR)
//                                        .setCancelable(false)
//                                        .setOnPositiveButtonClickListener(object : BaseDialog.OnDialogButtonClickListener {
//                                            override fun onClick() {
//                                                requestListener.onError(request, null, null)
//                                            }
//                                        }).show()
//                            }
                        }
                    } catch (e: Exception) {
                        /**
                         * this case can happen when json format is invalid or so
                         */
                        Timber.e(e)
                        requestListener.onError(request, null, null)
                    }

                }
            } else {
                /**
                 * response arrived successfully
                 */
                requestListener.onResponse(request, response.body()!!)
            }
        } else {
            /**
             * something went wrong while sending request
             */
            requestListener.onError(request, null, null)
        }
    }
}
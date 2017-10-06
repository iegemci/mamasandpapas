/*
 * Copyright (c) 2016.
 *
 * Enes Gemci
 */

package com.enesgemci.mamasandpapas.network

import android.support.annotation.UiThread
import com.enesgemci.mamasandpapas.base.BaseActivity
import com.enesgemci.mamasandpapas.base.MApp
import com.enesgemci.mamasandpapas.core.util.extensions.post
import com.enesgemci.mamasandpapas.data.ErrorResponse
import com.enesgemci.mamasandpapas.data.base.JsonData
import com.enesgemci.mamasandpapas.util.UIUtils
import com.enesgemci.mamasandpapas.widget.MProgressDialog
import com.google.gson.Gson
import com.squareup.otto.Bus
import dagger.Lazy
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by enesgemci on 01/08/16.
 */
internal class MResponseHandler(activity: BaseActivity<*, *>) : BaseResponseHandler(activity) {

    @Inject
    lateinit internal var converterGson: Gson

    @Inject
    lateinit internal var bus: Bus

    @Inject
    lateinit internal var application: Lazy<MApp>

    init {
        activity.activityComponent.inject(this)
    }

    override fun broadcastData(request: MRequest<*>, response: JsonData) {
        try {
            Timber.wtf("<--- [MRESPONSE] : [" + request.serviceConstant.name + " Service] -> " + converterGson.toJson(response))
        } catch (e: Exception) {
            /**
             * this case can happen when json format is invalid or so
             */
            Timber.e(e)
        }

        if (response != null) {
            Runnable {
                activity.unlockScreen()

                response?.let {
                    bus.post(response)
                }
            }.post()
        } else {
            broadcastError(request, response)
        }
    }

    override fun broadcastError(request: MRequest<*>, response: JsonData?) {
        var error = ErrorResponse()

//        error.isRequestBehind = request.isBehind
//        error.serviceConstant = request.serviceConstant
        Timber.e("MODEL: ErrorModel -> " + request.serviceConstant.name)

        val finalError = error
        UIUtils.mainThreadHandler.post {
            activity.unlockScreen()
            bus.post(finalError)
        }
    }

    override fun handleErrorPopup(request: MRequest<*>, r: JsonData?, error: ErrorResponse?) {
        var response = r
        val finalResponse = response

//        error?.let {
//            UIUtils.mainThreadHandler.post {
//                //                var message: String = ""
////                errors.forEach { message += it.message + "\n" }
//
//                val mDialog = MDialog.create(activity)
//                        .setMessage(message)
////                        .setTitle(error.title)
//                        .setCancelable(false)
////                        .setDialogType(error.dialogType!!)
//
//                mDialog.setPositiveButtonText(CommonTexts.Ok.value)
//
//                mDialog
//                        .setOnPositiveButtonClickListener(object : BaseDialog.OnDialogButtonClickListener {
//                            override fun onClick() {
////                                finalResponse?.let { handleButtonAction(request, it, error.positiveButtonAction) }
//                                finalResponse?.let { handleButtonAction(request, it) }
//                            }
//                        })
//
//                try {
//                    activity.showDialog(mDialog)
//                } catch (e: Exception) {
//                    Timber.e(e)
//                }
//            }
//            }
//        } else {
//            broadcastError(request, response)
//        }
    }

    //    private fun handleButtonAction(request: MRequest<*>, response: MResponse<*>, action: ErrorButtonAction?) {
    private fun handleButtonAction(request: MRequest<*>, response: JsonData) {
//        var errorAction = action
//
//        if (errorAction == null) {
//            errorAction = ErrorButtonAction.NONE
//        }
//
//        when (errorAction) {
//            ErrorButtonAction.NONE -> broadcastError(request, response)
//            ErrorButtonAction.BACK -> activity.onBackPressed()
//            ErrorButtonAction.GO_TO_DASHBOARD -> activity.redirectToDashboard()
//            ErrorButtonAction.GO_TO_MODULE -> {
//                activity.finishActivity()
//                activity.startActivity(activity.javaClass, if (activity.getIntent() != null) activity.getIntent().extras else null)
//            }
//            ErrorButtonAction.LOGOUT -> logout()
//        }

        // todo
        broadcastError(request, response)
    }

    @UiThread
    override fun lockScreen(isDialogRequest: Boolean) {
        try {
            UIUtils.mainThreadHandler.post {
                try {
                    if (progressDialog == null) {
                        progressDialog = MProgressDialog(activity, null)
                    } else {
                        try {
                            if (progressDialog!!.isShowing) {
                                progressDialog?.dismiss()
                            }
                        } catch (e: Exception) {
                        }

                        progressDialog = null
                        progressDialog = MProgressDialog(activity, null)
                    }

                    if (!progressDialog!!.isShowing) {
                        progressDialog?.show()
                    }
                } catch (e: Exception) {
                    Timber.e(e)
                }
            }
        } catch (e: Exception) {
            Timber.e(e)
        }

    }

    @UiThread
    override fun unlockScreen() {
        try {
            UIUtils.mainThreadHandler.post {
                try {
                    if (progressDialog != null && progressDialog!!.isShowing) {
                        progressDialog?.dismiss()
                        progressDialog = null
                    }
                } catch (e: Exception) {
                }
            }
        } catch (e: Exception) {
            Timber.e(e)
        }

    }
}
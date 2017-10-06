/*
 * Copyright (c) 2016.
 *
 * Enes Gemci
 */

package com.enesgemci.mamasandpapas.network

import android.support.annotation.Keep
import com.enesgemci.mamasandpapas.data.base.JsonData
import io.reactivex.Observable
import retrofit2.Response

/**
 * Created by enesgemci on 20/06/16.
 */
@Keep
class MRequest<T : JsonData> {

    val isWaitForUIAnimation: Boolean
    val observable: Observable<Response<T>>
    var isDialogRequest: Boolean = false
    var serviceConstant: ServiceConstant
        private set
    var requestMaxTrial: Int = 0
    var isBehind = false
    var requestTrialCount = 0
    var isCanceled: Boolean = false
        private set

    constructor(observable: Observable<Response<T>>, serviceConstant: ServiceConstant, waitForUIAnimation: Boolean = true) {
        this.observable = observable
        this.serviceConstant = serviceConstant
        this.requestMaxTrial = 3
        this.isWaitForUIAnimation = waitForUIAnimation
    }

    fun setBehind(behind: Boolean): MRequest<*> {
        isBehind = behind
        return this
    }

    fun cancel() {
        this.isCanceled = true
    }
}

package com.enesgemci.mamasandpapas.data.base

import com.enesgemci.mamasandpapas.network.Exclude

/**
 * Created by enesgemci on 21/06/16.
 */
abstract class BaseResponse<T> : JsonData() {

    @Exclude
    open var data: T? = null
}
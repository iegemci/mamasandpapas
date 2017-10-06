/*
 * Copyright (c) 2016.
 *
 * Enes Gemci
 */

package com.enesgemci.mamasandpapas.network

import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by enesgemci on 20/06/16.
 */
@Singleton
internal class MRequestGenerator @Inject constructor(private var restService: MRestService) {

    fun getProductListRequest(searchString: String = "", page: Int = 1, hitsPerPage: Int = 5): MRequest<*> {
        val call = restService.getProductListResponse(searchString, page, hitsPerPage)
        return MRequest(call, ServiceConstant.ProductList)
    }
}

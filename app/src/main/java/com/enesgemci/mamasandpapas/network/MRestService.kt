/*
 * Copyright (c) 2016.
 *
 * Enes Gemci
 */

package com.enesgemci.mamasandpapas.network

import com.enesgemci.mamasandpapas.data.ProductListResponse
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * Created by enesgemci on 17/06/16.
 */

internal interface MRestService {

    @POST("search/full/")
    fun getProductListResponse(@Query("searchString") searchString: String, @Query("page") page: Int, @Query("hitsPerPage") hitsPerPage: Int): Observable<Response<ProductListResponse>>
}
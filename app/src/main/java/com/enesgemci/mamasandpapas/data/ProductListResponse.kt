package com.enesgemci.mamasandpapas.data

import com.enesgemci.mamasandpapas.data.base.JsonData
import com.google.gson.annotations.SerializedName

data class ProductListResponse(@SerializedName("hits") var products: List<ProductModel?>?, var pagination: PaginationModel?) : JsonData()
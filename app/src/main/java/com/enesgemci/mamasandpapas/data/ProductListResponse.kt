package com.enesgemci.mamasandpapas.data

import com.enesgemci.mamasandpapas.data.base.JsonData
import com.google.gson.annotations.SerializedName

data class ProductListResponse(@SerializedName("hits") var products: ArrayList<ProductModel>?, var pagination: PaginationModel?) : JsonData()
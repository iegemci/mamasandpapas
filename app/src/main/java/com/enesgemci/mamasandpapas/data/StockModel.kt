package com.enesgemci.mamasandpapas.data

import com.enesgemci.mamasandpapas.data.base.JsonData

/**
 * Created by enesgemci on 06/10/2017.
 */
data class StockModel(var homeDeliveryQty: Int, var clickAndCollectQty: Int, var maxAvailableQty: Int) : JsonData()
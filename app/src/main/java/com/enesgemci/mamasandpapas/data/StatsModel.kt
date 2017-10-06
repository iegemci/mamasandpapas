package com.enesgemci.mamasandpapas.data

import com.enesgemci.mamasandpapas.data.base.JsonData

/**
 * Created by enesgemci on 06/10/2017.
 */
data class StatsModel(var itemRevenue: Double, var listViews: Double, var detailViews: Double, var addsToCart: Double,
                      var checkouts: Double, var uniquePurchases: Double, var listToDetail: Double) : JsonData()
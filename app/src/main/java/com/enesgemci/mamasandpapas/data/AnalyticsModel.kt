package com.enesgemci.mamasandpapas.data

import com.enesgemci.mamasandpapas.data.base.JsonData
import com.google.gson.annotations.SerializedName

/**
 * Created by enesgemci on 06/10/2017.
 */
data class AnalyticsModel(var name: String?, var division: String?, var department: String?, var group: String,
                          @SerializedName("class") var clazz: String?, var subClass: String?, var color: String?, var designer: String?, var season: String?) : JsonData()
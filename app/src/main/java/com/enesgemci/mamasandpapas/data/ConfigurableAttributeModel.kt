package com.enesgemci.mamasandpapas.data

import com.enesgemci.mamasandpapas.data.base.JsonData

data class ConfigurableAttributeModel(var code: String, var options: ArrayList<ConfigurableAttributeOptionModel>?) : JsonData()
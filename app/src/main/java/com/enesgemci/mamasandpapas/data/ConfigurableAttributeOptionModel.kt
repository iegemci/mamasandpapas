package com.enesgemci.mamasandpapas.data

import com.enesgemci.mamasandpapas.data.base.JsonData

/**
 * Created by enesgemci on 09/10/2017.
 */
data class ConfigurableAttributeOptionModel(var optionId: Int, var label: String, var simpleProductSkus: List<String>,
                                            var isInStock: Boolean) : JsonData()
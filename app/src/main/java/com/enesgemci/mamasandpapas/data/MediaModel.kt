package com.enesgemci.mamasandpapas.data

import com.enesgemci.mamasandpapas.data.base.JsonData

/**
 * Created by enesgemci on 06/10/2017.
 */
data class MediaModel(var position: Int, var mediaType: String, var src: String, var videoUrl: Any) : JsonData()
package com.enesgemci.mamasandpapas.data

import android.os.Parcel
import android.os.Parcelable
import com.enesgemci.mamasandpapas.data.base.JsonData

/**
 * Created by enesgemci on 06/10/2017.
 */
data class StockOfAllOptionsModel(var homeDeliveryQty: Int, var clickAndCollectQty: Int,
                                  var maxAvailableQty: Int) : JsonData(), Parcelable {
    
    constructor(source: Parcel) : this(
            source.readInt(),
            source.readInt(),
            source.readInt()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(homeDeliveryQty)
        writeInt(clickAndCollectQty)
        writeInt(maxAvailableQty)
    }

    companion object {
        @JvmField val CREATOR: Parcelable.Creator<StockOfAllOptionsModel> = object : Parcelable.Creator<StockOfAllOptionsModel> {
            override fun createFromParcel(source: Parcel): StockOfAllOptionsModel = StockOfAllOptionsModel(source)
            override fun newArray(size: Int): Array<StockOfAllOptionsModel?> = arrayOfNulls(size)
        }
    }
}
package com.enesgemci.mamasandpapas.data

import android.os.Parcel
import android.os.Parcelable
import com.enesgemci.mamasandpapas.data.base.JsonData

/**
 * Created by enesgemci on 06/10/2017.
 */
data class StatsModel(var itemRevenue: Double, var listViews: Double, var detailViews: Double, var addsToCart: Double,
                      var checkouts: Double, var uniquePurchases: Double, var listToDetail: Double) : JsonData(), Parcelable {
    
    constructor(source: Parcel) : this(
            source.readDouble(),
            source.readDouble(),
            source.readDouble(),
            source.readDouble(),
            source.readDouble(),
            source.readDouble(),
            source.readDouble()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeDouble(itemRevenue)
        writeDouble(listViews)
        writeDouble(detailViews)
        writeDouble(addsToCart)
        writeDouble(checkouts)
        writeDouble(uniquePurchases)
        writeDouble(listToDetail)
    }

    companion object {
        @JvmField val CREATOR: Parcelable.Creator<StatsModel> = object : Parcelable.Creator<StatsModel> {
            override fun createFromParcel(source: Parcel): StatsModel = StatsModel(source)
            override fun newArray(size: Int): Array<StatsModel?> = arrayOfNulls(size)
        }
    }
}
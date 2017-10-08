package com.enesgemci.mamasandpapas.data

import android.os.Parcel
import android.os.Parcelable
import com.enesgemci.mamasandpapas.data.base.JsonData

/**
 * Created by enesgemci on 06/10/2017.
 */
data class PaginationModel(var totalHits: Int, var totalPages: Int) : JsonData(), Parcelable {
    
    constructor(source: Parcel) : this(
            source.readInt(),
            source.readInt()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(totalHits)
        writeInt(totalPages)
    }

    companion object {
        @JvmField val CREATOR: Parcelable.Creator<PaginationModel> = object : Parcelable.Creator<PaginationModel> {
            override fun createFromParcel(source: Parcel): PaginationModel = PaginationModel(source)
            override fun newArray(size: Int): Array<PaginationModel?> = arrayOfNulls(size)
        }
    }
}
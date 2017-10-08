package com.enesgemci.mamasandpapas.data

import android.os.Parcel
import android.os.Parcelable
import com.enesgemci.mamasandpapas.data.base.JsonData
import com.google.gson.annotations.SerializedName

/**
 * Created by enesgemci on 06/10/2017.
 */
data class AnalyticsModel(var name: String?, var division: String?, var department: String?, var group: String,
                          @SerializedName("class") var clazz: String?, var subClass: String?, var color: String?, var designer: String?, var season: String?) : JsonData(), Parcelable {
    
    constructor(source: Parcel) : this(
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(name)
        writeString(division)
        writeString(department)
        writeString(group)
        writeString(clazz)
        writeString(subClass)
        writeString(color)
        writeString(designer)
        writeString(season)
    }

    companion object {
        @JvmField val CREATOR: Parcelable.Creator<AnalyticsModel> = object : Parcelable.Creator<AnalyticsModel> {
            override fun createFromParcel(source: Parcel): AnalyticsModel = AnalyticsModel(source)
            override fun newArray(size: Int): Array<AnalyticsModel?> = arrayOfNulls(size)
        }
    }
}
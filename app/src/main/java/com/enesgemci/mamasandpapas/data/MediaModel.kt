package com.enesgemci.mamasandpapas.data

import android.os.Parcel
import android.os.Parcelable
import com.enesgemci.mamasandpapas.data.base.JsonData

/**
 * Created by enesgemci on 06/10/2017.
 */
data class MediaModel(var position: Int, var mediaType: String, var src: String, var videoUrl: Any?) : JsonData(), Parcelable {

    constructor(source: Parcel) : this(
            source.readInt(),
            source.readString(),
            source.readString(),
            source.readValue(Any::class.java.classLoader) as Any?)

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(position)
        writeString(mediaType)
        writeString(src)
        writeValue(videoUrl)
    }

    companion object {
        @JvmField val CREATOR: Parcelable.Creator<MediaModel> = object : Parcelable.Creator<MediaModel> {
            override fun createFromParcel(source: Parcel): MediaModel = MediaModel(source)
            override fun newArray(size: Int): Array<MediaModel?> = arrayOfNulls(size)
        }
    }
}
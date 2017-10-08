package com.enesgemci.mamasandpapas.data

import android.os.Parcel
import android.os.Parcelable
import com.enesgemci.mamasandpapas.data.base.JsonData
import com.enesgemci.mamasandpapas.network.converter.DateTimeTypeAdapter
import com.google.gson.annotations.JsonAdapter
import org.joda.time.DateTime

/**
 * Created by enesgemci on 06/10/2017.
 */
data class ProductModel(var productId: Int?,
                        var categoryPositions: HashMap<String?, Long>?,
                        var sku: String?,
                        var createdAt: String?,
                        var typeId: String?,
                        var name: String?,
                        var slug: String?,
                        var brand: String?,
                        var age: List<String?>?,
                        var middleEastExclusive: Boolean = false,
                        var image: String?,
                        var smallImage: String?,
                        var thumbnail: String?,
                        @JsonAdapter(DateTimeTypeAdapter::class) var imageUpdateTime: DateTime?,
                        var motherReference: String?,
                        var giftMessageAvailable: String?,
                        var giftWrappingAvailable: String?,
                        var department: String?,
                        var addToBag: Any?,
                        var get30Off: Any?,
                        var description: String?,
                        var userManual: Any?,
                        var price: Double?,
                        var specialPrice: Long = 0,
                        @JsonAdapter(DateTimeTypeAdapter::class) var specialFromDate: DateTime?,
                        @JsonAdapter(DateTimeTypeAdapter::class) var specialToDate: DateTime?,
                        @JsonAdapter(DateTimeTypeAdapter::class) var newsFromDate: DateTime?,
                        @JsonAdapter(DateTimeTypeAdapter::class) var newsToDate: DateTime?,
                        @JsonAdapter(DateTimeTypeAdapter::class) var onlineDateWithStock: DateTime?,
                        @JsonAdapter(DateTimeTypeAdapter::class) var onlineDate: DateTime?,
                        var status: Int?,
                        var visibility: Int?,
                        var carrycotCompatible: String?,
                        var trippTrapp: Int?,
                        var togRating: String?,
                        var style: String?,
                        var rating: String?,
                        var sideImpactProtection: String?,
                        var cotTypeSize: String?,
                        var sizeCode: String?,
                        var sizeCodeId: Int?,
                        var collectionCharacter: String?,
                        var color: String?,
                        var colorId: Int?,
                        var gender: String?,
                        var developmentStage: String?,
                        var furnitureOnly: Int?,
                        var sizesInStock: List<String>?,
                        var personalisable: Int?,
                        var parentFacing: String?,
                        var lieFlatSeat: Int?,
                        var carSeatCompatible: String?,
                        var extendableHandles: String?,
                        var isofixCompatible: String?,
                        var rearFacing: Int?,
                        var extendedRearFacing: Int?,
                        var isizeReady: String?,
                        var travelSystemCompatible: Int?,
                        var antiAllergenic: String?,
                        var colorHex: String?,
                        var copyAttributes: List<Any>?,
                        var parentSku: String?,
                        var styleColorId: String?,
                        var ranged: Int?,
                        var analytics: AnalyticsModel?,
                        var categoryIds: List<Int>?,
                        var categories: List<String>?,
                        var media: List<MediaModel?>?,
                        var minPrice: Int?,
                        var stock: StockModel?,
                        var isInStock: Boolean = false,
                        var isInHomeDeliveryStock: Boolean = false,
                        var isInClickAndCollectStock: Boolean = false,
                        var isClearance: Boolean = false,
                        var customAllField: String?,
                        var recommended: List<String?>?,
                        var crossSell: List<String?>?,
                        var upSell: List<String?>?,
                        var discounted: String?,
                        var availableColors: List<String>?,
                        var simpleType: String?,
                        var sameColorSiblings: List<String>?,
                        var isAreAnyOptionsInStock: Boolean = false,
                        var stockOfAllOptions: StockOfAllOptionsModel?,
                        var isClearanceFacet: String?,
                        var isInStockFacet: String?,
                        var areAnyOptionsInStockFacet: String?,
                        var isInHomeDeliveryStockFacet: String?,
                        var isInClickAndCollectStockFacet: String?,
                        var sizesInHomeDeliveryStock: List<String>?,
                        var sizesInClickAndCollectStock: List<String?>?,
                        var visibleSku: String?,
                        var stats: StatsModel?,
                        var badges: MutableMap<String?, Long>?) : JsonData(), Parcelable {

    constructor(source: Parcel) : this(
            source.readValue(Int::class.java.classLoader) as Int?,
            source.readSerializable() as HashMap<String?, Long>?,
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            ArrayList<String?>().apply { source.readList(this, String::class.java.classLoader) },
            1 == source.readInt(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readSerializable() as DateTime?,
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readValue(Any::class.java.classLoader) as Any,
            source.readValue(Any::class.java.classLoader) as Any,
            source.readString(),
            source.readValue(Any::class.java.classLoader) as Any,
            source.readValue(Double::class.java.classLoader) as Double?,
            source.readLong(),
            source.readSerializable() as DateTime?,
            source.readSerializable() as DateTime?,
            source.readSerializable() as DateTime?,
            source.readSerializable() as DateTime?,
            source.readSerializable() as DateTime?,
            source.readSerializable() as DateTime?,
            source.readValue(Int::class.java.classLoader) as Int?,
            source.readValue(Int::class.java.classLoader) as Int?,
            source.readString(),
            source.readValue(Int::class.java.classLoader) as Int?,
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readValue(Int::class.java.classLoader) as Int?,
            source.readString(),
            source.readString(),
            source.readValue(Int::class.java.classLoader) as Int?,
            source.readString(),
            source.readString(),
            source.readValue(Int::class.java.classLoader) as Int?,
            source.createStringArrayList(),
            source.readValue(Int::class.java.classLoader) as Int?,
            source.readString(),
            source.readValue(Int::class.java.classLoader) as Int?,
            source.readString(),
            source.readString(),
            source.readString(),
            source.readValue(Int::class.java.classLoader) as Int?,
            source.readValue(Int::class.java.classLoader) as Int?,
            source.readString(),
            source.readValue(Int::class.java.classLoader) as Int?,
            source.readString(),
            source.readString(),
            ArrayList<Any>().apply { source.readList(this, Any::class.java.classLoader) },
            source.readString(),
            source.readString(),
            source.readValue(Int::class.java.classLoader) as Int?,
            source.readParcelable<AnalyticsModel>(AnalyticsModel::class.java.classLoader),
            ArrayList<Int>().apply { source.readList(this, Int::class.java.classLoader) },
            source.createStringArrayList(),
            source.createTypedArrayList(MediaModel.CREATOR),
            source.readValue(Int::class.java.classLoader) as Int?,
            source.readParcelable<StockModel>(StockModel::class.java.classLoader),
            1 == source.readInt(),
            1 == source.readInt(),
            1 == source.readInt(),
            1 == source.readInt(),
            source.readString(),
            ArrayList<String?>().apply { source.readList(this, String::class.java.classLoader) },
            ArrayList<String?>().apply { source.readList(this, String::class.java.classLoader) },
            ArrayList<String?>().apply { source.readList(this, String::class.java.classLoader) },
            source.readString(),
            source.createStringArrayList(),
            source.readString(),
            source.createStringArrayList(),
            1 == source.readInt(),
            source.readParcelable<StockOfAllOptionsModel>(StockOfAllOptionsModel::class.java.classLoader),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.createStringArrayList(),
            ArrayList<String?>().apply { source.readList(this, String::class.java.classLoader) },
            source.readString(),
            source.readParcelable<StatsModel>(StatsModel::class.java.classLoader),
            source.readSerializable() as MutableMap<String?, Long>
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeValue(productId)
        writeSerializable(categoryPositions)
        writeString(sku)
        writeString(createdAt)
        writeString(typeId)
        writeString(name)
        writeString(slug)
        writeString(brand)
        writeList(age)
        writeInt((if (middleEastExclusive) 1 else 0))
        writeString(image)
        writeString(smallImage)
        writeString(thumbnail)
        writeSerializable(imageUpdateTime)
        writeString(motherReference)
        writeString(giftMessageAvailable)
        writeString(giftWrappingAvailable)
        writeString(department)
        writeValue(addToBag)
        writeValue(get30Off)
        writeString(description)
        writeValue(userManual)
        writeValue(price)
        writeLong(specialPrice)
        writeSerializable(specialFromDate)
        writeSerializable(specialToDate)
        writeSerializable(newsFromDate)
        writeSerializable(newsToDate)
        writeSerializable(onlineDateWithStock)
        writeSerializable(onlineDate)
        writeValue(status)
        writeValue(visibility)
        writeString(carrycotCompatible)
        writeValue(trippTrapp)
        writeString(togRating)
        writeString(style)
        writeString(rating)
        writeString(sideImpactProtection)
        writeString(cotTypeSize)
        writeString(sizeCode)
        writeValue(sizeCodeId)
        writeString(collectionCharacter)
        writeString(color)
        writeValue(colorId)
        writeString(gender)
        writeString(developmentStage)
        writeValue(furnitureOnly)
        writeStringList(sizesInStock)
        writeValue(personalisable)
        writeString(parentFacing)
        writeValue(lieFlatSeat)
        writeString(carSeatCompatible)
        writeString(extendableHandles)
        writeString(isofixCompatible)
        writeValue(rearFacing)
        writeValue(extendedRearFacing)
        writeString(isizeReady)
        writeValue(travelSystemCompatible)
        writeString(antiAllergenic)
        writeString(colorHex)
        writeList(copyAttributes)
        writeString(parentSku)
        writeString(styleColorId)
        writeValue(ranged)
        writeParcelable(analytics, 0)
        writeList(categoryIds)
        writeStringList(categories)
        writeTypedList(media)
        writeValue(minPrice)
        writeParcelable(stock, 0)
        writeInt((if (isInStock) 1 else 0))
        writeInt((if (isInHomeDeliveryStock) 1 else 0))
        writeInt((if (isInClickAndCollectStock) 1 else 0))
        writeInt((if (isClearance) 1 else 0))
        writeString(customAllField)
        writeList(recommended)
        writeList(crossSell)
        writeList(upSell)
        writeString(discounted)
        writeStringList(availableColors)
        writeString(simpleType)
        writeStringList(sameColorSiblings)
        writeInt((if (isAreAnyOptionsInStock) 1 else 0))
        writeParcelable(stockOfAllOptions, 0)
        writeString(isClearanceFacet)
        writeString(isInStockFacet)
        writeString(areAnyOptionsInStockFacet)
        writeString(isInHomeDeliveryStockFacet)
        writeString(isInClickAndCollectStockFacet)
        writeStringList(sizesInHomeDeliveryStock)
        writeList(sizesInClickAndCollectStock)
        writeString(visibleSku)
        writeParcelable(stats, 0)
        writeValue(badges)
    }

    companion object {
        @JvmField val CREATOR: Parcelable.Creator<ProductModel> = object : Parcelable.Creator<ProductModel> {
            override fun createFromParcel(source: Parcel): ProductModel = ProductModel(source)
            override fun newArray(size: Int): Array<ProductModel?> = arrayOfNulls(size)
        }
    }
}
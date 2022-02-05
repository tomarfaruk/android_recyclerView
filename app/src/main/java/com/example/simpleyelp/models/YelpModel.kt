package com.example.simpleyelp.models

import com.google.gson.annotations.SerializedName

data class YelpModel(
    @SerializedName("total") val total: Int,
    @SerializedName("businesses") val restaurant: List<YelpRestaurantModel>
)

data class YelpRestaurantModel(
    val name: String,
    val rating: Double,
    val price: String,
    @SerializedName("review_count") val numReviews: Int,
    @SerializedName("distance") val distanceInMeters: String,
    @SerializedName("image_url") val imageUrl: String,
    @SerializedName("categories") val categories: List<YelpCategoriesModel>,
    @SerializedName("location") val location: YelpLocation

) {
    fun disPlayDistance(): String {
        val milesPerMeter = distanceInMeters.toFloat() * 0.000621371
        return "%.2f mi".format(milesPerMeter)
    }
}

data class YelpCategoriesModel(@SerializedName("title") val title: String)
data class YelpLocation(@SerializedName("address1") val address: String)
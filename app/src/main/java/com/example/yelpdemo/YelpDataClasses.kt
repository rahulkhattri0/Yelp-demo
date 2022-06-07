package com.example.yelpdemo

import android.icu.text.CaseMap
import com.google.gson.annotations.SerializedName
//Gson coverts the data we get from the api to these data classes
// if it is a JSON array then it will be treated as a list in kotlin.
data class YelpSearchResult(
    @SerializedName("total") val total:Int,
    @SerializedName("businesses") val restaurants: List<YelpBusiness>
)
data class YelpBusiness(
    @SerializedName("name") val name:String,
    @SerializedName("rating") val rating:Float,
    @SerializedName("price") val price:Float,
    @SerializedName("review_count") val number_of_reviews:Int,
    @SerializedName("image_url") val image_url:String,
    @SerializedName("categories") val categories:List<YelpCategory>,
    @SerializedName("location") val location:YelpLocation
)
data class YelpCategory(
    @SerializedName("title") val title: String
)
data class YelpLocation(
    @SerializedName("address1")val address:String
)
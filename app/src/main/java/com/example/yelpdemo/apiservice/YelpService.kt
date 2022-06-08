package com.example.yelpdemo.apiservice

import com.example.yelpdemo.YelpSearchResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

//endpoints of the yelp api must be defined here and ini the same interface pattern
//return value of each method must be Call<I>
//and functions must be annotated with th HTTP verb(GET,POST,etc)
//HTTP verbs are basically the types of requests that we send to the API
//Each parameter in the function has been annotated by the @QUERY annotation which specifies which parameter are passed in the query string(in our case we have used term and location)
//term is the name of the restaurant
//location is its location
interface YelpService {
    @GET("businesses/search")
    fun searchRestaurant(@Header("Authorization")authKey:String,@Query("term") name: String, @Query("location") location: String): Call<YelpSearchResult>
}
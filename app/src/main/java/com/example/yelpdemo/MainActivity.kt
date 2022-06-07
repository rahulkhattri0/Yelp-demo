package com.example.yelpdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "MainActivity"
        private const val BASE_URL: String = "https://api.yelp.com/v3/"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //as per documentation
        val retrofit=
            Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()
        val yelpservice = retrofit.create(YelpService::class.java)
        yelpservice.searchRestaurant("Bearer $API_KEY","Restaurant","Tokyo").enqueue(object: Callback<Any>{
            override fun onResponse(call: Call<Any>, response: Response<Any>){
                Log.i(TAG," Got Response : $response")
            }

            override fun onFailure(call: Call<Any>, t: Throwable) {
                Log.i(TAG,"error ocurred : $t")
            }

        })
    }
}
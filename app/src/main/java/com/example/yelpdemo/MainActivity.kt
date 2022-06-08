package com.example.yelpdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.yelpdemo.apiservice.YelpService
import com.google.android.material.snackbar.Snackbar
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
    private val restaurants = mutableListOf<YelpBusiness>()
    private var clroot:ConstraintLayout? =null
    private var location:String=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        clroot = findViewById(R.id.clroot)
        retrofitstuff()
    }

    private fun retrofitstuff() {
        val retrofit =
            Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
                .build()
        val yelpservice = retrofit.create(YelpService::class.java)
        val inflatedView = LayoutInflater.from(this).inflate(R.layout.edit_text, null)
        val rvlist: RecyclerView = findViewById(R.id.rv_main)
        val adapter = AdapterMain(this, restaurants)
        rvlist.adapter = adapter
        rvlist.layoutManager = LinearLayoutManager(this)
        AlertDialog.Builder(this).setTitle("enter name of city").setView(inflatedView)
            .setPositiveButton("OK") { _, _ ->
                val editText = inflatedView.findViewById<EditText>(R.id.edit_text_download)
                if (editText.text.toString().equals(""))
                    Toast.makeText(this, "Please enter a city name", Toast.LENGTH_LONG).show()
                else {
                    supportActionBar!!.title = "Popular Restaurants in "+ editText.text.toString()
                    location = editText.text.toString()
                    yelpservice.searchRestaurant("Bearer $API_KEY", "Restaurant", location)
                        .enqueue(object : Callback<YelpSearchResult> {
                            override fun onResponse(call: Call<YelpSearchResult>, response: Response<YelpSearchResult>) {
                                if (response.code() == 400) {
                                    Snackbar.make(clroot!!, "could not find this city!", Snackbar.LENGTH_LONG).show()
                                }
                                else {
                                    val body = response.body()
                                    if (body == null) {
                                        Log.i(TAG, "some error occurred and could not get gata")
                                        return
                                    } else {
                                        restaurants.addAll(body.restaurants)
                                        //notifies the recycler view adapter that the data set has been changed
                                        adapter.notifyDataSetChanged()
                                    }
                                }
                                Log.i(TAG, " Got Response : $response")
                            }
                            override fun onFailure(call: Call<YelpSearchResult>, t: Throwable) {
                                Log.i(TAG, "error occurred : $t")
                            }
                        })
                }
            }.show()
    }
}
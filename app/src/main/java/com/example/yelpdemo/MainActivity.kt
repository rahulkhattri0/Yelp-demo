package com.example.yelpdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
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
    private var location:String=""
    override fun onCreate(savedInstanceState: Bundle?) {
        val retrofit=
        Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()
        val yelpservice = retrofit.create(YelpService::class.java)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val inflatedView = LayoutInflater.from(this).inflate(R.layout.edit_text,null)
        AlertDialog.Builder(this).setTitle("enter name of city").setView(inflatedView).setPositiveButton("OK"){
            _,_ -> val editText = inflatedView.findViewById<EditText>(R.id.edit_text_download)
            if (editText.text.toString().equals(""))
                Toast.makeText(this,"Please enter a city name",Toast.LENGTH_LONG).show()
            else
                location = editText.text.toString()
            yelpservice.searchRestaurant("Bearer $API_KEY","Restaurant",location).enqueue(object: Callback<Any>{
                override fun onResponse(call: Call<Any>, response: Response<Any>){
                    Log.i(TAG," Got Response : $response")
                }

                override fun onFailure(call: Call<Any>, t: Throwable) {
                    Log.i(TAG,"error ocurred : $t")
                }

            })
        }.show()
    }
}
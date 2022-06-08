package com.example.yelpdemo

import android.content.Context
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import retrofit2.Callback

class AdapterMain(val context: Context, val list_of_restaurants:List<YelpBusiness>):RecyclerView.Adapter<AdapterMain.ViewHolder>() {




    override fun getItemCount(): Int {
        return list_of_restaurants.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list_of_restaurants[position],position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflated_view:View = LayoutInflater.from(context).inflate(R.layout.restaurant_name,parent,false)
        return ViewHolder(inflated_view)
    }
    inner class ViewHolder(itemview : View): RecyclerView.ViewHolder(itemview){
        fun bind(YelpBusiness : YelpBusiness,position: Int){
            val textView = itemView.findViewById<TextView>(R.id.Tv_name)
            textView.text = YelpBusiness.name
            itemView.findViewById<RatingBar>(R.id.rating_bar).rating = YelpBusiness.rating
            itemView.findViewById<TextView>(R.id.number_of_reviews).text = YelpBusiness.number_of_reviews.toString()
            itemView.findViewById<TextView>(R.id.address).text = YelpBusiness.location.address
            itemView.findViewById<TextView>(R.id.Category).text = YelpBusiness.categories[0].title
            itemView.findViewById<TextView>(R.id.price).text = YelpBusiness.price
            Glide.with(context).load(YelpBusiness.image_url).apply(RequestOptions().transforms(
                CenterCrop() , RoundedCorners(20)
            )).into(itemView.findViewById(R.id.image_view))

        }
    }
}
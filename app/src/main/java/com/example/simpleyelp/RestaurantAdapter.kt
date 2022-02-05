package com.example.simpleyelp

import android.content.Context
import android.view.LayoutInflater
import android.view.RoundedCorner
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
import com.example.simpleyelp.models.YelpRestaurantModel
import com.google.android.material.shape.RoundedCornerTreatment

class RestaurantAdapter(
    private val context: Context,
    private val restaurantList: MutableList<YelpRestaurantModel>
) : RecyclerView.Adapter<RestaurantAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(position: Int) {
            val itemModel = restaurantList[position]
            itemView.findViewById<TextView>(R.id.tvName).text = itemModel.name
            itemView.findViewById<RatingBar>(R.id.rbRatingBar).rating = itemModel.rating.toFloat()
            itemView.findViewById<TextView>(R.id.tvReviews).text = "${itemModel.numReviews} Reviews"
            itemView.findViewById<TextView>(R.id.tvAddress).text = itemModel.location.address
            itemView.findViewById<TextView>(R.id.tvDistence).text = itemModel.disPlayDistance()
            itemView.findViewById<TextView>(R.id.tvPrice).text = itemModel.price

            if(itemModel.categories.isNotEmpty()) {
                itemView.findViewById<TextView>(R.id.tvCategory).text =
                    itemModel.categories[0].title
            }
            Glide.with(context).load(itemModel.imageUrl).apply(RequestOptions().transforms(
                CenterCrop(), RoundedCorners (10)
            )).into(itemView.findViewById<ImageView>(R.id.ivImageView))

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_restaurant, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount() = restaurantList.size

}

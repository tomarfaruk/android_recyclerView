package com.example.simpleyelp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.simpleyelp.models.YelpModel
import com.example.simpleyelp.models.YelpRestaurantModel
import com.example.simpleyelp.utils.API_KEY
import com.example.simpleyelp.utils.BASE_URL
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    companion object {
        private val TAG = "MainActivity"
    }

    private lateinit var restaurantAdapter: RestaurantAdapter
    private lateinit var rvRestaurants: RecyclerView
    private val restaurantList: MutableList<YelpRestaurantModel> =
        emptyList<YelpRestaurantModel>().toMutableList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvRestaurants = findViewById(R.id.rvRestaurants)

        restaurantAdapter = RestaurantAdapter(this, restaurantList)
        rvRestaurants.adapter = restaurantAdapter
        rvRestaurants.layoutManager = LinearLayoutManager(this)

        val retrofit =
            Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
                .build()

        val yelpService = retrofit.create(YelpService::class.java)
        yelpService.searchRestaurants("Bearer $API_KEY", "Avocado Toast", "New York")
            .enqueue(object : Callback<YelpModel> {
                override fun onResponse(call: Call<YelpModel>, response: Response<YelpModel>) {
                    Log.i(TAG, "response ${response.body()}")
                    val yelpModel = response.body() ?: return
                    restaurantList.clear()
                    restaurantList.addAll(yelpModel.restaurant)
                    restaurantAdapter.notifyDataSetChanged()
                }

                override fun onFailure(call: Call<YelpModel>, t: Throwable) {
                    Log.i(TAG, t.message.toString())
                }
            })
    }
}
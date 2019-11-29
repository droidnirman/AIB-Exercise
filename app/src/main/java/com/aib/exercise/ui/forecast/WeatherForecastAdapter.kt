package com.aib.exercise.ui.forecast

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aib.exercise.R
import com.aib.exercise.di.GlideApp
import com.aib.exercise.domain.model.Forecast
import com.aib.exercise.utils.px
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_forecast.view.*

private const val IMAGE_BASE_URL = "https://openweathermap.org/img/w/"

class WeatherForecastAdapter : RecyclerView.Adapter<WeatherForecastAdapter.ViewHolder>() {

    private var forecastList: List<Forecast> = emptyList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_forecast, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return forecastList.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        var dateText: String = forecastList[position].date
        if (position == 0)
            dateText = "Today"

        viewHolder.dateText.text = dateText
        viewHolder.weatherCondition.text = forecastList[position].weather_condition
        viewHolder.tempMax.text = forecastList[position].temp_max
        viewHolder.tempMin.text = forecastList[position].temp_min
        loadImage(
            IMAGE_BASE_URL + forecastList[position].weather_icon + ".png",
            viewHolder.itemView.context,
            viewHolder.weatherIcon
        )
    }

    /**
     * Method setting results to list
     */
    fun setResults(results: List<Forecast>) {
        forecastList = results.toList()
        notifyDataSetChanged()
    }

    /**
     * Method to download image using Glide and set to [ImageView]
     */
    @Suppress("DEPRECATION")
    private fun loadImage(url: String?, context: Context, imageView: ImageView) {

        val requestOption = RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
            .override(80.px)
            .transforms(CenterCrop(), RoundedCorners(10))

        GlideApp.with(context).load(url)
            .transition(DrawableTransitionOptions.withCrossFade())
            .apply(requestOption)
            .into(imageView)
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dateText: TextView = itemView.text_date
        val weatherCondition: TextView = itemView.text_weather_condition
        val tempMax: TextView = itemView.text_max_temp
        val tempMin: TextView = itemView.text_min_temp
        val weatherIcon: ImageView = itemView.image_weather_icon
    }
}
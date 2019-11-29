package com.aib.exercise.data.repository

import android.content.Context
import com.aib.exercise.data.ForecastServices
import com.aib.exercise.data.model.response.ForecastResponseModel
import com.aib.exercise.domain.model.Forecast
import com.aib.exercise.utils.convertDateTextToNewFormat
import com.aib.exercise.utils.isNetworkStatusAvailable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import timber.log.Timber.d
import java.net.UnknownHostException

class WeatherForecastRepositoryImpl(
    private val service: ForecastServices,
    private val context: Context
) : WeatherForecastRepository {


    override fun getWeatherForecast(cityId: String, appId: String)
            : Single<List<Forecast>> {

        if (context.isNetworkStatusAvailable()) {
            return service.getWeatherForecast(
                cityId = cityId,
                appId = appId
            ).map {
                processResult(it)
            }.subscribeOn(Schedulers.io())
        }

        return Single.error(UnknownHostException())

    }

    /**
     * Method process the API response and returns the desired data model class
     */
    private fun processResult(responseModel: ForecastResponseModel): List<Forecast> {

        val forecastList = mutableListOf<Forecast>()
        val weatherDataMap = responseModel.list.asSequence().groupBy {
            convertDateTextToNewFormat(it.dtTxt)
        }

        val keysMap = weatherDataMap.keys

        for (item in keysMap) {
            d("Date $item")

            val dateWiseForecastList = weatherDataMap[item]

            if (!dateWiseForecastList.isNullOrEmpty()) {
                val condition: String = dateWiseForecastList[0].weather[0].description
                val icon: String = dateWiseForecastList[0].weather[0].icon

                val tempArray = dateWiseForecastList.map { it.weatherRecord.temp }

                d("Temp Array $tempArray")
                d("-----------------------")

                val forecastData = Forecast(
                    date = item,
                    weather_condition = condition,
                    weather_icon = icon,
                    temp_max = tempArray.max().toString() + "°C",
                    temp_min = tempArray.min().toString() + "°C"
                )

                forecastList.add(forecastData)
            }

        }

        return forecastList
    }
}
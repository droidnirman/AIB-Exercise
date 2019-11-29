package com.aib.exercise.data

import com.aib.exercise.data.model.response.ForecastResponseModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query


interface ForecastServices {

    /**
     * Get 5 days weather Forecast. Returns a [Single] emitting the API response.
     */
    @GET("/data/2.5/forecast?units=metric")
    fun getWeatherForecast(
        @Query("id") cityId: String,
        @Query("APPID") appId: String
    ): Single<ForecastResponseModel>

}
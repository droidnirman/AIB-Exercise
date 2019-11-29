package com.aib.exercise.data.repository

import com.aib.exercise.domain.model.Forecast
import io.reactivex.Single

/**
 * Provider of [List<Forecast>]
 * This interface abstracts the logic of getting weather forecast through API.
 */
interface WeatherForecastRepository {
    /**
     * Fetches 5 days / 3 hour weather forecast data
     *
     * Returns a [Single] emitting a set of [List<Forecast>] returned by the API
     *
     * Operates on a background thread.
     */
    fun getWeatherForecast(cityId: String, appId: String): Single<List<Forecast>>


}
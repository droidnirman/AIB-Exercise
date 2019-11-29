package com.aib.exercise.ui.forecast

import com.aib.exercise.domain.model.Forecast


sealed class WeatherForecastListState {
    abstract val data: List<Forecast>
}

data class DefaultState(
    override val data: List<Forecast>
) : WeatherForecastListState()

data class LoadingState(
    override val data: List<Forecast>
) : WeatherForecastListState()

data class ErrorState(
    val errorMessage: String?,
    override val data: List<Forecast>
) : WeatherForecastListState()
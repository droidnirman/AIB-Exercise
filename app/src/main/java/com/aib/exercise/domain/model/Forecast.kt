package com.aib.exercise.domain.model

data class Forecast(
    val date: String,
    val weather_condition: String?,
    val weather_icon: String?,
    val temp_max: String,
    val temp_min: String
)
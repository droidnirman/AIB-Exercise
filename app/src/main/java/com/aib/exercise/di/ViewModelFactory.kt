package com.aib.exercise.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aib.exercise.data.repository.WeatherForecastRepository
import com.aib.exercise.ui.forecast.WeatherForecastViewModel
import javax.inject.Inject
import javax.inject.Singleton

@Suppress("UNCHECKED_CAST")
@Singleton
class ViewModelFactory @Inject constructor(private val repo: WeatherForecastRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeatherForecastViewModel::class.java)) {
            return WeatherForecastViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
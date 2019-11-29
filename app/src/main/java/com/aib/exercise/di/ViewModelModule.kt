package com.aib.exercise.di

import androidx.lifecycle.ViewModel
import com.aib.exercise.ui.forecast.WeatherForecastViewModel
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindWeatherForecastViewModel(forecastViewModel: WeatherForecastViewModel): ViewModel
}
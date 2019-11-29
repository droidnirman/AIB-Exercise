package com.aib.exercise.di

import android.content.Context
import com.aib.exercise.data.ForecastServices
import com.aib.exercise.data.repository.WeatherForecastRepository
import com.aib.exercise.data.repository.WeatherForecastRepositoryImpl
import dagger.Module
import dagger.Provides

@Module
class ForecastRepositoryModule {

    @Provides
    fun provideForecastRepo(
        forecastApi: ForecastServices,
        context: Context
    ): WeatherForecastRepository = WeatherForecastRepositoryImpl(forecastApi, context)
}
package com.aib.exercise.ui.main

import com.aib.exercise.ui.forecast.WeatherForecastFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Module to declare UI components that have injectable members
 */
@Module
abstract class BindingModule {

    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun bindWeatherForecastFragment(): WeatherForecastFragment
}
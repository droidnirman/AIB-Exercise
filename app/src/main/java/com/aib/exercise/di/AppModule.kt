package com.aib.exercise.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Module to setup Application scoped instances that require providers.
 */
@Module
class AppModule {

    @Provides
    @Singleton
    fun provideContext(app: Application): Context = app.applicationContext
}
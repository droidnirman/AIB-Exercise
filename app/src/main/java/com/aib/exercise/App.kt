package com.aib.exercise

import com.aib.exercise.di.AppComponent
import com.aib.exercise.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import timber.log.Timber

class App : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        getDaggerAppComponent().inject(this)
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return getDaggerAppComponent()
    }

    private fun getDaggerAppComponent(): AppComponent {
        return DaggerAppComponent.builder().application(this).build()
    }
}
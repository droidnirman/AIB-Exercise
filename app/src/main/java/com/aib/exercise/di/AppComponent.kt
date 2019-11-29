package com.aib.exercise.di

import android.app.Application
import com.aib.exercise.App
import com.aib.exercise.ui.main.BindingModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * Component providing Application scoped instances.
 */
@Singleton
@Component(
    modules = [AndroidSupportInjectionModule::class,
        AppModule::class,
        BindingModule::class,
        ForecastRepositoryModule::class,
        RetrofitModule::class,
        ViewModelModule::class,
        ViewModelFactoryModule::class]
)

interface AppComponent : AndroidInjector<DaggerApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(app: App)
}
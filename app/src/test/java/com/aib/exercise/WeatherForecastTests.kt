@file:Suppress("DEPRECATION")

package com.aib.exercise

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.aib.exercise.data.repository.WeatherForecastRepository
import com.aib.exercise.domain.model.Forecast
import com.aib.exercise.ui.forecast.*
import io.reactivex.Single
import junit.framework.Assert.assertNotNull
import junit.framework.Assert.assertTrue
import org.junit.Before
import org.junit.ClassRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations


@RunWith(JUnit4::class)
class WeatherForecastTests {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var observer: Observer<WeatherForecastListState>

    @Mock
    lateinit var repository: WeatherForecastRepository

    private lateinit var viewModel: WeatherForecastViewModel

    companion object {
        @ClassRule
        @JvmField
        val schedulers = RxImmediateSchedulerRule()
    }

    @Before
    @Throws(Exception::class)
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = WeatherForecastViewModel(repository)
        viewModel.stateLiveData.observeForever(observer)
    }

    @Test
    fun testNull() {
        `when`(repository.getWeatherForecast(CITY_ID, BuildConfig.OpenWeatherMapAPIKey)).thenReturn(
            null
        )
        assertNotNull(viewModel.stateLiveData)
        assertTrue(viewModel.stateLiveData.hasObservers())
    }

    @Test
    fun testApiFetchDataSuccess() {
        // Mock API response
        `when`(repository.getWeatherForecast(CITY_ID, BuildConfig.OpenWeatherMapAPIKey)).thenReturn(
            Single.just(getDataModel())
        )
        viewModel.updateWeatherForecastData()
        verify(observer).onChanged(LoadingState(emptyList()))
        verify(observer).onChanged(DefaultState(getDataModel()))
    }

    @Test
    fun testApiFetchDataError() {
        `when`(repository.getWeatherForecast(CITY_ID, BuildConfig.OpenWeatherMapAPIKey)).thenReturn(
            Single.error(
                Throwable(
                    "Api error"
                )
            )
        )
        viewModel.updateWeatherForecastData()
        verify(observer).onChanged(LoadingState(emptyList()))
        verify(observer).onChanged(ErrorState("Api error", emptyList()))
    }


    private fun getDataModel(): List<Forecast> {
        return listOf(
            Forecast(
                "Test",
                "Test",
                "03d",
                "",
                ""
            )
        )

    }

}
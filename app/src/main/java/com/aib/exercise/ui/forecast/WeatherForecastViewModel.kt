package com.aib.exercise.ui.forecast

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aib.exercise.BuildConfig
import com.aib.exercise.data.repository.WeatherForecastRepository
import com.aib.exercise.domain.model.Forecast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposables
import io.reactivex.schedulers.Schedulers
import timber.log.Timber.d
import timber.log.Timber.e
import javax.inject.Inject

const val CITY_ID = "1259229" // Pune city ID

class WeatherForecastViewModel @Inject constructor(private val repo: WeatherForecastRepository) :
    ViewModel() {

    // declare state for forecast list
    val stateLiveData = MutableLiveData<WeatherForecastListState>()

    private var disposable = Disposables.disposed()

    // initiate state for forecast list
    init {
        stateLiveData.value = LoadingState(emptyList())
    }

    fun updateWeatherForecastData() {
        d("update list")
        getForecastData()
    }

    fun restoreWeatherForecastData() {
        d("restore list")
        stateLiveData.value = DefaultState(obtainCurrentData())
    }

    fun refreshWeatherForecastData() {
        stateLiveData.value = LoadingState(emptyList())
        getForecastData()
    }

    private fun getForecastData() {
        disposable = repo.getWeatherForecast(CITY_ID, BuildConfig.OpenWeatherMapAPIKey)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::onForecastDataReceived, this::onError)
    }

    private fun onError(error: Throwable) {
        e("error ${error.localizedMessage}")
        stateLiveData.value = ErrorState(error.localizedMessage, obtainCurrentData())
        disposable.dispose()
    }

    private fun onForecastDataReceived(weatherForecastList: List<Forecast>) {
        d("data received ${weatherForecastList.size}")
        stateLiveData.value = DefaultState(weatherForecastList)
        disposable.dispose()
    }

    private fun obtainCurrentData() = stateLiveData.value?.data ?: emptyList()


}
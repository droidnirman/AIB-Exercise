package com.aib.exercise.ui.forecast

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.aib.exercise.R
import com.aib.exercise.domain.model.Forecast
import com.aib.exercise.ui.main.MainActivity
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_weather_forecast.*
import timber.log.Timber.d
import javax.inject.Inject


class WeatherForecastFragment : DaggerFragment(), SwipeRefreshLayout.OnRefreshListener {

    @Inject
    lateinit var mViewModelFactory: ViewModelProvider.Factory

    private lateinit var mViewModel: WeatherForecastViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_weather_forecast, container, false)
        setHasOptionsMenu(false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel =
            ViewModelProviders.of(this, mViewModelFactory).get(WeatherForecastViewModel::class.java)

        setUpToolbar()

        setUpList()

        layout_refresh.setOnRefreshListener(this)

        observerViewModel()

        savedInstanceState?.let {
            mViewModel.restoreWeatherForecastData()
        } ?: mViewModel.updateWeatherForecastData()
    }

    override fun onRefresh() {
        setResultToListAdapter(emptyList())
        mViewModel.refreshWeatherForecastData()
    }

    override fun onDestroy() {
        super.onDestroy()
        mViewModel.stateLiveData.removeObserver(stateObserver)
    }

    private val stateObserver = Observer<WeatherForecastListState> { state ->
        state?.let {
            when (state) {

                is DefaultState -> {
                    d("default state")
                    layout_refresh.isRefreshing = false
                    shimmer_view_container.stopShimmerAnimation()
                    shimmer_view_container.visibility = View.GONE
                    layout_refresh.visibility = View.VISIBLE

                    // Setting data to view
                    setResultToListAdapter(it.data)
                }

                is LoadingState -> {
                    d("loading state")
                    layout_refresh.isRefreshing = true
                    shimmer_view_container.startShimmerAnimation()
                    shimmer_view_container.visibility = View.VISIBLE
                    layout_refresh.visibility = View.GONE
                }

                is ErrorState -> {
                    d("error state")
                    layout_refresh.isRefreshing = false
                    shimmer_view_container.stopShimmerAnimation()
                    shimmer_view_container.visibility = View.GONE
                    layout_refresh.visibility = View.VISIBLE
                    showErrorStateView(state.errorMessage ?: "Error")
                }

            }
        }

    }

    private fun observerViewModel() {
        mViewModel.stateLiveData.observe(this, stateObserver)
    }


    private fun setUpToolbar() {
        val act = activity as MainActivity
        toolbar.title = this.getString(R.string.pune_weather_forecast)
        act.setSupportActionBar(toolbar)
    }

    private fun setUpList() {

        with(list_forecast) {
            adapter = WeatherForecastAdapter()
            layoutManager = LinearLayoutManager(activity).apply {
                orientation = RecyclerView.VERTICAL
            }
            setHasFixedSize(false)
        }
    }

    private fun setResultToListAdapter(factsList: List<Forecast>) {
        val adapter = list_forecast.adapter as WeatherForecastAdapter
        adapter.setResults(factsList)
    }

    private fun showErrorStateView(errorMessage: String) {
        val snackbar = view?.let { it ->
            Snackbar
                .make(it, errorMessage, Snackbar.LENGTH_LONG)
        }
        snackbar?.show()
    }
}
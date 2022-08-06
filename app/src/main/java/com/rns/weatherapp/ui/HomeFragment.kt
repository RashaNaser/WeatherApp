package com.rns.weatherapp.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.rns.weatherapp.R
import com.rns.weatherapp.data.domain.Weather
import com.rns.weatherapp.data.network.Client
import com.rns.weatherapp.databinding.FragmentHomeBinding
import com.rns.weatherapp.util.formatDecimals

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        setup()
        return binding.root
    }

    private fun setup() {
        val okHTTP = Client()
        binding.shimmerLayout.startShimmer()
        okHTTP.getWeather {
            requireActivity().runOnUiThread {
                val weather = okHTTP.weatherList
                if (okHTTP.weatherList.list?.isNotEmpty() == true) {
                    setViews(weather)
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setViews(weather: Weather) {

        val weatherItem = weather.list!![0]
        val imageUrl = "https://openweathermap.org/img/wn/${weatherItem.weather[0].icon}.png"

        binding.shimmerLayout.apply {
            stopShimmer()
            visibility = View.GONE
        }
        binding.constraintLayout.visibility = View.VISIBLE

        binding.textLocationName.text = weather.city!!.name

        binding.textTemperature.text = "${formatDecimals(weatherItem.temp.day)}º"
        binding.textDescription.text = weatherItem.weather[0].main
        Glide.with(requireActivity())
            .load(imageUrl)
            .placeholder(R.mipmap.ic_launcher)
            .error(R.mipmap.ic_launcher)
            .into(binding.imageWeather)

        binding.textWindSpeed.text = "${formatDecimals(weatherItem.speed)} m/s"
        binding.textHumidity.text = "${weatherItem.humidity}%"
        binding.textRain.text = "${100 * (weatherItem.pop)}%"

        binding.recyclerview.adapter = WeatherRecyclerView(weather.list)
    }
}
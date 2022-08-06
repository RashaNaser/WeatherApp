package com.rns.weatherapp.data.domain

data class Weather(
    val city: City? = null,
    val list: List<WeatherItem>? = null
)
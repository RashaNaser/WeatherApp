package com.rns.weatherapp.data.domain

data class WeatherItem(
    val humidity: Int,
    val pop: Double,
    val speed: Double,
    val temp: Temp,
    val dt: Int,
    val weather: List<WeatherObject>
)
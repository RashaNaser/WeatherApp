package com.rns.weatherapp.data.network

import android.util.Log
import com.google.gson.Gson
import com.rns.weatherapp.data.domain.Weather
import com.rns.weatherapp.util.Constants
import okhttp3.*
import java.io.IOException

class Client() {
    private val client = OkHttpClient()
    private var _weatherItem = Weather()
    val weatherList: Weather get() = _weatherItem

    fun getWeather(setupViews: () -> Unit) {
        val request = Request.Builder().url(Constants.URL).build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d("TAG", "onFailure: ${e.message}")
            }

            override fun onResponse(call: Call, response: Response) {
                response.body?.string().let { jsonString ->
                    val result = Gson().fromJson(jsonString, Weather::class.java)
                    _weatherItem = result
                }
                setupViews()
            }
        }
        )
    }
}
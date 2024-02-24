package com.plcoding.weatherapp.data.remote

import retrofit2.http.GET

interface WeatherApi {

    @GET("v1/forecast?hourly=temperature_2m,weathercode,relativehumidity_2m,windspeed_10m,pressure_msl")
    suspend fun getWeatherData(): WeatherDto
}
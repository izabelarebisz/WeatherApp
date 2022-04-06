package com.example.weatherapp.model

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {
    @GET("data/2.5/weather?&units=metric&APPID=XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX")
    fun getData(
        @Query("q") cityName: String
    ): Single<Model>
}
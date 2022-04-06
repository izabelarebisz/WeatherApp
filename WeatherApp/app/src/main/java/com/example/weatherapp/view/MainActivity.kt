package com.example.weatherapp.view

import android.content.ContentValues.TAG
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.weatherapp.R
import com.example.weatherapp.viewmodel.WeatherViewModel
import kotlinx.android.synthetic.main.activity_main.*
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_basic.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

class MainActivity  : AppCompatActivity() {

   // private lateinit var GET: SharedPreferences
   // private lateinit var SET: SharedPreferences.Editor
   lateinit var viewModel: WeatherViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(WeatherViewModel::class.java)
    }
/*
    private lateinit var viewmodel: WeatherViewModel

    private lateinit var GET: SharedPreferences
    private lateinit var SET: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_basic)

        viewmodel = ViewModelProviders.of(this).get(WeatherViewModel::class.java)

        GET = getSharedPreferences(packageName, MODE_PRIVATE)
        SET = GET.edit()



        var name = GET.getString("cityName", "Gliwice")?.toLowerCase()
        city_name.setText(name)
        viewmodel.refresh(name!!)

        getLiveData()

        main_screen.setOnRefreshListener {
            ll_data.visibility = View.GONE
            tv_error.visibility = View.GONE
            pb_loading.visibility = View.GONE

            var cityName = GET.getString("cityName", name)?.toLowerCase()
            city_name.setText(cityName)
            viewmodel.refresh(cityName!!)
            main_screen.isRefreshing = false
        }

        choose_city.setOnClickListener {
            val cityName = city_name.text.toString()
            SET.putString("cityName", cityName)
            SET.apply()
            viewmodel.refresh(cityName)
            getLiveData()
            Log.i(TAG, "onCreate: " + cityName)

        }
    }



    private fun getLiveData() {

        viewmodel.data.observe(this, Observer { data ->
            data?.let {
                ll_data.visibility = View.VISIBLE
                pb_loading.visibility = View.GONE

                val icon  = data.weather.get(0).icon
                if (icon == "01d") weather_icon.setImageResource(R.drawable.i01d)
                if (icon == "01n") weather_icon.setImageResource(R.drawable.i01n)
                if (icon == "02d") weather_icon.setImageResource(R.drawable.i02d)
                if (icon == "02n") weather_icon.setImageResource(R.drawable.i02n)
                if (icon == "03d" || icon == "03n") weather_icon.setImageResource(R.drawable.i03)
                if (icon == "04d" || icon == "04n") weather_icon.setImageResource(R.drawable.i03)
                if (icon == "09d" || icon == "09n") weather_icon.setImageResource(R.drawable.i09)
                if (icon == "10d" || icon == "10n") weather_icon.setImageResource(R.drawable.i10)
                if (icon == "11d" || icon == "11n") weather_icon.setImageResource(R.drawable.i11)
                if (icon == "13d" || icon == "13n") weather_icon.setImageResource(R.drawable.i13)
                if (icon == "50d" || icon == "50n") weather_icon.setImageResource(R.drawable.i50)

                val temp = data.main.temp.roundToInt()
                temperature.text = temp.toString() + "°C"


                val sunr = data.sys.sunrise.toString().toLong()
                sunrise.text = SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(sunr*1000))

                val suns = data.sys.sunset.toString().toLong()
                sunset.text = SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(suns*1000))

                description.text = data.weather.get(0).description

                pressure.text = data.main.pressure.toString() + "hPa"
                humidity.text = data.main.humidity.toString() + "%"
                wind.text = data.wind.speed.toString() + "km/h"
                visibility.text = data.visibility.toString() + "km"

            }
        })

        viewmodel.error.observe(this, Observer { error ->
            error?.let {
                if (error) {
                    tv_error.visibility = View.VISIBLE
                    pb_loading.visibility = View.GONE
                    ll_data.visibility = View.GONE
                } else {
                    tv_error.visibility = View.GONE
                }
            }
        })

        viewmodel.load.observe(this, Observer { loading ->
            loading?.let {
                if (loading) {
                    pb_loading.visibility = View.VISIBLE
                    tv_error.visibility = View.GONE
                    ll_data.visibility = View.GONE
                } else {
                    pb_loading.visibility = View.GONE
                }
            }
        })

    }*/
}



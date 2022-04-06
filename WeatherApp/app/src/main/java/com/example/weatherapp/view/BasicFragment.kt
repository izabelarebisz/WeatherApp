package com.example.weatherapp.view

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.example.weatherapp.R
import com.example.weatherapp.viewmodel.WeatherViewModel
import kotlinx.android.synthetic.main.fragment_basic.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText


class BasicFragment : Fragment() {

    private lateinit var viewmodel: WeatherViewModel

    private lateinit var GET: SharedPreferences
    private lateinit var SET: SharedPreferences.Editor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_basic, container, false)

        view.findViewById<Button>(R.id.go).setOnClickListener {
            view.findNavController().navigate(R.id.action_basicFragment_to_seniorFragment)
        }
/*
        view.findViewById<FloatingActionButton>(R.id.action_button).setOnClickListener {
            view.findNavController().navigate(R.id.action_basicFragment_to_seniorFragment)
        }*/


        viewmodel = ViewModelProviders.of(this).get(WeatherViewModel::class.java)

        GET = this.activity!!.getSharedPreferences("pref", AppCompatActivity.MODE_PRIVATE)
       SET = GET.edit()


        var name = GET.getString("cityName", "Gliwice")?.toLowerCase()

        //var name = "Gliwice"
        //city_name.setText(name)
        viewmodel.refresh(name!!)

        getLiveData()

        view.findViewById<SwipeRefreshLayout>(R.id.main_screen).setOnRefreshListener {
            ll_data.visibility = View.GONE
            tv_error.visibility = View.GONE
            pb_loading.visibility = View.GONE

            var cityName = GET.getString("cityName", name)?.toLowerCase()
            city_name.setText(cityName)
            viewmodel.refresh(cityName!!)
            main_screen.isRefreshing = false
        }
/*
        main_screen.setOnRefreshListener {
            ll_data.visibility = View.GONE
            tv_error.visibility = View.GONE
            pb_loading.visibility = View.GONE

            var cityName = GET.getString("cityName", name)?.toLowerCase()
            city_name.setText(cityName)
            viewmodel.refresh(cityName!!)
            main_screen.isRefreshing = false
        }*/

        view.findViewById<ImageView>(R.id.choose_city).setOnClickListener {
            var cityName = view.findViewById<TextInputEditText>(R.id.city_name).text.toString()
            SET.putString("cityName", cityName)
            SET.apply()
            viewmodel.refresh(cityName!!)
            getLiveData()
        }
       /* choose_city.setOnClickListener {
            val cityName = city_name.text.toString()
            SET.putString("cityName", cityName)
            SET.apply()
            viewmodel.refresh(cityName)
            getLiveData()
            Log.i(ContentValues.TAG, "onCreate: " + cityName)

        }*/


        return view

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



                val dat = data.dt.toString().toLong()
                val date = SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(dat*1000)
                day.text = date.toString()

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
                //visibility.text = data.visibility.toString() + "km"
                visibility.text = data.main.feels_like.roundToInt().toString() + "°C"

                sr.setImageResource(R.drawable.sr)
                ss.setImageResource(R.drawable.ss)
                pr.setImageResource(R.drawable.pr)
                vi.setImageResource(R.drawable.vi)
                wi.setImageResource(R.drawable.wi)
                hu.setImageResource(R.drawable.hu)


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

    }

}







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


class SeniorFragment : Fragment() {
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
        val view = inflater.inflate(R.layout.fragment_senior, container, false)

        view.findViewById<Button>(R.id.go_back).setOnClickListener {
            view.findNavController().navigate(R.id.action_seniorFragment_to_basicFragment)
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


        view.findViewById<ImageView>(R.id.choose_city).setOnClickListener {
            var cityName = view.findViewById<TextInputEditText>(R.id.city_name).text.toString()
            SET.putString("cityName", cityName)
            SET.apply()
            viewmodel.refresh(cityName!!)
            getLiveData()
        }

        return view

    }




    private fun getLiveData() {

        viewmodel.data.observe(this, Observer { data ->
            data?.let {
                ll_data.visibility = View.VISIBLE
                pb_loading.visibility = View.GONE


                val temp = data.main.temp.roundToInt()
                temperature.text = temp.toString() + "°C"



                description.text = data.weather.get(0).description

                pressure.text = data.main.pressure.toString() + "hPa"
                wind.text = data.wind.speed.toString() + "km/h"

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





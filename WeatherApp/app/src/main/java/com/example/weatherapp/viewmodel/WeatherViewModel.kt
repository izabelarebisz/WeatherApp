package com.example.weatherapp.viewmodel

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.model.Model
import com.example.weatherapp.model.RetrofitInstance
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

private const val TAG = "WeatherViewModel"
class WeatherViewModel : ViewModel(){
    private val retrofitInstance = RetrofitInstance()
    private val disposable = CompositeDisposable()

    val data = MutableLiveData<Model>()
    val error = MutableLiveData<Boolean>()
    val load = MutableLiveData<Boolean>()

    fun refresh(cityName: String){
        getData(cityName)
    }

    fun getData(cityName: String){
        load.value = true
        disposable.add(
            retrofitInstance.getDataService(cityName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<Model>(){
                    override fun onSuccess(t: Model) {
                        data.value = t
                        error.value = false
                        load.value = false
                        Log.d(TAG, "onSuccess: Success")
                    }

                    override fun onError(e: Throwable) {
                        error.value = true
                        load.value = false
                        Log.e(TAG, "onError: " + e)
                    }

                }

                )
        )
    }



}
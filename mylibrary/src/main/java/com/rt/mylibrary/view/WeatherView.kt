package com.rt.layoutlibrary.view

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.widget.Toast
import androidx.appcompat.widget.AppCompatTextView
import com.rt.mylibrary.utils.ApiConfig
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

@SuppressLint("CheckResult")
class WeatherView : AppCompatTextView {
    constructor(context: Context?, city: String) : super(context) {
        initData(city)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(
        context,
        attrs
    )

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr)

    @SuppressLint("CheckResult")
    private fun initData(city: String) {
        ApiConfig.apiServer.getWeather(city).subscribeOn(Schedulers.io())
            .unsubscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                text = it.toString()
                Log.i("testLoading", "data:$it")
            }, {
                Log.i("testLoading", "error：$it")
            })
    }
}
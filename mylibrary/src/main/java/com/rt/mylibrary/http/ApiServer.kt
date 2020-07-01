package com.rt.layoutlibrary.http

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface ApiServer {
    //    @GET("http://10.10.20.240:88/meetingcloud/apps/meeting/api/v1/getWeather")
    @GET("http://wthrcdn.etouch.cn/weather_mini")
    fun getWeather(@Query("city") city: String): Observable<String>
}
package com.example.sunnyweather.android.logic.network

import com.example.sunnyweather.android.SunnyWeatherApplication
import com.example.sunnyweather.android.logic.model.PlaceResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PlaceService {
    //当调用search-Places()方法时，Retrofit会自动发起一条GET请求
    @GET("v2/place?token=${SunnyWeatherApplication.TOKEN}&lang=zh_CN")
    //API中的query这个参数需要动态指定的
    fun searchPlace(@Query("query") query:String):Call<PlaceResponse>
    //返回值申明成Call<PlaceResponse>->Retrofit会将服务器返回的JOSN数据自动解析成PlaceResponse对象
}
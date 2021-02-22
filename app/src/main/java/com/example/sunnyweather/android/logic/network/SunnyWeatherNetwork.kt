package com.example.sunnyweather.android.logic.network

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.RuntimeException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


/**
 * 统一的网络数据访问入口，对所有请求的API进行封装
 * 使用11.7.3小节->简化Retrofit回调写法，通过定义await()函数来实现
 */
object SunnyWeatherNetwork {
    //创建PlaceService接口的动态代理对象
    private val placeService=ServiceCreator.create<PlaceService>()

    //发起搜索城市数据请求
    suspend fun searchPlaces(query:String)= placeService.searchPlace(query).await()

    //await函数简化Retrofit回调写法->详解见11.7.3
    private suspend fun <T> Call<T>.await():T{

        //suspendCoroutine函数来挂起当前协程
        return suspendCoroutine { continuation ->
            enqueue(object : Callback<T> {
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()
                    if (body != null) continuation.resume(body)
                    else continuation.resumeWithException(RuntimeException("response body is null"))
                }

                override fun onFailure(call: Call<T>, t: Throwable) {
                    continuation.resumeWithException(t)
                }
            })
        }
    }
}
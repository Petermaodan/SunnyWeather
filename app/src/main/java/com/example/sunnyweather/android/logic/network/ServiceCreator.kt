package com.example.sunnyweather.android.logic.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Retrofit构造器
 * 11.6.3小结类似
 */
object ServiceCreator {
    private const val BASE_URL = "https://api.caiyunapp.com/"

    private val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    //外部调用时，实际上调用的是retrofit的create方法
    fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)


    //内联函数->泛型实例化
    //使用inline来修饰方法，使用reified关键字来修饰泛型->泛型实例化的两大前提条件
    //获取动态代理的方式->val appService=ServiceCreator.create<AppService>()
    inline fun <reified T> create(): T = create(T::class.java)
}
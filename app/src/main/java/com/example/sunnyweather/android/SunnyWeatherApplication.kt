package com.example.sunnyweather.android

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

/**
 * 全局获取Context
 */
class SunnyWeatherApplication: Application() {
    companion object{
        //彩云天气的令牌
        const val TOKEN="AEW9VHCX7srSzFwg"

        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context=applicationContext
    }
}
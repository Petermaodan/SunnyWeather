package com.example.sunnyweather.android.logic


import androidx.lifecycle.liveData
import com.example.sunnyweather.android.logic.model.Place
import com.example.sunnyweather.android.logic.network.SunnyWeatherNetwork
import kotlinx.coroutines.Dispatchers
import java.lang.RuntimeException

/**
 * 作为仓库层的统一封装入口
 * 13.4节->LiveData用法->用于在数据发生变化时通知给观察者
 */
object Repository {

    //将liveData()函数的线程参数类型指定成Dispatchers.IO->代码块所有的代码都运行在子线程中
    fun searchPlaces(query:String)= liveData(Dispatchers.IO){
        val result=try {
            //SunnyWeatherNetwork.searchPlaces(query)->搜索城市数据
            val placeResponse=SunnyWeatherNetwork.searchPlaces(query)
            if (placeResponse.status=="ok"){
                val places=placeResponse.places

                //Result.success()->内置函数，包装获取到的数据
                Result.success(places)
            }else{
                Result.failure(RuntimeException("response status is ${placeResponse.status}"))
            }
        }catch (e:Exception){
            Result.failure<List<Place>>(e)
        }

        //将包装的结果发射出去
        emit(result)
    }
}
package com.example.sunnyweather.android.ui.place

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.sunnyweather.android.logic.Repository
import com.example.sunnyweather.android.logic.model.Place

class PlaceViewModel:ViewModel() {
    private val searchLiveData=MutableLiveData<String>()

    //用于对界面上显示的城市数据进行缓存，保证在手机屏幕发生旋转时，数据不会丢失
    val placeList= ArrayList<Place>()

    //13.4.2小节
    //使用Transformations.switchMap()->来将LiveData对象转换成一个可观察的LiveData对象->searchLiveData的数据发生变化，就调用下面的方法
    //使用场景也非常固定，即这个LiveData是调用另外的方法获取的
    val placeLiveData=Transformations.switchMap(searchLiveData){
        query->Repository.searchPlaces(query)
    }


    //将传入的搜索参数赋值给一个searchLiveData对象
    fun searchPlaces(query:String){
        searchLiveData.value=query
    }
}
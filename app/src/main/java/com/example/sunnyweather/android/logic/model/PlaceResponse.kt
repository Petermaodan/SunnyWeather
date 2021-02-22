package com.example.sunnyweather.android.logic.model

/**
 * 类与属性根据搜索城市数据接口返回的JOSN格式来定义
 */

import com.google.gson.annotations.SerializedName

class PlaceResponse(val status: String, val places: List<Place>)

class Place(val name: String, val location: Location, @SerializedName("formatted_address") val address: String)

class Location(val lng: String, val lat: String)
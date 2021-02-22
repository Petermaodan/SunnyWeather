package com.example.sunnyweather.android.ui.place

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sunnyweather.R
import kotlinx.android.synthetic.main.fragment_place.*

/**
 * 对Fragment进行实现->fragment不能直接显示，编写完成后需要添加到activity中才行
 */
class PlaceFragment: Fragment() {
    //懒加载技术，允许我们在整个类中随时使用viewModel这个变量，不用关心何是初始化以及是否为空等前提条件
    val viewModel by lazy { ViewModelProvider(this).get(PlaceViewModel::class.java) }

    private lateinit var adapter:PlaceAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //加载之前编写的fragment_place布局
        return inflater.inflate(R.layout.fragment_place,container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //先给RecyclerView设置LayoutManager和适配器
        val layoutManager=LinearLayoutManager(activity)
        recyclerView.layoutManager=layoutManager
        //PlaceViewModel中的placeList集合作为数据源
        adapter=PlaceAdapter(this,viewModel.placeList)
        recyclerView.adapter=adapter

        //调用searchPlaceEdit的addTextChangedListener()来监听搜索框中内容的变化
        searchPlaceEdit.addTextChangedListener{ editable->
            val content=editable.toString()

            //若发生变化，就调用ViewModel的searchPlace()方法
            if (content.isNotEmpty()){
                viewModel.searchPlaces(content)
            }else{
                //若无变化，就将RecyclerView进行隐藏
                recyclerView.visibility=View.GONE
                bgImageView.visibility=View.VISIBLE
                viewModel.placeList.clear()
                adapter.notifyDataSetChanged()
            }
        }
        viewModel.placeLiveData.observe(viewLifecycleOwner, Observer { result->
            //判断数据是否为空
            val places=result.getOrNull()
            if (places!=null){
                recyclerView.visibility=View.VISIBLE
                bgImageView.visibility=View.GONE
                //将这些数据添加到PlaceViewModel的placeList集合中
                viewModel.placeList.clear()
                viewModel.placeList.addAll(places)
                adapter.notifyDataSetChanged()
            }else{
                Toast.makeText(activity,"未能查到任何地点",Toast.LENGTH_SHORT).show()
                result.exceptionOrNull()?.printStackTrace()
            }
        })
    }
}
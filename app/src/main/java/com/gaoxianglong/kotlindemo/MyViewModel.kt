package com.gaoxianglong.kotlindemo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyViewModel : ViewModel () { // 类名后面跟 : 表示继承某个类 后面在跟一个 () 表示这个类有一个构造方法
    private val _number:MutableLiveData<Int> by lazy { MutableLiveData<Int>().also{it.value=0} } // 延迟初始化
    val number:LiveData<Int>
    get() = _number
    fun modifyNumber(number: Int) {
        _number.value = _number.value?.plus(number) // 如果_number.value不为空那么就进行？后的内容
    }
}
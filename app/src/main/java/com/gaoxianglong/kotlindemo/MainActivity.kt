package com.gaoxianglong.kotlindemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val myViewModel = ViewModelProvider(this).get(MyViewModel::class.java)
        // 通过ViewModel中共享出来的liveData可以监听到数据的改变，然后设置到textView上
        // Observer { textView.text = it.toString() } 是java中的匿名函数
        // 在kotlin中不需要findViewById()来获取控件了，直接使用id
        // textView.text 表示java中的 textView.setText()
        // it表示这匿名函数接收的那参数,因为这个匿名函数只有一个接收的参数所以才能使用it
        myViewModel.number.observe(this, Observer { textView.text = it.toString() } )
        buttonAdd.setOnClickListener{
            myViewModel.modifyNumber(1)
        }
        buttonCut.setOnClickListener{
            myViewModel.modifyNumber(-1)
        }

    }
}

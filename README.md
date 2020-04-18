# KotlinDemo

从现在开始都是使用kotlin来代替java，我也是边学边写文档，可能会出现一些错误或一些不足，我没能及时发现，请通过qq联系我。

QQ：1766816333

第一期我就先不从kotlin的基础将了，先来看看kotlin中怎么实现ViewModel。当然如果你不知道java的ViewModel怎么实现，也没关系，直接学习kotlin的就够了。

首先是布局，还是一样的没有变化，两个Button,一个TextView。

TextView:用来显示数字
Button:分别用来对TextView中的数字进行加/减1的操作

ViewModel是什么呢，是用于保存数据的，它是怎保存的呢？它是贯穿整个Activity生命周期的一个组件，这就说明它不会因为Activity被重建而初始化。

举个栗子：

    当应用程序翻转屏幕时，被系统意外回收后再次打开应用时等方式都要导致当前Activity被重新创建，
    如果这个Activity之前有数据存在，那么重建后数据就会丢失。
    这个时候就能用到ViewModel了，因为它贯穿Activity的整个周期，所以它不受重建的影响，
    保存在它里面的数据就不会丢失。

ViewModel:[官方文档](https://developer.android.google.cn/topic/libraries/architecture/viewmodel?hl=zh_cn#kotlin)

加载ViewModel还需要添加一个Lifecycle的依赖:[官方文档](https://developer.android.google.cn/jetpack/androidx/releases/lifecycle#declaring_dependencies)
```gradle
implementation 'androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0'
```

创建一个kotlin的类取名为MyViewModel,继承与ViewModel。
```kotlin
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyViewModel : ViewModel() {
    private val _number:MutableLiveData<Int> by lazy { MutableLiveData<Int>().also{it.value=0} } // 延迟初始化
    val number:LiveData<Int> // 这个是和上面那个常量相关联的，提供给外部访问
    get() = _number // 让获取ViewModel的时候返回的是这个常量
    fun modifyNumber(number: Int) {
        // 因为_number是MutableLiveData类型的 所以可以使用.value来获取值或赋值
        _number.value = _number.value?.plus(number) // 如果_number.value不为空那么就进行？后的内容
    }
}
```

来阅读以下代码，从定义一个类开始：
```
class 类名 : 要继承的类 (这个括号表示构造方法){
    private val表示定义一个常量 常量名:这个常量的类型 by lazy{类型().also{it当前匿名方法接收的唯一值.value=0}匿名方法}延迟初始化，用到的时候才初始化
    ...
    fun定义一个函数 函数名(接收的参数名:参数类型){
        _number.value = _number.value?表示当前面这个不为空的时候就执行后面的内容.plus(number) //这个方法表示将前面的值加上后面的值
    }
}
```

在MainActivity中加载ViewModel
```kotlin
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val myViewModel = ViewModelProvider(this).get(MyViewModel::class.java) // MyViewModel::class.java 等同于Java中的MyViewModel.class 就是去找到这个类
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
```

到这里在kotlin中实现ViewModel就实现了，可能还是会有对kotlin的不熟悉，我也是一样的，后面我考虑是不是出点kotlin的基础文档，android第一行代码第三版还在路上，等到了再考虑一下.
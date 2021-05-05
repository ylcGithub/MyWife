package ylc.love.wxj.mywife.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.util.*
import kotlin.collections.HashMap

/**
 * @author Administrator
 * @create on 2021/5/4 0004
 * 说明:
 */
object LiveDataBus {
    private val bus = HashMap<String,MutableLiveData<Any>>()

    fun with(tag:String):LiveData<Any>?{
        if(!bus.containsKey(tag)){
            bus[tag]= MutableLiveData()
        }
        //不会收到重复事件，但是多处订阅的时候需要使用不同的tag
//        bus[tag] = MutableLiveData()
        return bus[tag]
    }

    fun send(tag:String,msg:Any){
        if(!bus.containsKey(tag))return
        bus[tag]?.postValue(msg)
    }
}
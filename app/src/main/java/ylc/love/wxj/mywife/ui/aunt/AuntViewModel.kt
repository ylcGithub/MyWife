package ylc.love.wxj.mywife.ui.aunt

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ylc.love.wxj.mywife.base.BaseViewModel
import ylc.love.wxj.mywife.model.AppDataBase
import ylc.love.wxj.mywife.model.AuntBean

/**
 *@author YLC-D
 *@create on 2021/1/28 15
 *说明:
 */
class AuntViewModel : BaseViewModel() {

    private val _dataList = MutableLiveData<List<AuntBean>>()
    val dateList: LiveData<List<AuntBean>> = _dataList


    fun getAllAuntBeans() = runOnThread(work = {
        val dateBeanDao = AppDataBase.instance.auntBeanDao()
        val list = dateBeanDao.selectAll()
        //添加预测日期
        val count = list.size
        if (count == 1) {
            val last = list[0]
            val startTime = last.startTime + (28 * 86400000L)
            val endTime = startTime + (last.usedTime * 86400000L)
            list.add(0,AuntBean(-1L, startTime, endTime, last.usedTime))
        } else if(count > 1) {
            val averageStart = mutableListOf<Float>()
            val averageUsed = mutableListOf<Int>()
            for (i in 0 until count) {
                if (i < count - 1) {
                    val d = ((list[i].startTime - list[i+1].startTime)/86400000).toFloat()
                    averageStart.add(d)
                }
                if (list[i].usedTime > 0) {
                    averageUsed.add(list[i].usedTime)
                }
            }
            var usedTotal = 0
            averageUsed.forEach {
                usedTotal += it
            }
            val usedTime = if(averageUsed.size > 0) usedTotal / averageUsed.size else 0
            var totalST = 0f
            averageStart.forEach {
                totalST += it
            }
            val startTime = ((totalST / averageStart.size)*86400000).toLong()+ list[0].startTime
            list.add(0,AuntBean(-1L, startTime, 0L, usedTime))
        }
        _dataList.postValue(list)
    })
}
package ylc.love.wxj.mywife.ui.bill

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ylc.love.wxj.mywife.base.BaseViewModel
import ylc.love.wxj.mywife.model.AppDataBase
import ylc.love.wxj.mywife.model.BillBean
import ylc.love.wxj.mywife.model.DateBean
import ylc.love.wxj.mywife.model.DateInterval
import ylc.love.wxj.mywife.utils.DateUtils
import ylc.love.wxj.mywife.utils.LogUtil

class BillViewModel : BaseViewModel() {

    private val _dataList = MutableLiveData<List<DateBean>>()
    val dateList: LiveData<List<DateBean>> = _dataList
    val currTime:MutableLiveData<DateInterval> = MutableLiveData()

    fun queryBeans() = runOnThread(work = {
        currTime.value?.let {
            val billDao = AppDataBase.instance.billBeanDao()
            val list = billDao.selectByDate(it.startTime,it.endTime)
            LogUtil.log(list.toString())
        }
    },catch = { e->
        LogUtil.log(e.toString())
    })

    fun addBean() = runOnThread(work = {
        val billBeanDao = AppDataBase.instance.billBeanDao()
        for (i in 1..9){
            val bean = BillBean(i.toLong(),i,"类型$i", DateUtils.curTime - (i * 24 * 3600 * 1000),56f,"描述设计了东风科技$i")
            billBeanDao.insert(bean)
        }
        val list = billBeanDao.selectByDateAndType(
            DateUtils.curTime - (9 * 24 * 3600 * 1000),
            DateUtils.curTime,
            6
        )
        LogUtil.log("$list--------咕咕鸡")
    },
    catch = { e ->
        LogUtil.log(e.toString())
    })
}
package ylc.love.wxj.mywife.ui.bill

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ylc.love.wxj.mywife.base.BaseViewModel
import ylc.love.wxj.mywife.base.LifeApplication
import ylc.love.wxj.mywife.expand.toast
import ylc.love.wxj.mywife.model.AppDataBase
import ylc.love.wxj.mywife.model.BillBean
import ylc.love.wxj.mywife.model.BillTypeBean
import ylc.love.wxj.mywife.model.DateInterval
import ylc.love.wxj.mywife.utils.DateUtils
import ylc.love.wxj.mywife.utils.LogUtil

class BillViewModel : BaseViewModel() {

    private val _dataList = MutableLiveData<List<BillBean>>()
    val dateList: LiveData<List<BillBean>> = _dataList

    private val _typeList = MutableLiveData<MutableList<BillTypeBean>>()
    val typeList: LiveData<MutableList<BillTypeBean>> = _typeList

    val currTime:MutableLiveData<DateInterval> = MutableLiveData(DateInterval(0L,0L))
    val currType:MutableLiveData<Int> = MutableLiveData(0)

    fun queryBills() = runOnThread(work = {
        currTime.value?.let {
            val billDao = AppDataBase.instance.billBeanDao()
            val list =if(currType.value!! > 0) billDao.selectByDateAndType(it.startTime,it.endTime,currType.value!!) else billDao.selectByDate(it.startTime,it.endTime)
            setValueOnMain(_dataList,list)
        }
    },catch = { e->
        e.printStackTrace()
        LogUtil.log(e.toString())
    })

    fun queryBillTypes()=runOnThread(work = {
        val typeDao = AppDataBase.instance.billTypeBeanDao()
        val list = typeDao.selectAll()
        list.add(0, BillTypeBean("全部",0))
        setValueOnMain(_typeList, list)
    },catch = { e->
        e.printStackTrace()
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
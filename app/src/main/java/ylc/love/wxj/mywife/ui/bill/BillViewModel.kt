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
import ylc.love.wxj.mywife.utils.LiveDataBus
import ylc.love.wxj.mywife.utils.LogUtil
import java.util.*

class BillViewModel : BaseViewModel() {

    private val _dataList = MutableLiveData<List<BillBean>>()
    val dateList: LiveData<List<BillBean>> = _dataList

    private val _typeList = MutableLiveData<MutableList<BillTypeBean>>()
    val typeList: LiveData<MutableList<BillTypeBean>> = _typeList

    val currTime: MutableLiveData<DateInterval> =
        MutableLiveData(DateInterval(DateUtils.getCurrMonthStartTime(), DateUtils.curTime))
    val currType: MutableLiveData<Int> = MutableLiveData(0)

    fun queryBills() = runOnThread(work = {
        currTime.value?.let {
            val billDao = AppDataBase.instance.billBeanDao()
            val list = if (currType.value!! > 0) billDao.selectByDateAndType(
                it.startTime,
                it.endTime,
                currType.value!!
            ) else billDao.selectByDate(it.startTime, it.endTime)
            setValueOnMain(_dataList, list)
        }
    }, catch = { e ->
        e.printStackTrace()
        LogUtil.log(e.toString())
    })

    fun queryBillTypes() = runOnThread(work = {
        val typeDao = AppDataBase.instance.billTypeBeanDao()
        val list = typeDao.selectAll()
        list.add(0, BillTypeBean("全部", 0))
        setValueOnMain(_typeList, list)
    }, catch = { e ->
        e.printStackTrace()
        LogUtil.log(e.toString())
    })


    fun addBean(bill: BillBean) = runOnThread(work = {
        val billBeanDao = AppDataBase.instance.billBeanDao()
        billBeanDao.insert(bill)
    },
        catch = { e ->
            e.printStackTrace()
            LogUtil.log(e.toString())
        })

    fun addBeanType(type: BillTypeBean) = runOnThread(work = {
        val billBeanDao = AppDataBase.instance.billTypeBeanDao()
        val insert = billBeanDao.insert(type)
        queryBillTypes()
    },
        catch = { e ->
            e.printStackTrace()
            LogUtil.log(e.toString())
        })
}
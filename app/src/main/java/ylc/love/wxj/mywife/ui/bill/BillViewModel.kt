package ylc.love.wxj.mywife.ui.bill

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ylc.love.wxj.mywife.base.BaseViewModel
import ylc.love.wxj.mywife.model.DateBean

class BillViewModel : BaseViewModel() {

    private val _dataList = MutableLiveData<List<DateBean>>()
    val dateList: LiveData<List<DateBean>> = _dataList
    val currTime:MutableLiveData<Long> = MutableLiveData(0L)

    fun queryBeans() = runOnThread(work = {
//        val dateBeanDao = AppDataBase.instance.eventBeanDao()
//        val list = dateBeanDao.selectBeansByTime(2,currTime.value?:0L)
//        val count= list.size
//        val resultList = list.sortedByDescending {
//            it.date
//        }
//        setValueOnMain(_dataList, resultList)
    })
}
package ylc.love.wxj.mywife.ui.date

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ylc.love.wxj.mywife.base.BaseViewModel
import ylc.love.wxj.mywife.model.AppDataBase
import ylc.love.wxj.mywife.model.DateBean
import ylc.love.wxj.mywife.utils.DateUtils

class DateViewModel : BaseViewModel() {

    private val _dataList = MutableLiveData<List<DateBean>>()
    val dateList: LiveData<List<DateBean>> = _dataList

    fun getAllDateBeans() = runOnThread(work = {
        val dateBeanDao = AppDataBase.instance.eventBeanDao()
        val list = dateBeanDao.selectAll()
        val count= list.size
        repeat(count) {
            list[it].needDay = DateUtils.getNeed(list[it].date)
            list[it].goneDay = DateUtils.getGone(list[it].date)
        }
        val resultList = list.sortedBy {
            it.needDay
        }
        setValueOnMain(_dataList, resultList)
    })

}
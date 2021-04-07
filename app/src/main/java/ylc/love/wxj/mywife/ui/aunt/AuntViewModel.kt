package ylc.love.wxj.mywife.ui.aunt

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ylc.love.wxj.mywife.base.BaseViewModel
import ylc.love.wxj.mywife.model.AppDataBase
import ylc.love.wxj.mywife.model.AuntBean
import ylc.love.wxj.mywife.model.DateBean
import ylc.love.wxj.mywife.utils.LogUtil

/**
 *@author YLC-D
 *@create on 2021/1/28 15
 *说明:
 */
class AuntViewModel:BaseViewModel() {

    private val _dataList = MutableLiveData<List<AuntBean>>()
    val dateList: LiveData<List<AuntBean>> = _dataList


    fun getAllAuntBeans() = runOnThread(work = {
        val dateBeanDao = AppDataBase.instance.auntBeanDao()
        val list = dateBeanDao.selectAll()
        _dataList.postValue(list)
        LogUtil.log(list.toString())
//        val count= list.size
//        repeat(count) {
//            list[it].needDay = DateUtils.getNeed(list[it].date)
//            list[it].goneDay = DateUtils.getGone(list[it].date)
//        }
//        val resultList = list.sortedBy {
//            it.needDay
//        }
//        setValueOnMain(_dataList, resultList)
    })
}
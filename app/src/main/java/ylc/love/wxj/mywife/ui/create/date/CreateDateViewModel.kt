package ylc.love.wxj.mywife.ui.create.date

import android.text.Editable
import androidx.lifecycle.MutableLiveData
import ylc.love.wxj.mywife.base.BaseViewModel
import ylc.love.wxj.mywife.common.SimpleTextWatcher
import ylc.love.wxj.mywife.expand.toast
import ylc.love.wxj.mywife.model.AppDataBase
import ylc.love.wxj.mywife.model.DateBean
import ylc.love.wxj.mywife.utils.LogUtil

/**
 *@author YLC-D
 *@create on 2021/1/28 10
 *说明:
 */
class CreateDateViewModel : BaseViewModel() {
    val eventTitle: MutableLiveData<String> = MutableLiveData()
    val eventDate: MutableLiveData<Long> = MutableLiveData()
    val eventDes: MutableLiveData<String> = MutableLiveData()
    val saveState: MutableLiveData<Boolean> = MutableLiveData()

    val desWatcher = object : SimpleTextWatcher {
            override fun afterTextChanged(s: Editable?) {
                eventDes.postValue(s.toString())
            }
    }

    val titleWatcher = object :SimpleTextWatcher{
        override fun afterTextChanged(s: Editable?) {
            eventTitle.postValue(s.toString())
        }
    }

    fun saveEvent() {
        if (eventTitle.value == null) {
            "请填写事件标题".toast()
            return
        }
        if (eventDate.value == null) {
            "请选择事件时间".toast()
            return
        }

        insertBean()
    }

    private fun insertBean() = runOnThread(work = {
        val eventBeanDao = AppDataBase.instance.eventBeanDao()
        val currMill = System.currentTimeMillis()
        val bean = DateBean(
            currMill,
            eventTitle.value,
            eventDes.value,
            eventDate.value ?: currMill,
            currMill
        )
        val insert = eventBeanDao.insert(bean)
        saveState.postValue(insert > 0)
    }, catch = { e -> LogUtil.log(e.message.toString()) })
}
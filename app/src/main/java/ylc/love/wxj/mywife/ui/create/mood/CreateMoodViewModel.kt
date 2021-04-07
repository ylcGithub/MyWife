package ylc.love.wxj.mywife.ui.create.mood

import android.text.Editable
import androidx.lifecycle.MutableLiveData
import ylc.love.wxj.mywife.base.BaseViewModel
import ylc.love.wxj.mywife.common.SimpleTextWatcher
import ylc.love.wxj.mywife.expand.toast
import ylc.love.wxj.mywife.model.AppDataBase
import ylc.love.wxj.mywife.model.MoodBean

/**
 *@author YLC-D
 *@create on 2021/2/19 10
 *说明:
 */
class CreateMoodViewModel : BaseViewModel() {
    private val title: MutableLiveData<String> = MutableLiveData()
    private val content: MutableLiveData<String> = MutableLiveData()
    val saveState: MutableLiveData<Boolean> = MutableLiveData()

    val titleWatcher = object : SimpleTextWatcher{
        override fun afterTextChanged(s: Editable?) {
            title.postValue(s.toString())
        }
    }

    val contentWatcher = object : SimpleTextWatcher{
        override fun afterTextChanged(s: Editable?) {
            content.postValue(s.toString())
        }
    }

    fun saveMoodDiary() {
        if (title.value == null) {
            "请输入心情标题".toast()
        }
        if (content.value == null) {
            "请填写好心情日记".toast()
        }
        insertBean()
    }

    private fun insertBean() = runOnThread(work = {
        val moodBeanDao = AppDataBase.instance.moodBeanDao()
        val mill = System.currentTimeMillis()
        val bean = MoodBean(mill, title.value, content.value, mill)
        val insert = moodBeanDao.insert(bean)
        saveState.postValue(insert > 0)
    })
}
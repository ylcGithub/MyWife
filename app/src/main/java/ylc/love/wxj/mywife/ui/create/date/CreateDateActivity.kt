package ylc.love.wxj.mywife.ui.create.date

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.android.synthetic.main.activity_create_date.*
import ylc.love.wxj.mywife.R
import ylc.love.wxj.mywife.base.BaseMvvmActivity
import ylc.love.wxj.mywife.databinding.ActivityCreateDateBinding
import ylc.love.wxj.mywife.expand.toast
import ylc.love.wxj.mywife.utils.DateUtils
import java.util.*

/**
 *@author YLC-D
 *@create on 2021/1/28 10
 *说明:
 */
class CreateDateActivity : BaseMvvmActivity<CreateDateViewModel, ActivityCreateDateBinding>() {
    override fun getLayoutId(): Int = R.layout.activity_create_date
    override fun getViewModel(): CreateDateViewModel =
        getViewModelProvider(this).get(CreateDateViewModel::class.java)

    override fun initData() {
        mBinding.click = ClickProxy()
        mBinding.vm = mViewModel
        val time = DateUtils.getCurDateStr("yyyy-MM-dd")
        tv_event_time.text = time
        mViewModel.eventDate.postValue(DateUtils.getDateFromStr(time, "yyyy-MM-dd"))

        mViewModel.saveState.observe(this, {
            if (it) {
                "保存成功".toast()
                finishActivity()
            } else {
                "保存失败".toast()
            }
        })
    }

    inner class ClickProxy {

        fun back() {
            finishActivity()
        }

        @SuppressLint("SetTextI18n")
        @RequiresApi(Build.VERSION_CODES.N)
        fun selectDate() {
            val calender = Calendar.getInstance()
            val dialog = DatePickerDialog(
                this@CreateDateActivity,
                { _, year, month, dayOfMonth ->
                    val time = "$year-${month + 1}-$dayOfMonth"
                    tv_event_time.text = time
                    mViewModel.eventDate.value = DateUtils.getDateFromStr(time, "yyyy-MM-dd")
                },
                calender.get(Calendar.YEAR),
                calender.get(Calendar.MONTH),
                calender.get(Calendar.DAY_OF_MONTH)
            )
            dialog.show()
        }

        fun saveEvent() {
            mViewModel.saveEvent()
        }
    }

}
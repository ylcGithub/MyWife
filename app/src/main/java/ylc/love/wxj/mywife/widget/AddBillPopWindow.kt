package ylc.love.wxj.mywife.widget

import android.app.DatePickerDialog
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.TypedValue
import android.view.Gravity.CENTER
import android.view.View
import androidx.databinding.DataBindingUtil
import razerdp.basepopup.BasePopupWindow
import ylc.love.wxj.mywife.R
import ylc.love.wxj.mywife.databinding.PopWindowAddBillBinding
import ylc.love.wxj.mywife.expand.toast
import ylc.love.wxj.mywife.model.AppDataBase
import ylc.love.wxj.mywife.model.BillBean
import ylc.love.wxj.mywife.model.BillTypeBean
import ylc.love.wxj.mywife.utils.DateUtils
import ylc.love.wxj.mywife.utils.ResUtil
import java.util.*
import java.util.regex.Pattern

/**
 *@author YLC-D
 *@create on 2021/4/27 15
 *说明:
 */
class AddBillPopWindow(context: Context) : BasePopupWindow(context) {
    private lateinit var bind: PopWindowAddBillBinding
    private var type: BillTypeBean = BillTypeBean("", -1)
    private val tvList = mutableListOf<AppTextView>()
    private val tealColor = ResUtil.getColor(context, R.color.teal_700)
    private val transColor = ResUtil.getColor(context, R.color.transparent)
    private val whiteColor = ResUtil.getColor(context, R.color.white)

    override fun onCreateContentView(): View {
        val view = createPopupById(R.layout.pop_window_add_bill)
        bind = DataBindingUtil.bind(view)!!
        bind.tvTime.text = DateUtils.getCurDateStr("yyyy-MM-dd")
        setOutSideDismiss(false)
        bind.ivCancel.setOnClickListener { dismiss() }
        return bind.root
    }

    private val watcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }

        override fun afterTextChanged(s: Editable?) {
            val start = bind.etSpend.selectionStart
            val end = bind.etSpend.selectionEnd
            s?.let {
                if (!isRightNum(it.toString())) {
                    //删除多余输入的字（不会显示出来）
                    s.delete(start - 1, end)
                    bind.etSpend.text = s
                    bind.etSpend.setSelection(s.length)
                }
            }
        }
    }

    fun isRightNum(number: String): Boolean {
        if (number.isEmpty()) return true
        val compile = Pattern.compile("^\\d+\\.?\\d{0,2}$")
        return compile.matcher(number).matches()
    }

    override fun showPopupWindow() {
        popupGravity = CENTER
        super.showPopupWindow()
        bind.etSpend.addTextChangedListener(watcher)
        setTypeList()
    }

    fun setTypeList() {
        val list = AppDataBase.instance.billTypeBeanDao().selectAll()
        bind.btnSave.setOnClickListener { save() }
        bind.tvTime.setOnClickListener { selectTime() }
        bind.wordWrap.removeAllViews()
        list.add(BillTypeBean(" + ", 0L,-1))
        val count = list.size
        val textSize = ResUtil.getDimen(context, R.dimen.font_size_18)
        val strokeWidth = ResUtil.getDimen(context, R.dimen.widget_size_1).toInt()
        val radius = ResUtil.getDimen(context, R.dimen.widget_size_10).toInt()
        var tv: AppTextView
        for (index in 0 until count) {
            tv = AppTextView(context)
            tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize)
            tv.setTextColor(tealColor)
            tv.strokeColor = tealColor
            tv.strokeWidth = strokeWidth
            tv.radius = radius
            tv.text = list[index].type
            tv.setOnClickListener {
                selectType(it as AppTextView, list[index])
            }
            tv.setBgSelector()
            tvList.add(tv)
            bind.wordWrap.addView(tv)
        }
    }


    private fun selectType(tv: AppTextView, type: BillTypeBean) {
        if (type.id == -1) {
            AddBillTypePopWindow(context).showPopupWindow()
            return
        }
        tv.isSelected = !tv.isSelected
        if (tv.isSelected) {
            this.type = type
            this.type.lastUse = DateUtils.curTime
            tv.bgColor = tealColor
            tv.setTextColor(whiteColor)
        } else {
            this.type = BillTypeBean("", -0L,-1)
            tv.bgColor = transColor
            tv.setTextColor(tealColor)
        }
        tv.setBgSelector()

        val count = tvList.size
        for (i in 0 until count) {
            val cTv = tvList[i]
            if (cTv != tv && cTv.isSelected) {
                cTv.isSelected = false
                cTv.bgColor = transColor
                cTv.setTextColor(tealColor)
                cTv.setBgSelector()
            }
        }
    }

    private fun selectTime() {
        val calender = Calendar.getInstance()
        val dialog = DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                val time = "$year-${month + 1}-$dayOfMonth"
                bind.tvTime.text = time
            },
            calender.get(Calendar.YEAR),
            calender.get(Calendar.MONTH),
            calender.get(Calendar.DAY_OF_MONTH)
        )
        dialog.show()
    }

    var listener: SaveListener? = null

    interface SaveListener {
        fun onClick(type: BillTypeBean, bill: BillBean)
    }

    private fun save() {
        if (type.id == -1) {
            "请选择账单类型".toast()
            return
        }
        if (bind.etSpend.text.isNullOrEmpty()) {
            "填写账单费用".toast()
            return
        }

        val bean = BillBean(
            DateUtils.curTime,
            type.id,
            type.type,
            DateUtils.getDateFromStr(bind.tvTime.text.toString(), "yyyy-MM-dd"),
            bind.etSpend.text.toString().toFloat(),
            bind.etDes.text.toString()
        )
        listener?.onClick(this.type, bean)
    }
}
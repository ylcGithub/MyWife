package ylc.love.wxj.mywife.widget

import android.app.DatePickerDialog
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.TypedValue
import android.view.Gravity.CENTER
import android.view.LayoutInflater
import android.view.View
import androidx.databinding.DataBindingUtil
import razerdp.basepopup.BasePopupWindow
import ylc.love.wxj.mywife.R
import ylc.love.wxj.mywife.databinding.BillTypeItemBinding
import ylc.love.wxj.mywife.databinding.PopWindowAddBillBinding
import ylc.love.wxj.mywife.expand.toast
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

    override fun onCreateContentView(): View {
        val view = createPopupById(R.layout.pop_window_add_bill)
        bind = DataBindingUtil.bind(view)!!
        bind.tvTime.text = DateUtils.getCurDateStr("yyyy-MM-dd")
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
        val compile = Pattern.compile("^\\d+\\.?\\d{0,2}$")
        return compile.matcher(number).matches()
    }

    override fun showPopupWindow() {
        popupGravity = CENTER
        super.showPopupWindow()
        bind.etSpend.addTextChangedListener(watcher)
    }

    fun setTypeList(list: MutableList<BillTypeBean>) {
        val tvList = mutableListOf<AppTextView>()
        list.removeAt(0)
        bind.btnSave.setOnClickListener { save() }
        bind.tvTime.setOnClickListener { selectTime() }
        bind.wordWrap.removeAllViews()
        list.add(BillTypeBean("+", -1))
        val count = list.size
        var tv: AppTextView
        for (index in 0 until count) {
            tv = LayoutInflater.from(context).inflate(R.layout.bill_type_item,null) as AppTextView
            tv.text = list[index].type
            tv.setOnClickListener {
                selectType(list[index])
                for (i in 0 until tvList.size){

                }
            }
            tvList.add(tv)
            bind.wordWrap.addView(tv)
        }
    }


    private fun selectType(type:BillTypeBean){
        this.type = type
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
        fun onClick(type: BillBean)
    }

    private fun save() {
        if (type.id == -1) {
            "请选择账单类型".toast()
            return
        }
        if (bind.etSpend.text == null) {
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
        listener?.onClick(bean)
    }
}
package ylc.love.wxj.mywife.widget

import android.content.Context
import android.view.Gravity
import android.view.View
import androidx.databinding.DataBindingUtil
import razerdp.basepopup.BasePopupWindow
import ylc.love.wxj.mywife.R
import ylc.love.wxj.mywife.config.ParamsKey
import ylc.love.wxj.mywife.databinding.AddBillTypePopWindowBinding
import ylc.love.wxj.mywife.expand.toast
import ylc.love.wxj.mywife.model.AppDataBase
import ylc.love.wxj.mywife.model.BillTypeBean
import ylc.love.wxj.mywife.utils.DateUtils
import ylc.love.wxj.mywife.utils.LiveDataBus

/**
 *@author YLC-D
 *@create on 2021/4/30 14
 *说明:
 */
class AddBillTypePopWindow(context: Context) : BasePopupWindow(context) {
    private lateinit var bind: AddBillTypePopWindowBinding
    override fun onCreateContentView(): View {
        val view = createPopupById(R.layout.add_bill_type_pop_window)
        bind = DataBindingUtil.bind(view)!!
        bind.btnSave.setOnClickListener {
            saveBillType()
        }
        bind.ivCancel.setOnClickListener { dismiss()}
        setOutSideDismiss(false)
        return bind.root
    }

    override fun showPopupWindow() {
        popupGravity = Gravity.CENTER
        super.showPopupWindow()
    }

    private fun saveBillType(){
        val newType = bind.etBillType.text.toString()
        if(newType.isEmpty()){
            "请填写账单类型".toast()
            return
        }
        val typeDao = AppDataBase.instance.billTypeBeanDao()
        val list = typeDao.selectAll()
        list.forEach {
            if(it.type == newType){
                return
            }
        }

        val type = BillTypeBean(newType,DateUtils.curTime)
        //保存该账单类型
        LiveDataBus.send(ParamsKey.SAVE_BILL_TYPE,type)
        dismiss()
    }

}
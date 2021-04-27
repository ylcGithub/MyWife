package ylc.love.wxj.mywife.widget

import android.content.Context
import android.view.View
import androidx.databinding.DataBindingUtil
import razerdp.basepopup.BasePopupWindow
import ylc.love.wxj.mywife.R
import ylc.love.wxj.mywife.databinding.PopWindowBillFilterBinding

/**
 *@author YLC-D
 *@create on 2021/4/27 15
 *说明:
 */
class BillFilterPopWindow(context: Context):BasePopupWindow(context) {
    private lateinit var bind:PopWindowBillFilterBinding
    override fun onCreateContentView(): View {
        val view = createPopupById(R.layout.pop_window_bill_filter)
        bind = DataBindingUtil.bind(view)!!
        return bind.root
    }



}
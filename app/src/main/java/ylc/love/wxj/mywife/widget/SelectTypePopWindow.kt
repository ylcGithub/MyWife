package ylc.love.wxj.mywife.widget

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import razerdp.basepopup.BasePopupWindow
import ylc.love.wxj.mywife.R
import ylc.love.wxj.mywife.base.BaseOneLayoutAdapter
import ylc.love.wxj.mywife.base.BaseViewHolder
import ylc.love.wxj.mywife.databinding.PopTypeListItemBinding
import ylc.love.wxj.mywife.databinding.PopWindowSelectItemBinding
import ylc.love.wxj.mywife.model.SelectTypeBean
import ylc.love.wxj.mywife.utils.ResUtil

/**
 * @author Administrator
 * @create on 2021/1/29 0029
 * 说明:
 */
class SelectTypePopWindow(context: Context) : BasePopupWindow(context) {
    private lateinit var bind: PopWindowSelectItemBinding
    override fun onCreateContentView(): View {
        val view = createPopupById(R.layout.pop_window_select_item)
        bind = DataBindingUtil.bind(view)!!
        return bind.root
    }

    override fun showPopupWindow(anchorView: View?) {
        super.showPopupWindow(anchorView)
        bind.rcvTypeList.adapter = mAdapter
        bind.rcvTypeList.layoutManager = LinearLayoutManager(context)
        if (bind.rcvTypeList.itemDecorationCount == 0) {
            bind.rcvTypeList.addItemDecoration(
                CustomItemDecoration(
                    CustomItemDecoration.Type.VER,
                    ResUtil.getColor(context, R.color.transparent)
                )
                    .apply {
                        space = ResUtil.getDimen(context, R.dimen.widget_size_6).toInt()
                        mostBottom= space
                    })
        }
        mAdapter.updateList(list,true)
    }

    var list: List<SelectTypeBean>? = null
    private val mAdapter = object :
        BaseOneLayoutAdapter<SelectTypeBean, PopTypeListItemBinding>(R.layout.pop_type_list_item) {
        override fun itemIsSame(oldItem: SelectTypeBean, newItem: SelectTypeBean): Boolean {
            return oldItem == newItem
        }

        @SuppressLint("SetTextI18n")
        override fun onBindItem(
            bind: PopTypeListItemBinding,
            item: SelectTypeBean,
            holder: BaseViewHolder
        ) {
            bind.tvTypeTitle.text = "${item.id}."
            bind.tvType.text = item.type
            bind.root.setOnClickListener {
                listener?.onClick(item.id,item.type)
                dismiss()
            }
        }
    }

    var listener: CustomClickListener? = null

    interface CustomClickListener {
        fun onClick(id:Int,type: String)
    }

}
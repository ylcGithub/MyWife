package ylc.love.wxj.mywife.ui.bill

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_bill.*
import ylc.love.wxj.mywife.R
import ylc.love.wxj.mywife.base.BaseFragment
import ylc.love.wxj.mywife.base.BaseOneLayoutAdapter
import ylc.love.wxj.mywife.base.BaseViewHolder
import ylc.love.wxj.mywife.databinding.BillListItemBinding
import ylc.love.wxj.mywife.databinding.BillTypeItemBinding
import ylc.love.wxj.mywife.databinding.FragmentBillBinding
import ylc.love.wxj.mywife.model.*
import ylc.love.wxj.mywife.utils.DateUtils
import ylc.love.wxj.mywife.utils.ResUtil
import ylc.love.wxj.mywife.widget.AppTextView
import ylc.love.wxj.mywife.widget.CustomItemDecoration
import java.util.*

class BillFragment : BaseFragment<BillViewModel,FragmentBillBinding>() {
    override fun getLayoutId(): Int = R.layout.fragment_bill

    override fun getViewModel(): BillViewModel = getViewModelProvider(this).get(BillViewModel::class.java)

    override fun initData() {
        mBinding.click = ClickProxy()
        rcv_bill.adapter = mAdapter
        rcv_bill.layoutManager = LinearLayoutManager(mContext)
        add_new_bill.setOnClickListener {
            mViewModel.addBean()
        }
        mViewModel.currTime.observe(this,{
            mViewModel.queryBills()
        })
        if(rcv_bill.itemDecorationCount == 0){
            rcv_bill.addItemDecoration(CustomItemDecoration(CustomItemDecoration.Type.VER).apply {
                space = ResUtil.getDimen(mContext, R.dimen.widget_size_15).toInt()
                mostTop = space
                mostLeft = space
                mostRight = space
                mostBottom = space
            })
        }
        mViewModel.currTime.value = DateInterval(DateUtils.getCurrMonthStartTime(),DateUtils.getCurrMonthEndTime())
        mViewModel.dateList.observe(this,{
            mAdapter.updateList(it,true)
        })
        mViewModel.typeList.observe(this,{
            typeAdapter.updateList(it,true)
        })
        rcv_type.adapter = typeAdapter
        rcv_type.layoutManager = LinearLayoutManager(mContext).also {it.orientation = RecyclerView.HORIZONTAL }
        if(rcv_type.itemDecorationCount == 0){
            rcv_type.addItemDecoration(CustomItemDecoration(CustomItemDecoration.Type.HOR).apply {
                space = ResUtil.getDimen(mContext, R.dimen.widget_size_10).toInt()
                mostLeft = space
                mostRight = space
            })
        }
    }

    private fun showDateSelectDialog(v:View){
        val calender = Calendar.getInstance()
        val dialog = DatePickerDialog(
            mContext,
            { _, year, month, dayOfMonth ->
                val time = "$year-${month + 1}-$dayOfMonth"
//                tv_event_time.text = time
//                mViewModel.currTime.postValue(DateUtils.getDateFromStr(time, "yyyy-MM"))
            },
            calender.get(Calendar.YEAR),
            calender.get(Calendar.MONTH),
            calender.get(Calendar.DAY_OF_MONTH)
        )
        dialog.show()
    }

    inner class ClickProxy{
        fun unfoldPutAway(v:View){
           v as AppTextView
            if(v.isSelected){
                //收起
                mBinding.llFilter.visibility = View.GONE
                v.text = getString(R.string.filter)
            }else{
                //展开
                mBinding.llFilter.visibility = View.VISIBLE
                v.text = getString(R.string.put_away)
                if(mViewModel.typeList.value == null){
                    mViewModel.queryBillTypes()
                }
            }
            v.isSelected = !v.isSelected
        }
    }



    private val mAdapter = object :BaseOneLayoutAdapter<BillBean,BillListItemBinding>(R.layout.bill_list_item){
        override fun itemIsSame(oldItem: BillBean, newItem: BillBean): Boolean = oldItem == newItem

        @SuppressLint("SetTextI18n")
        override fun onBindItem(
            bind: BillListItemBinding,
            item: BillBean,
            holder: BaseViewHolder) {
            bind.tvType.text = item.type
            bind.tvTime.text = DateUtils.getDateStr(item.date,"yyyy-MM-dd HH:mm")
            bind.tvWeek.text = DateUtils.getDateWeek(item.date)
            bind.tvSpend.text = "-${item.spend}"
            item.des?.let {
                bind.tvDes.text = it
            }
        }
    }

    private val typeAdapter = object : BaseOneLayoutAdapter<BillTypeBean,BillTypeItemBinding>(R.layout.bill_type_item){
        override fun itemIsSame(oldItem: BillTypeBean, newItem: BillTypeBean): Boolean = oldItem == newItem
        override fun onBindItem(
            bind: BillTypeItemBinding,
            item: BillTypeBean,
            holder: BaseViewHolder
        ) {
            bind.tvType.text = item.type
            bind.tvType.setOnClickListener {
                mViewModel.currType.value = item.id
            }
        }
    }
}
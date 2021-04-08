package ylc.love.wxj.mywife.ui.aunt

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_create_date.*
import ylc.love.wxj.mywife.R
import ylc.love.wxj.mywife.base.BaseFragment
import ylc.love.wxj.mywife.base.BaseOneLayoutAdapter
import ylc.love.wxj.mywife.base.BaseViewHolder
import ylc.love.wxj.mywife.databinding.AuntListItemBinding
import ylc.love.wxj.mywife.databinding.FragmentAuntBinding
import ylc.love.wxj.mywife.model.AppDataBase
import ylc.love.wxj.mywife.model.AuntBean
import ylc.love.wxj.mywife.utils.DateUtils
import ylc.love.wxj.mywife.utils.LogUtil
import ylc.love.wxj.mywife.utils.ResUtil
import ylc.love.wxj.mywife.widget.CustomItemDecoration
import java.util.*

/**
 *@author YLC-D
 *@create on 2021/1/28 15
 *说明:
 */
class AuntFragment : BaseFragment<AuntViewModel, FragmentAuntBinding>() {
    override fun getLayoutId(): Int = R.layout.fragment_aunt

    override fun getViewModel(): AuntViewModel =
        getViewModelProvider(this).get(AuntViewModel::class.java)

    override fun initData() {
        mBinding.click = ClickProxy()
        mBinding.rcvAuntList.adapter = listAdapter
        mBinding.rcvAuntList.layoutManager = LinearLayoutManager(mContext)
        val customItemDecoration = CustomItemDecoration(CustomItemDecoration.Type.VER)
            .also {
                it.round = ResUtil.getDimen(mContext, R.dimen.widget_size_10).toInt()
                it.space = ResUtil.getDimen(mContext, R.dimen.widget_size_10).toInt()
            }
        mBinding.rcvAuntList.addItemDecoration(customItemDecoration)
        mViewModel.dateList.observe(this, {
            listAdapter.updateList(it, true)
        })
        mViewModel.getAllAuntBeans()
    }

    private val listAdapter =
        object : BaseOneLayoutAdapter<AuntBean, AuntListItemBinding>(R.layout.aunt_list_item) {
            override fun itemIsSame(oldItem: AuntBean, newItem: AuntBean): Boolean =
                oldItem.id == newItem.id

            @SuppressLint("SetTextI18n")
            override fun onBindItem(
                bind: AuntListItemBinding,
                item: AuntBean,
                holder: BaseViewHolder
            ) {
                if (item.id == -1L) {
                    bind.tvPre.visibility = View.VISIBLE
                } else {
                    bind.tvPre.visibility = View.GONE
                }

                bind.tvTitle.text = DateUtils.getDateStr(item.startTime, "yyyy年MM月")
                bind.tvDes.text = "${DateUtils.getDateStr(item.startTime, "dd")}号开始"
                val used = if (item.usedTime > 0) item.usedTime else "---"
                bind.tvCon.text = "持续时间${used}天"
                bind.item.setOnLongClickListener {
                    addEndTime(item)
                    true
                }
            }
        }
    private fun addEndTime(bean: AuntBean){
        val calender = Calendar.getInstance()
        val dialog = DatePickerDialog(
            mContext,
            { _, year, month, dayOfMonth ->
                val time = "$year-${month + 1}-$dayOfMonth"
                val endTime = DateUtils.getDateFromStr(time, "yyyy-MM-dd")
                val auntBeanDao = AppDataBase.instance.auntBeanDao()
                bean.endTime = endTime
                bean.usedTime = ((endTime - bean.startTime)/86400000).toInt() + 1
                auntBeanDao.insert(bean)
            },
            calender.get(Calendar.YEAR),
            calender.get(Calendar.MONTH),
            calender.get(Calendar.DAY_OF_MONTH)
        )
        dialog.show()
    }

    inner class ClickProxy {
        fun addAunt() {

            val calender = Calendar.getInstance()
            val dialog = DatePickerDialog(
                mContext,
                { _, year, month, dayOfMonth ->
                    val time = "$year-${month + 1}-$dayOfMonth"
                    val startTime = DateUtils.getDateFromStr(time, "yyyy-MM-dd")
                    val auntBeanDao = AppDataBase.instance.auntBeanDao()
                    val auntBean = AuntBean("$year${month + 1}".toLong(), startTime, 0L, 0)
                    val insert = auntBeanDao.insert(auntBean)
                    LogUtil.log("$insert---------")
                },
                calender.get(Calendar.YEAR),
                calender.get(Calendar.MONTH),
                calender.get(Calendar.DAY_OF_MONTH)
            )
            dialog.show()
        }
    }

}
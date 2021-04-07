package ylc.love.wxj.mywife.ui.mood

import android.text.TextUtils
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_mood.*
import ylc.love.wxj.mywife.R
import ylc.love.wxj.mywife.base.BaseFragment
import ylc.love.wxj.mywife.base.BaseOneLayoutAdapter
import ylc.love.wxj.mywife.base.BaseViewHolder
import ylc.love.wxj.mywife.databinding.FragmentMoodBinding
import ylc.love.wxj.mywife.databinding.MoodListItemBinding
import ylc.love.wxj.mywife.model.MoodBean
import ylc.love.wxj.mywife.ui.create.mood.CreateMoodActivity
import ylc.love.wxj.mywife.utils.DateUtils

class MoodFragment : BaseFragment<MoodViewModel, FragmentMoodBinding>() {
    override fun getLayoutId(): Int = R.layout.fragment_mood

    override fun getViewModel(): MoodViewModel =
        getViewModelProvider(this).get(MoodViewModel::class.java)

    override fun initData() {
        rcv_mood.adapter = adapter
        rcv_mood.layoutManager = LinearLayoutManager(mContext)
        mViewModel.dateList.observe(this, {
            adapter.updateList(it, true)
        })
        mViewModel.getAllMoodBeans()
    }

    private val adapter =
        object : BaseOneLayoutAdapter<MoodBean, MoodListItemBinding>(R.layout.mood_list_item) {
            override fun itemIsSame(oldItem: MoodBean, newItem: MoodBean): Boolean {
                return oldItem.id == newItem.id
            }

            override fun onBindItem(
                bind: MoodListItemBinding,
                item: MoodBean,
                holder: BaseViewHolder
            ) {
                if (item.id == 0L) {
                    bind.rlNormal.visibility = View.GONE
                    bind.llCreate.visibility = View.VISIBLE
                    bind.llCreate.setOnClickListener{
                        toNextActivity(CreateMoodActivity::class.java)
                    }
                } else {
                    bind.rlNormal.visibility = View.VISIBLE
                    bind.llCreate.visibility = View.GONE
                    bind.tvTitle.text = item.title
                    bind.tvTime.text = DateUtils.getDateStr(item.createDate, "yyyy-MM-dd")
                    bind.tvContent.text = item.des
                    bind.tvContent.setOnClickListener {
                        if (bind.tvContent.maxLines == 2) {
                            bind.tvContent.maxLines = item.des?.length ?: 40 /20
                            bind.tvContent.ellipsize = null
                        } else {
                            bind.tvContent.maxLines = 2
                            bind.tvContent.ellipsize = TextUtils.TruncateAt.END
                        }
                    }
                }
            }
        }
}
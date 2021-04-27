package ylc.love.wxj.mywife.ui.customview

import com.jaeger.library.StatusBarUtil
import ylc.love.wxj.mywife.R
import ylc.love.wxj.mywife.base.BaseMvvmActivity
import ylc.love.wxj.mywife.databinding.ActivitySumCustomViewBinding

/**
 *@author YLC-D
 *@create on 2021/3/17 17
 *说明:
 */
class SumCustomViewActivity : BaseMvvmActivity<SumCustomViewModel, ActivitySumCustomViewBinding>() {

    override fun getViewModel(): SumCustomViewModel =
        getViewModelProvider(this).get(SumCustomViewModel::class.java)

    override fun getLayoutId(): Int = R.layout.activity_sum_custom_view

    override fun initData() {
        mBinding.click = ClickProxy()
        mBinding.vm = mViewModel
        StatusBarUtil.setTranslucentForImageView(this,0,null)
    }

    inner class ClickProxy {
        fun back() {
            finishActivity()
        }
    }
}
package ylc.love.wxj.mywife.ui.create.mood

import kotlinx.android.synthetic.main.activity_create_mood.*
import ylc.love.wxj.mywife.R
import ylc.love.wxj.mywife.base.BaseMvvmActivity
import ylc.love.wxj.mywife.databinding.ActivityCreateMoodBinding
import ylc.love.wxj.mywife.expand.toast

/**
 *@author YLC-D
 *@create on 2021/2/19 10
 *说明:
 */
class CreateMoodActivity : BaseMvvmActivity<CreateMoodViewModel, ActivityCreateMoodBinding>() {
    override fun getViewModel(): CreateMoodViewModel =
        getViewModelProvider(this).get(CreateMoodViewModel::class.java)

    override fun getLayoutId(): Int = R.layout.activity_create_mood

    override fun initData() {
        mBinding.click = ClickProxy()
        mBinding.vm = mViewModel
        mViewModel.saveState.observe(this, {
            if (it) {
                "心情日记保存成功".toast()
                finishActivity()
            } else {
                "心情日记保存失败".toast()
            }
        })
    }

    inner class ClickProxy {
        fun back() {
            finishActivity()
        }

        fun saveMood() {
            mViewModel.saveMoodDiary()
        }
    }
}
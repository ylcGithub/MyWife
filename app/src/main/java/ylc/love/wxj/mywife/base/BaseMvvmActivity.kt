package ylc.love.wxj.mywife.base

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import ylc.love.wxj.mywife.expand.toast

/**
 *@author YLC-D
 *@create on 2021/1/18 11
 *说明:
 */
abstract class BaseMvvmActivity<VM : BaseViewModel, B : ViewDataBinding> : BaseActivity() {
    protected lateinit var mBinding: B
    protected lateinit var mViewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        needSetLayout = false
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, getLayoutId())
        mBinding.lifecycleOwner = this
        mViewModel = getViewModel()
        lifecycle.addObserver(mViewModel)
    }

    abstract fun getViewModel(): VM


    override fun onDestroy() {
        super.onDestroy()
        lifecycle.removeObserver(mViewModel)
    }

    override fun onResume() {
        if(isCreated){
            loadingInit()
        }
        super.onResume()
    }

    private fun loadingInit(){
        mViewModel.errorMsg.observe(this,{
            it.toast()
        })
    }
}
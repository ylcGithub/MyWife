package ylc.love.wxj.mywife.base

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import ylc.love.wxj.mywife.R
import ylc.love.wxj.mywife.config.ParamsKey
import ylc.love.wxj.mywife.utils.AppManager
import java.lang.ref.WeakReference

/**
 *@author YLC-D
 *@create on 2021/1/27 11
 *说明:
 */
abstract class BaseFragment<VM : BaseViewModel, B : ViewDataBinding> : Fragment() {
    protected lateinit var mBinding:B
    protected lateinit var mContext:AppCompatActivity
    protected lateinit var mViewModel:VM
    private var needInitData:Boolean = false

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = activity as AppCompatActivity
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        mBinding.lifecycleOwner = this
        mViewModel = getViewModel()
        lifecycle.addObserver(mViewModel)
        needInitData = true
        return mBinding.root
    }

    override fun onResume() {
        super.onResume()
        if(needInitData){
            initData()
        }
    }

    private lateinit var mFragmentProvider: ViewModelProvider
    protected open fun getViewModelProvider(fragment: Fragment): ViewModelProvider {
        if (!this::mFragmentProvider.isInitialized) mFragmentProvider = ViewModelProvider(fragment)
        return mFragmentProvider
    }


    override fun onDestroyView() {//账单
        super.onDestroyView()
        lifecycle.removeObserver(mViewModel)
    }

    /**
     * 跳转到下一个页面
     * @param resId id
     * @param args args 要传递的参数
     * @param navOptions options 页面的跳转动画效果
     */
    protected open fun navigate(
        @IdRes resId: Int,
        args: Bundle? = null,
        navOptions: NavOptions? = getNavOptions()
    ) {
        NavHostFragment.findNavController(this).navigate(resId, args, navOptions)
    }

    private var mNavOptions:NavOptions? = null
    private fun getNavOptions(): NavOptions? {
        if (mNavOptions == null) {
            mNavOptions = NavOptions.Builder()
                .setEnterAnim(R.anim.slide_right_in)
                .setExitAnim(R.anim.slide_left_out)
                .setPopEnterAnim(R.anim.slide_left_in)
                .setPopExitAnim(R.anim.slide_right_out)
                .build()
        }
        return mNavOptions
    }

    fun pageBack() {
        NavHostFragment.findNavController(this).navigateUp()
    }

    protected fun toNextActivity(cls: Class<out Activity>, bundle: Bundle? = null) {
        val intent = Intent(mContext, cls)
        intent.putExtra(ParamsKey.ACTIVITY_TO_ACTIVITY_DATA_KEY, bundle)
        AppManager.start(WeakReference(mContext), intent)
    }

    @LayoutRes
    abstract fun getLayoutId():Int
    abstract fun getViewModel():VM
    abstract fun initData()
}
package ylc.love.wxj.mywife

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Build
import android.util.Log
import kotlinx.android.synthetic.main.activity_splash.*
import ylc.love.wxj.mywife.base.BaseActivity
import ylc.love.wxj.mywife.expand.toast
import ylc.love.wxj.mywife.widget.FingerPrintPopWindow

/**
 *@author YLC-D
 *@create on 2021/1/26 10
 *说明:启动屏页面
 */
class SplashActivity : BaseActivity() {

    override fun getLayoutId(): Int = R.layout.activity_splash

    override fun initData() {
        anim_logo.addOffsetAnimListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                Log.d("AnimLogoView", "Offset anim end")
            }
        })
        anim_logo.addGradientAnimListener(object : AnimatorListenerAdapter() {

            override fun onAnimationEnd(animation: Animator) {
                Log.d("AnimLogoView", "Gradient anim end")
                if (BuildConfig.DEBUG) {
                    toNextActivity(MainActivity::class.java)
                    finishActivity()
                } else {
                    verifyFingerPrint()
                }
            }
        })
        anim_logo.startAnimation()
    }

    private fun verifyFingerPrint() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val window = FingerPrintPopWindow(this)
            window.listener = object : FingerPrintPopWindow.ClickListener {
                override fun onCancel() {
                    finishActivity()
                }

                override fun onOk() {
                    toNextActivity(MainActivity::class.java)
                    finishActivity()
                }
            }
            window.showPopupWindow()
        } else {
            "手机的SDK版本太低不支持指纹验证".toast()
        }
    }
}
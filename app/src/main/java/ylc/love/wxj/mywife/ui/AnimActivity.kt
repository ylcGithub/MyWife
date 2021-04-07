package ylc.love.wxj.mywife.ui

import android.view.View
import androidx.interpolator.view.animation.FastOutLinearInInterpolator
import kotlinx.android.synthetic.main.activity_anim.*
import ylc.love.wxj.mywife.R
import ylc.love.wxj.mywife.base.BaseActivity
import ylc.love.wxj.mywife.utils.AnimUtil

/**
 *@author YLC-D
 *@create on 2021/2/5 10
 *说明:
 */
class AnimActivity : BaseActivity() {
    override fun getLayoutId(): Int = R.layout.activity_anim

    override fun initData() {
        start.setOnClickListener(this::onClick)
    }

    private fun onClick(v: View) {
        //动画的差值器学习记录
        testInter()
    }

    private fun testInter() {
        val anim = AnimUtil.getTransY(view, 300f)
        anim.duration = 3000
        //动画加速完成的 ---》加速差值器 (如果属性值有多个 只能作用在第一个值上面)
//        anim.interpolator = AccelerateInterpolator()
        //快速完成动画，超出后再回到动画结束的位置 --》过冲差值器
//        anim.interpolator = OvershootInterpolator()
        //先加速后减速 完成动画 ---》加速减速差值器
//        anim.interpolator = AccelerateDecelerateInterpolator()
        //反向运行，再正向加速运行 ---》预期差值器
//        anim.interpolator = AnticipateInterpolator()
        //反向运行，再正向加速运行超过后再回到原点 ---》预期超调差值器
//        anim.interpolator = AnticipateOvershootInterpolator()
        //动画最后阶段，类似弹球效果 ---》反弹差值器
//        anim.interpolator = BounceInterpolator()
        //周期运动差值器 ---》循环差值器
//        anim.interpolator = CycleInterpolator(2f)
        //减速差值器
//        anim.interpolator = DecelerateInterpolator()
        //匀速 线性差值器
//        anim.interpolator = LinearInterpolator()

        anim.interpolator = FastOutLinearInInterpolator()
        anim.start()
    }
}
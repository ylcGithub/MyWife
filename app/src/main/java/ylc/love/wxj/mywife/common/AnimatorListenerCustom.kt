package ylc.love.wxj.mywife.common

import android.animation.Animator

/**
 *@author YLC-D
 *@create on 2021/3/11 15
 *说明:
 */
interface AnimatorListenerCustom : Animator.AnimatorListener {
    override fun onAnimationStart(animation: Animator?) {

    }
    override fun onAnimationRepeat(animation: Animator?) {
    }

    override fun onAnimationCancel(animation: Animator?) {

    }
}
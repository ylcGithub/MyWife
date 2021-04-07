package ylc.love.wxj.mywife.ui.game

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.graphics.Point
import ylc.love.wxj.mywife.utils.AnimUtil

/**
 *@author YLC-D
 *@create on 2021/3/9 15
 *说明:
 */
object GameAnimUtil {

    fun showScaleAnim(card: Card){
        val set = AnimatorSet()
        set.playTogether(AnimUtil.getScaleX(card, 0f, 1f), AnimUtil.getScaleY(card, 0f, 1f))
        set.duration = 150
        set.start()
    }

    fun createMoveAnim(fromCard: Card,toCard: Card, from: Point, to: Point){
        val anim: ObjectAnimator = if(kotlin.math.abs(to.y - from.y) > 0){
            AnimUtil.getTransY(fromCard,0f,fromCard.width * (to.y - from.y)*1f)
        }else {
            AnimUtil.getTransX(fromCard, 0f, fromCard.width * (to.x - from.x) * 1f)
        }
        val x = fromCard.x
        val y = fromCard.y
        anim.addListener(object : Animator.AnimatorListener{
            override fun onAnimationStart(animation: Animator?) {

            }

            override fun onAnimationEnd(animation: Animator?) {

            }

            override fun onAnimationCancel(animation: Animator?) {

            }

            override fun onAnimationRepeat(animation: Animator?) {
                fromCard.x = x
                fromCard.y = y
                toCard.num = toCard.num + fromCard.num
                fromCard.num = 0
            }
        })
        anim.duration = 100
        anim.start()
    }
}
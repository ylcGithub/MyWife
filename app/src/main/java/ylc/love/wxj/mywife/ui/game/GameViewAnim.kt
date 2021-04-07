package ylc.love.wxj.mywife.ui.game

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Point
import android.util.AttributeSet
import android.widget.FrameLayout
import ylc.love.wxj.mywife.R
import ylc.love.wxj.mywife.common.AnimatorListenerCustom
import ylc.love.wxj.mywife.utils.AnimUtil
import ylc.love.wxj.mywife.utils.ResUtil
import kotlin.math.abs

/**
 *@author YLC-D
 *@create on 2021/3/1 11
 *说明:
 */
class GameViewAnim @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : FrameLayout(context, attrs, defStyle) {

    init {
        setBackgroundColor(ResUtil.getColor(context, R.color.transparent))
        setPadding(GameView.space, GameView.space, GameView.space, GameView.space)
    }

    //point 表示要操作的卡片下标
    fun createMoveAnim(fromCard: Card, toCard: Card, fromPoint: Point, toPoint: Point) {
        val to = getCard(toCard.num, getLayoutParams(toPoint, toCard))
        val from = getCard(fromCard.num, getLayoutParams(fromPoint, fromCard))
        val anim: ObjectAnimator = if (abs(toPoint.y - fromPoint.y) > 0) {
            val transY = (toCard.y - fromCard.y) * 1f
            AnimUtil.getTransY(from, 0f, transY)
        } else {
            val transX = (toCard.x - fromCard.x) * 1f
            AnimUtil.getTransX(from, 0f, transX)
        }
        anim.addListener(object : AnimatorListenerCustom {
            override fun onAnimationEnd(animation: Animator?) {
                removeView(to)
                removeView(from)
            }
        })
        anim.duration = 100L
        anim.start()
    }

    private fun getLayoutParams(point: Point, card: Card): LayoutParams {
        val lp = LayoutParams(card.width, card.height)
        lp.leftMargin = point.x * card.width
        lp.topMargin = point.y * card.width
        return lp
    }

    private fun getCard(n: Int, lp: LayoutParams): Card {
        val c = Card(context, GameView.space, GameView.space, GameView.space, GameView.space)
        addView(c, lp)
        c.num = n
        return c
    }
}
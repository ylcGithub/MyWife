package ylc.love.wxj.mywife.ui.game

import android.annotation.SuppressLint
import android.content.Context
import android.view.Gravity
import android.widget.FrameLayout
import ylc.love.wxj.mywife.R
import ylc.love.wxj.mywife.utils.ResUtil
import ylc.love.wxj.mywife.widget.AppTextView

/**
 *@author YLC-D
 *@create on 2021/3/1 15
 *说明:
 */
@SuppressLint("ViewConstructor")
class Card constructor(
    context: Context,
    pLeft: Int = 0,
    pTop: Int = 0,
    pRight: Int = 0,
    pBottom: Int = 0,
) : FrameLayout(context, null) {

    private var label: AppTextView = AppTextView(context)
    var num: Int = 0
        set(value) {
            field = value
            if (value <= 0) label.text = "" else label.text = value.toString()
            setBg()
        }

    init {
        label.textSize = 24f
        label.gravity = Gravity.CENTER
        label.radius = 20
        label.setTextColor(ResUtil.getColor(context, R.color.color_333))

        addView(label, LayoutParams(-1, -1))
        setPadding(pLeft, pTop, pRight, pBottom)
    }

    private fun setBg() {
        when (num) {
            -1 -> {
                label.bgColor = ResUtil.getColor(context, R.color.transparent)
            }
            0 -> {
                label.bgColor = ResUtil.getColor(context, R.color.card_color_0)
            }
            2 -> {
                label.bgColor = ResUtil.getColor(context, R.color.card_color_2)
            }
            4 -> {
                label.bgColor = ResUtil.getColor(context, R.color.card_color_4)
            }
            8 -> {
                label.bgColor = ResUtil.getColor(context, R.color.card_color_8)
            }
            16 -> {
                label.bgColor = ResUtil.getColor(context, R.color.card_color_16)
            }
            32 -> {
                label.bgColor = ResUtil.getColor(context, R.color.card_color_32)
            }
            64 -> {
                label.bgColor = ResUtil.getColor(context, R.color.card_color_64)
            }
            128 -> {
                label.bgColor = ResUtil.getColor(context, R.color.card_color_128)
            }
            256 -> {
                label.bgColor = ResUtil.getColor(context, R.color.card_color_256)
            }
            512 -> {
                label.bgColor = ResUtil.getColor(context, R.color.card_color_512)
            }
            1024 -> {
                label.bgColor = ResUtil.getColor(context, R.color.card_color_1024)
            }
            2048 -> {
                label.bgColor = ResUtil.getColor(context, R.color.card_color_2048)
            }
            4096 -> {
                label.bgColor = ResUtil.getColor(context, R.color.card_color_4096)
            }
            8182 -> {
                label.bgColor = ResUtil.getColor(context, R.color.card_color_8182)
            }
            16364 -> {
                label.bgColor = ResUtil.getColor(context, R.color.card_color_16364)
            }
            else -> {
                label.bgColor = ResUtil.getColor(context, R.color.card_color_else)
            }
        }
        label.setBgSelector()

        val w = width - paddingLeft - paddingRight
        val len = num.toString().length
        if (len > 2) {
            val ts =  w / len / 2f
            label.textSize = if( ts > 24f) 24f else ts
        } else {
            label.textSize = 24f
        }
    }

    fun equal(card: Card): Boolean {
        return num == card.num
    }

}
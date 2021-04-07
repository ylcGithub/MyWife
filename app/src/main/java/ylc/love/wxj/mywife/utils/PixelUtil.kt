package ylc.love.wxj.mywife.utils

import ylc.love.wxj.mywife.base.LifeApplication
/**
 *@author YLC-D
 *@create on 2021/3/15 17
 *说明:
 */
object PixelUtil {

    fun dp2px(dp: Float): Float {
        val density = LifeApplication.getAppContext().resources.displayMetrics.density
        return dp * density + 0.5f
    }

    fun px2dp(px: Float): Float {
        val density = LifeApplication.getAppContext().resources.displayMetrics.density
        return  px/density + 0.5f
    }

    fun sp2px(sp:Float):Float{
        val scale = LifeApplication.getAppContext().resources.displayMetrics.scaledDensity
        return scale * sp + 0.5f
    }
}
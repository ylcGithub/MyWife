package ylc.love.wxj.mywife.utils

import android.util.DisplayMetrics
import ylc.love.wxj.mywife.base.LifeApplication

/**
 *@author YLC-D
 *@create on 2021/3/5 10
 *说明:
 */
object ScreenUtil {
    /**
     * 获取屏幕高度
     */
    fun getScreenHeight(): Int {
        val dm: DisplayMetrics = LifeApplication.getAppContext().resources.displayMetrics
        return dm.heightPixels
    }

    /**
     * 获取屏幕宽度
     */
    fun getScreenWidth(): Int {
        val dm: DisplayMetrics =LifeApplication.getAppContext().resources.displayMetrics
        return dm.widthPixels
    }
}
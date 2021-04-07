package ylc.love.wxj.mywife.utils

import android.util.Log

/**
 * @author Administrator
 * @create on 2020/10/3 0003
 * 说明:打印的封装工具 暂时统一使用警告错误级别的打印
 */
object LogUtil {
    fun log(errorMsg: String) {
        Log.e("臭妹妹", errorMsg)
    }
}
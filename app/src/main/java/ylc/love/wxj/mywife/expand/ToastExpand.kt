package ylc.love.wxj.mywife.expand

/**
 *@author YLC-D
 *@create on 2020/11/10 17
 *说明: toast 的扩展
 */
import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes
import ylc.love.wxj.mywife.base.LifeApplication

fun Context.toast(content: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, content, duration).apply { show() }
}
fun Context.toast(@StringRes id: Int, duration: Int = Toast.LENGTH_SHORT) {
    toast(getString(id), duration)
}

fun Any.toast(duration: Int = Toast.LENGTH_SHORT) {
    LifeApplication.getAppContext().toast(this.toString(), duration)
}
fun Any.toast(content: String,duration: Int = Toast.LENGTH_SHORT) {
    LifeApplication.getAppContext().toast(content, duration)
}

fun Any.toast(@StringRes id: Int, duration: Int=Toast.LENGTH_SHORT) {
    LifeApplication.getAppContext().toast(id, duration)
}

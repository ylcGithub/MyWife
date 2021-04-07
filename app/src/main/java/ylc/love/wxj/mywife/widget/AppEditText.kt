package ylc.love.wxj.mywife.widget

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import ylc.love.wxj.mywife.utils.FontCustom

/**
 *@author YLC-D
 *@create on 2021/2/23 10
 *说明:
 */
class AppEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : AppCompatEditText(context, attrs, defStyle) {
    init {
        //不是预览页面情况下，设置字体样式
        if (!isInEditMode) {
            typeface = FontCustom.setFont(context)
            //避免自定义editText无法获取焦点
            isFocusableInTouchMode = true
        }
    }

    fun notifyTypeFace() {
        typeface = FontCustom.setFont(context)
    }
}
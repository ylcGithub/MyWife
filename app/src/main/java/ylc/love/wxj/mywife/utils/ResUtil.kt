package ylc.love.wxj.mywife.utils

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.annotation.*
import androidx.core.content.ContextCompat

/**
 * @author YLC-D
 * @create on 2020/10/26
 * 说明:资源类相关的工具方法
 */
object ResUtil {
    @ColorInt
    fun getColor(context: Context, @ColorRes colorId: Int): Int {
        return ContextCompat.getColor(context, colorId)
    }

    fun getDimen(context: Context, @DimenRes dimenId: Int): Float {
        return context.resources.getDimension(dimenId)
    }

    fun getString(context: Context, @StringRes stringId: Int): String {
        return context.resources.getString(stringId)
    }

    fun getDrawable(context: Context, @DrawableRes drawableId: Int): Drawable? {
        return ContextCompat.getDrawable(context, drawableId)
    }
}
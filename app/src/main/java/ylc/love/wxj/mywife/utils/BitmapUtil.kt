package ylc.love.wxj.mywife.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.core.content.ContextCompat

/**
 *@author YLC-D
 *@create on 2021/3/19 11
 *说明:
 */
object BitmapUtil {

    fun getBitmap(context: Context, drawableId: Int, scale: Float = 1f): Bitmap {
        var bitmap: Bitmap? = null
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            val vectorDrawable: Drawable? = ContextCompat.getDrawable(context, drawableId)
            vectorDrawable?.let { d ->
                Bitmap.createBitmap(
                    d.intrinsicWidth,
                    d.intrinsicHeight, Bitmap.Config.ARGB_8888
                ).also { bitmap = it }
                bitmap?.let {
                    val canvas = Canvas(it)
                    d.setBounds(0, 0, canvas.width, canvas.height)
                    d.draw(canvas)
                }
            }
        } else {
            bitmap = BitmapFactory.decodeResource(context.resources, drawableId)
        }

        val result = if (bitmap == null) Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888) else bitmap!!

        return if (scale == 1f) {
            result
        } else bitmapScale(result, scale)
    }

    fun bitmapScale(bitmap: Bitmap, scale: Float): Bitmap {
        val matrix = Matrix()
        matrix.setScale(scale, scale)
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    }
}
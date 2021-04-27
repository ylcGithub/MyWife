package ylc.love.wxj.mywife.ui.customview

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import ylc.love.wxj.mywife.R
import ylc.love.wxj.mywife.utils.BitmapUtil


/**
 *@author YLC-D
 *@create on 2021/3/18 10
 *说明:
 */
class TestPaint @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : View(context, attrs, defStyle) {
    //初始化一个抗锯齿的画笔
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var bmp1: Bitmap = BitmapUtil.getBitmap(context,R.drawable.vector_drawable_diy_failure,3f)
    private var bmp2: Bitmap = BitmapFactory.decodeResource(resources,R.drawable.icon_bill)
    //图片灰度化，RGB取平均值
    private val array = floatArrayOf(
        0.33f,0.33f,0.33f,0f,0f,// 红色值
        0.33f,0.33f,0.33f,0f,0f,// 绿色值
        0.33f,0.33f,0.33f,0f,0f,// 蓝色值
        0f,0f,0f,1f,0f)// 透明度值
    private val lcf = ColorMatrixColorFilter(array)

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        paint.colorFilter = lcf
        canvas.drawBitmap(bmp2,0F,0f,paint)
    }
}
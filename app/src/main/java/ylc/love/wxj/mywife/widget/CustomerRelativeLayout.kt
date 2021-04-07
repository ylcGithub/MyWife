package ylc.love.wxj.mywife.widget

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout

@SuppressLint("NewApi")
class CustomerRelativeLayout : RelativeLayout {
    constructor(context: Context?) : super(context) {
        setBg()
    }

    constructor(
        context: Context?, attrs: AttributeSet?,
        defStyleAttr: Int, defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        setBg()
    }

    constructor(
        context: Context?, attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
        setBg()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        setBg()
    }

    private lateinit var colorAnim: ValueAnimator
    fun setBg() {
         colorAnim = ObjectAnimator.ofInt(this, "backgroundColor", RED, GREEN,BLUE, BLACK)
        colorAnim.duration = 6000
        colorAnim.setEvaluator(MyEvaluator())
        colorAnim.repeatCount = ValueAnimator.INFINITE
        colorAnim.repeatMode = ValueAnimator.REVERSE
        colorAnim.start()
    }

    fun pause(){
        colorAnim.pause()
    }

    fun resume(){
        if(::colorAnim.isInitialized && colorAnim.isPaused){
            colorAnim.start()
        }
    }

    companion object {
        private const val RED = -0xff80
        private const val GREEN = -0xff0010
        private const val BLUE = -0xff7f01
        private const val BLACK = -0xcccccd
    }

    class MyEvaluator : ArgbEvaluator(){
        override fun evaluate(fraction: Float, startValue: Any?, endValue: Any?): Any {
            //            LogUtil.log(result.toString())
            return super.evaluate(fraction, startValue, endValue)
        }
    }
}
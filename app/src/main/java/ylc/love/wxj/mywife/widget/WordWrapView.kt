package ylc.love.wxj.mywife.widget

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.TextView
import ylc.love.wxj.mywife.R

/**
 * @author YLC-D
 * 说明：
 * 自定义一个ViewGroup,里面的子view都是TextView，
 * 每个子view  TextView的宽度随内容自适应且每行的子View的个数自适应，
 * 并可以自动换行
 */
class WordWrapView @JvmOverloads constructor(context: Context, attrs: AttributeSet?, defStyle: Int = 0) : ViewGroup (context,attrs,defStyle){
    /**
     * 子view水平方向padding
     */
    private var paddingHor = 0

    /**
     * 子view垂直方向padding
     */
    private var paddingVertical = 0

    /**
     * 子view之间的水平间距
     */
    private var marginHor = 0

    /**
     * 行间距
     */
    private var marginVertical = 0

    /**
     * 最多字个数
     */
    private var num = 0

   init {
       initAttrs(context, attrs)
   }

    /**
     * 获取属性值
     */
    private fun initAttrs(context: Context, attrs: AttributeSet?) {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.WordWrapView)
        paddingHor = ta.getDimension(R.styleable.WordWrapView_paddingHor, 0f).toInt()
        paddingVertical = ta.getDimension(R.styleable.WordWrapView_paddingVertical, 0f).toInt()
        marginHor = ta.getDimension(R.styleable.WordWrapView_marginHor, 0f).toInt()
        marginVertical = ta.getDimension(R.styleable.WordWrapView_marginVertical, 0f).toInt()
        ta.recycle()
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val childCount = childCount
        //实际宽度
        val actualWidth = r - l
        var x = 0
        var y: Int
        var rows = 1
        //判断累积高度
        for (i in 0 until childCount) {
            val view = getChildAt(i)
            var width = view.measuredWidth
            val height = view.measuredHeight
            x += width + marginHor
            if (x > actualWidth - marginHor) {
                if (i != 0) {
                    x = width + marginHor
                    rows++
                }
            }
            //当一个子view长度超出父view长度时
            if (x > actualWidth - marginHor) {
                //判断单个高度
                if (view is TextView) {
                    if (num == 0) {
                        val wordNum = view.text.toString().length
                        num = wordNum * (actualWidth - 2 * marginHor - 2 * paddingHor) / (width - 2 * paddingHor) - 1
                    }
                    var text = view.text.toString()
                    text = text.substring(0, num) + "..."
                    view.text = text
                }
                x = actualWidth - marginHor
                width = actualWidth - 2 * marginHor
            }
            y = rows * (height + marginVertical)
            view.layout(x - width, y - height, x, y)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        //横坐标
        var x = 0
        //纵坐标
        var y = 0
        //总行数
        var rows = 1
        val specWidth = MeasureSpec.getSize(widthMeasureSpec)
        //实际宽度
        val childCount = childCount
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            child.setPadding(paddingHor, paddingVertical, paddingHor, paddingVertical)
            child.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED)
            val width = child.measuredWidth
            val height = child.measuredHeight
            x += width + marginHor
            //换行
            if (x > specWidth - marginHor) {
                if (i != 0) {
                    x = width + marginHor
                    rows++
                }
            }
            y = rows * (height + marginVertical)
        }
        setMeasuredDimension(specWidth, y + marginVertical)
    }
}
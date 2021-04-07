package ylc.love.wxj.mywife.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.base_common_hor_widget.view.*
import ylc.love.wxj.mywife.R

/**
 *@author YLC-D
 *@create on 2021/2/18 15
 *说明:
 */
class CommonHor @JvmOverloads constructor(context: Context,attrs:AttributeSet? = null,defStyle:Int = 0):RelativeLayout(context,attrs,defStyle) {
    init {
       inflate(context, R.layout.base_common_hor_widget,this)
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CommonHor)
        val title = typedArray.getString(R.styleable.CommonHor_hor_title)
        val content = typedArray.getString(R.styleable.CommonHor_hor_content)
        val rightIcon = typedArray.getResourceId(R.styleable.CommonHor_hor_right_icon,R.drawable.icon_arrow_down)
        typedArray.recycle()
        tv_title.text = title
        tv_content.text = content
        tv_content.setRightDrawable(ContextCompat.getDrawable(context,rightIcon))
    }

    fun setContentClickListener(listener: OnClickListener){
        tv_content.setOnClickListener(listener)
    }
    fun setContent(content:String){
        tv_content.text = if(content.isEmpty()) "没有自定义字体" else content
    }

    fun notifyTypeFace(){
        tv_title.notifyTypeFace()
        tv_content.notifyTypeFace()
    }
}
package ylc.love.wxj.mywife.widget

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.annotation.ColorInt
import com.jaeger.library.StatusBarUtil
import kotlinx.android.synthetic.main.base_app_title_no_right.view.*
import ylc.love.wxj.mywife.R
import ylc.love.wxj.mywife.type.DefaultPageType
import ylc.love.wxj.mywife.utils.ResUtil

/**
 *@author YLC-D
 *@create on 2020/11/26 16
 *说明: 自定义的APP标题栏，其中包含有 APP的缺省页的实现，
 * 就是一个标题栏和缺省页的集合实现控件
 */
class AppTitle @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int = 0
) : RelativeLayout(
    context,
    attrs,
    defStyleAttr
) {

    private var titleBackgroundColor: Int
    private var statusIsLightMode:Boolean = true

    init {
        inflate(context, R.layout.base_app_title_no_right, this)
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.AppTitle)
        val noTitle = typedArray.getBoolean(R.styleable.AppTitle_no_title, false)
        val backIcon = typedArray.getResourceId(
            R.styleable.AppTitle_back_icon,
            R.drawable.icon_arrow_back
        )
        val noBackIcon = typedArray.getBoolean(R.styleable.AppTitle_no_back_icon, false)
        val rightOneIcon = typedArray.getResourceId(R.styleable.AppTitle_right_one_icon, -1)
        val rightTwoIcon = typedArray.getResourceId(R.styleable.AppTitle_right_two_icon, -1)
        val title = typedArray.getString(R.styleable.AppTitle_title)
        val rightOneText = typedArray.getString(R.styleable.AppTitle_right_one_text)
        val rightTwoText = typedArray.getString(R.styleable.AppTitle_right_two_text)
        val titleColor = typedArray.getColor(
            R.styleable.AppTitle_title_color,
            ResUtil.getColor(context, R.color.color_333)
        )
        val titleSize: Float = typedArray.getDimension(
            R.styleable.AppTitle_title_size,
            ResUtil.getDimen(context, R.dimen.font_size_16)
        )
        titleBackgroundColor = typedArray.getColor(
            R.styleable.AppTitle_title_background,
            ResUtil.getColor(context, R.color.white)
        )
        val rightOneTextColor = typedArray.getColor(R.styleable.AppTitle_right_one_text_color, -1)
        val rightTwoTextColor = typedArray.getColor(R.styleable.AppTitle_right_two_text_color, -1)
        val rightOneTextPressedColor =
            typedArray.getColor(R.styleable.AppTitle_right_one_text_pressed_color, -1)
        val rightTwoTextPressedColor =
            typedArray.getColor(R.styleable.AppTitle_right_two_text_pressed_color, -1)
        statusIsLightMode = typedArray.getBoolean(R.styleable.AppTitle_status_is_light_mode,true)
        typedArray.recycle()
        rl_box.setBackgroundColor(titleBackgroundColor)
        val tvTitle = findViewById<AppTextView>(R.id.tv_title)
        if (noTitle) {
            tvTitle.visibility = GONE
            iv_back.visibility = GONE
        } else {
            tvTitle.text = title
            tvTitle.setTextColor(titleColor)
            tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, titleSize)
            if (noBackIcon) {
                iv_back.visibility = GONE
            } else {
                iv_back.setImageResource(backIcon)
            }
            rightOneText?.let {
                tv_right_one.text = rightOneText
                tv_right_one.visibility = VISIBLE
                setTextViewColor(rightOneTextColor, rightOneTextPressedColor, tv_right_one)
            }
            rightTwoText?.let {
                tv_right_two.text = rightTwoText
                tv_right_two.visibility = VISIBLE
                setTextViewColor(rightTwoTextColor, rightTwoTextPressedColor, tv_right_two)
            }
            if (rightOneIcon != -1) {
                iv_right_one.setImageResource(rightOneIcon)
                iv_right_one.visibility = VISIBLE
            }
            if (rightTwoIcon != -1) {
                iv_right_two.setImageResource(rightTwoIcon)
                iv_right_two.visibility = VISIBLE
            }
        }
    }

    private fun setTextViewColor(
        @ColorInt color: Int,
        @ColorInt pressedColor: Int,
        tv: AppTextView
    ) {
        if (color != -1) {
            tv.setTextColor(color)
        }
        if (pressedColor != -1) {
            tv.setPressedColor(pressedColor)
        }
    }

    fun setTitle(title: String?) {
        title?.let { tv_title.text = it }
    }

    fun showDefaultPage(type: DefaultPageType, action: (View) -> Unit) {
        val defaultPage = findViewById<LinearLayout>(R.id.default_page)
        when (type) {
            DefaultPageType.NETWORK_ERROR -> {
                defaultPage.visibility = VISIBLE
                val ivHint = defaultPage.findViewById<AppImageView>(R.id.default_page_hint_icon)
                val tvHint = defaultPage.findViewById<AppTextView>(R.id.default_page_tv_hint)
                ivHint.setImageResource(R.drawable.vector_drawable_network_difference)
                tvHint.setText(R.string.the_current_network_does_not_give_force_please_click_page_refresh)
                ivHint.setOnClickListener(action)
                tvHint.setOnClickListener(action)
            }

            DefaultPageType.PAGE_EMPTY -> {
                defaultPage.visibility = VISIBLE
                val ivHint = defaultPage.findViewById<AppImageView>(R.id.default_page_hint_icon)
                val tvHint = defaultPage.findViewById<AppTextView>(R.id.default_page_tv_hint)
                ivHint.setImageResource(R.drawable.vector_drawable_page_empty)
                tvHint.setText(R.string.it_is_empty)
            }
            DefaultPageType.HIDE_DEFAULT_PAGE -> {
                defaultPage.visibility = GONE
            }
        }
    }

    fun setClickBackListener(listener: OnClickListener?) {
        iv_back.setOnClickListener(listener)
    }

    fun setRightOneTextBtnClickListener(listener: OnClickListener?) {
        tv_right_one.setOnClickListener(listener)
    }


    fun setRightTwoTextBtnClickListener(listener: OnClickListener?) {
        tv_right_two.setOnClickListener(listener)
    }


    fun setRightOneImageBtnClickListener(listener: OnClickListener?) {
        iv_right_one.setOnClickListener(listener)
    }


    fun setRightTwoImageBtnClickListener(listener: OnClickListener?) {
        iv_right_two.setOnClickListener(listener)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        //这时控件已经加载完成
        if (!isInEditMode) {
            if(statusIsLightMode) StatusBarUtil.setLightMode(context as Activity?)
            else StatusBarUtil.setDarkMode(context as Activity?)
            StatusBarUtil.setTranslucent(context as Activity?, 0)
            StatusBarUtil.setColorNoTranslucent(context as Activity?, titleBackgroundColor)
        }
    }

    fun notifyTypeFace(){
       tv_title.notifyTypeFace()
    }
}
package ylc.love.wxj.mywife.common

import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import androidx.databinding.BindingAdapter
import ylc.love.wxj.mywife.widget.AppTitle

/**
 *@author YLC-D
 *@create on 2021/2/4 16
 *说明:数据绑定的扩展方法
 */

@BindingAdapter("titleBack")
fun setAppTitleBackClick(titleBar: AppTitle, listener: View.OnClickListener?) {
    titleBar.setClickBackListener(listener)
}

@BindingAdapter("titleRightOneTextClick")
fun setAppTitleRightOneTextClick(titleBar: AppTitle, listener: View.OnClickListener?) {
    titleBar.setRightOneTextBtnClickListener(listener)
}

@BindingAdapter("titleRightTwoTextClick")
fun setAppTitleRightTwoTextClick(titleBar: AppTitle, listener: View.OnClickListener?) {
    titleBar.setRightTwoTextBtnClickListener(listener)
}


@BindingAdapter("titleRightOneIconClick")
fun setAppTitleRightOneIconClick(titleBar: AppTitle, listener: View.OnClickListener?) {
    titleBar.setRightOneImageBtnClickListener(listener)
}

@BindingAdapter("titleRightTwoIconClick")
fun setAppTitleRightTwoIconClick(titleBar: AppTitle, listener: View.OnClickListener?) {
    titleBar.setRightTwoImageBtnClickListener(listener)
}

@BindingAdapter("addTextChangeListener")
fun addTextChangeListener(et: EditText, watcher: TextWatcher?) {
    watcher?.let {
        et.addTextChangedListener(it)
    }
}
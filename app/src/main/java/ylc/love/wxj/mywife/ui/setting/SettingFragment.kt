package ylc.love.wxj.mywife.ui.setting

import android.graphics.Color
import android.view.Gravity
import com.orhanobut.hawk.Hawk
import kotlinx.android.synthetic.main.fragment_setting.*
import ylc.love.wxj.mywife.R
import ylc.love.wxj.mywife.base.BaseFragment
import ylc.love.wxj.mywife.databinding.FragmentSettingBinding
import ylc.love.wxj.mywife.model.SelectTypeBean
import ylc.love.wxj.mywife.ui.customview.SumCustomViewActivity
import ylc.love.wxj.mywife.ui.game.SmallGameActivity
import ylc.love.wxj.mywife.utils.FontCustom
import ylc.love.wxj.mywife.widget.SelectTypePopWindow

/**
 * @author Administrator
 * @create on 2021/2/1 0001
 * 说明:
 */
class SettingFragment : BaseFragment<SettingViewModel, FragmentSettingBinding>() {
    override fun getLayoutId(): Int = R.layout.fragment_setting

    override fun getViewModel(): SettingViewModel =
        getViewModelProvider(this).get(SettingViewModel::class.java)

    override fun initData() {
        hor_font.setContentClickListener { it ->
            val array = mContext.assets.list("")
            val window = SelectTypePopWindow(mContext)
            val list: MutableList<SelectTypeBean> = mutableListOf()
            array?.let {
                for ((index, value) in it.withIndex()) {
                    if (value.startsWith("经典")) {
                        val bean = SelectTypeBean(list.size, value.trim().replace(".TTF", ""))
                        list.add(bean)
                    }
                }
                val bean = SelectTypeBean(list.size, "取消自定义")
                list.add(bean)
            }
            window.list = list
            window.showPopupWindow(it)
            window.popupGravity = Gravity.END
            window.setBackgroundColor(Color.TRANSPARENT)
            window.listener = object : SelectTypePopWindow.CustomClickListener {
                override fun onClick(id: Int, type: String) {
                    FontCustom.tf = null
                    if ("取消自定义" != type) {
                        Hawk.put(FontCustom.FONT_URL_KEY, "$type.TTF")
                        hor_font.setContent(type)
                    } else {
                        Hawk.put(FontCustom.FONT_URL_KEY, "")
                        hor_font.setContent("")
                    }
                    notifyFont()
                }
            }
        }
        hor_font.setContent(Hawk.get(FontCustom.FONT_URL_KEY, "").replace(".TTF", ""))
        hor_game.setContentClickListener {
            toNextActivity(SmallGameActivity::class.java)
        }
        hor_custom_view.setContentClickListener{
            toNextActivity(SumCustomViewActivity::class.java)
        }
    }

    fun notifyFont() {
        app_title.notifyTypeFace()
        hor_font.notifyTypeFace()
        hor_game.notifyTypeFace()
        hor_custom_view.notifyTypeFace()
    }
}
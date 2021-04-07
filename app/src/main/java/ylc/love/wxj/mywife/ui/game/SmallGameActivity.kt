package ylc.love.wxj.mywife.ui.game

import android.app.AlertDialog
import android.view.KeyEvent
import com.orhanobut.hawk.Hawk
import kotlinx.android.synthetic.main.activity_small_game.*
import kotlinx.android.synthetic.main.base_app_title_no_right.view.*
import ylc.love.wxj.mywife.R
import ylc.love.wxj.mywife.base.BaseMvvmActivity
import ylc.love.wxj.mywife.databinding.ActivitySmallGameBinding
import ylc.love.wxj.mywife.model.SelectTypeBean
import ylc.love.wxj.mywife.widget.SelectTypePopWindow

/**
 *@author YLC-D
 *@create on 2021/2/26 17
 *说明:
 */
class SmallGameActivity : BaseMvvmActivity<SmallGameViewModel, ActivitySmallGameBinding>() {


    override fun getViewModel(): SmallGameViewModel = getViewModelProvider(this).get(
        SmallGameViewModel::class.java
    )

    override fun getLayoutId(): Int = R.layout.activity_small_game

    override fun initData() {
        mBinding.click = ClickProxy()
        mBinding.vm = mViewModel
        mViewModel.bestScoreValue = Hawk.get(GameView.BEST_SCORE, 0)
        mViewModel.addScore(0)
        game.viewModel = mViewModel
        mViewModel.swipe.observe(this, {
            anim_view.createMoveAnim(it.from, it.to, it.fromPoint, it.toPoint)
        })
    }

    var window: SelectTypePopWindow? = null

    inner class ClickProxy {
        fun back() {
           showExitDialog()
        }

        fun restart() {
            game.restartGame()
        }

        fun setting() {
            if (window == null) {
                window = SelectTypePopWindow(this@SmallGameActivity)
                val listBean = List(3) {
                    SelectTypeBean(it+1, "大小：${it + 4} * ${it + 4}")
                }
                window?.list = listBean
                window?.listener = object : SelectTypePopWindow.CustomClickListener {
                    override fun onClick(id: Int, type: String) {
                        Hawk.put(GameView.LINES_KEY, id + 3)
                        game.newLayout(id + 3)
                    }
                }
            }
            window?.showPopupWindow(app_title.tv_right_one)
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return if(keyCode == KeyEvent.KEYCODE_BACK){
            showExitDialog()
            true
        }else super.onKeyDown(keyCode, event)
    }

    private fun showExitDialog(){
        AlertDialog.Builder(this).setTitle("退出提示").setMessage("是否确定退出游戏，退出后当前游戏进度不会保存！").setPositiveButton(
            "退出"
        ) { _, _ -> finishActivity() }.setNegativeButton("取消"){_,_ -> }.show()
    }
}
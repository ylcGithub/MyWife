package ylc.love.wxj.mywife

import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main_layout.*
import ylc.love.wxj.mywife.base.BaseActivity
import ylc.love.wxj.mywife.model.AppDataBase
import ylc.love.wxj.mywife.model.BillTypeBean

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val navController = findNavController(R.id.nav_host_fragment)
        nav_view.setupWithNavController(navController)
    }

    override fun getLayoutId(): Int = R.layout.activity_main_layout

    override fun initData() {
        initBillTypeBean()
    }

    /**
     * 初始化账单类型数据
     */
    private fun initBillTypeBean() = runOnThread(work = {
        val billTypeBeanDao = AppDataBase.instance.billTypeBeanDao()
        val list = billTypeBeanDao.selectAll()
        if (list.isEmpty()) {
            val stringArray = resources.getStringArray(R.array.BillTypeString)
            repeat(stringArray.count()) {
                val ben = BillTypeBean(it + 1, stringArray[it])
                billTypeBeanDao.insert(ben)
            }
        }
    })
}
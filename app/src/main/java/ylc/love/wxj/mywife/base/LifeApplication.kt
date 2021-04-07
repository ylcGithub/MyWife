package ylc.love.wxj.mywife.base

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.orhanobut.hawk.Hawk

/**
 *@author YLC-D
 *@create on 2021/1/26 10
 *说明:
 */
class LifeApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)
        mAppContext = this
        Hawk.init(this).build()
    }

    companion object {
        private var mAppContext: Context? = null
        /**
         * 全局获取系统上下文
         */
        fun getAppContext(): Context {
            return mAppContext!!
        }
    }
}
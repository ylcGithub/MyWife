package ylc.love.wxj.mywife.base

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import ylc.love.wxj.mywife.config.ParamsKey
import ylc.love.wxj.mywife.utils.AppManager
import java.lang.Exception
import java.lang.ref.WeakReference

/**
 *@author YLC-D
 *@create on 2021/1/26 11
 *说明:
 */
abstract class BaseActivity : AppCompatActivity() {
    protected var isCreated: Boolean = false
    protected var needSetLayout: Boolean = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //解决键盘遮挡输入框的问题
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        AppManager.addActivity(this)
        isCreated = true
        if (needSetLayout && getLayoutId() != -1) {
            setContentView(getLayoutId())
        }
    }

    @LayoutRes
    protected abstract fun getLayoutId(): Int

    protected abstract fun initData()

    override fun onResume() {
        super.onResume()
        if (isCreated) {
            initData()
            isCreated = false
        }
    }

    protected fun toNextActivity(cls: Class<out Activity>, bundle: Bundle? = null) {
        val intent = Intent(this, cls)
        intent.putExtra(ParamsKey.ACTIVITY_TO_ACTIVITY_DATA_KEY, bundle)
        AppManager.start(WeakReference(this), intent)
    }

    fun runOnThread(
        work: suspend CoroutineScope.() -> Unit,
        catch: suspend CoroutineScope.(e: Throwable) -> Unit = {},
        end: suspend CoroutineScope.() -> Unit = {}
    ) = lifecycleScope.launch(Dispatchers.IO) {
        try {
            work()
        } catch (e: Exception) {
            catch(e)
        } finally {
            end()
        }
    }

    fun runOnMain(
        work: suspend CoroutineScope.() -> Unit,
        catch: suspend CoroutineScope.(e: Throwable) -> Unit = {},
        end: suspend CoroutineScope.() -> Unit = {}
    ) = lifecycleScope.launch(Dispatchers.Main) {
        try {
            work()
        } catch (e: Exception) {
            catch(e)
        } finally {
            end()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycleScope.cancel()
    }

    private lateinit var mActivityProvider: ViewModelProvider
    protected open fun getViewModelProvider(activity: AppCompatActivity): ViewModelProvider {
        if (!this::mActivityProvider.isInitialized) mActivityProvider = ViewModelProvider(activity)
        return mActivityProvider
    }

    protected fun finishActivity(){
        AppManager.finishActivity(this)
    }
}
package ylc.love.wxj.mywife.base

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

/**
 *@author YLC-D
 *@create on 2021/1/27 11
 *说明:
 */
open class BaseViewModel : ViewModel(), LifecycleObserver {
    val errorMsg = MutableLiveData<String>()

    fun runOnThread(
        work: suspend CoroutineScope.() -> Unit,
        catch: suspend CoroutineScope.(e: Throwable) -> Unit = {},
        end: suspend CoroutineScope.() -> Unit = {}
    ) = viewModelScope.launch(Dispatchers.IO) {
        try {
            work()
        } catch (e: Exception) {
            catch(e)
        } finally {
            end()
        }
    }

    private fun runOnMain(work: suspend CoroutineScope.() -> Unit) = viewModelScope.launch(Dispatchers.Main) {
        work()
    }

    fun <T> setValueOnMain(data: MutableLiveData<T>, value: T) = runOnMain {
        data.value = value
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}
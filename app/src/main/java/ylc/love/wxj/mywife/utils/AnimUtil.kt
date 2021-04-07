package ylc.love.wxj.mywife.utils

import android.animation.ObjectAnimator
import android.view.View
import androidx.lifecycle.LifecycleObserver

/**
 *@author YLC-D
 *@create on 2021/2/5 16
 *说明:
 */
object AnimUtil : LifecycleObserver {
    fun getTransX(v: View, vararg values: Float): ObjectAnimator {
        return ObjectAnimator.ofFloat(v, View.TRANSLATION_X, *values)
    }

    fun getTransY(v: View, vararg values: Float): ObjectAnimator {
        return ObjectAnimator.ofFloat(v, View.TRANSLATION_Y, *values)
    }

    fun getRotation(v: View, vararg values: Float): ObjectAnimator {
        return ObjectAnimator.ofFloat(v, View.ROTATION, *values)
    }

    fun getRotationX(v: View, vararg values: Float): ObjectAnimator {
        return ObjectAnimator.ofFloat(v, View.ROTATION_X, *values)
    }

    fun getRotationY(v: View, vararg values: Float): ObjectAnimator {
        return ObjectAnimator.ofFloat(v, View.ROTATION_Y, *values)
    }

    fun getAlpha(v: View, vararg values: Float): ObjectAnimator {
        return ObjectAnimator.ofFloat(v, View.ALPHA, *values)
    }

    fun getScaleX(v: View, vararg values: Float): ObjectAnimator {
        return ObjectAnimator.ofFloat(v, View.SCALE_X, *values)
    }

    fun getScaleY(v: View, vararg values: Float): ObjectAnimator {
        return ObjectAnimator.ofFloat(v, View.SCALE_Y, *values)
    }
    
    fun getBackgroundColorAnim(v: View, vararg values: Int): ObjectAnimator {
        return ObjectAnimator.ofInt(v, "backgroundColor", *values)
    }
}
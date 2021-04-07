package ylc.love.wxj.mywife.ui.game

import android.graphics.Point

/**
 *@author YLC-D
 *@create on 2021/3/11 10
 *说明:
 */
data class SwipePoint constructor(val from:Card, val to:Card, val fromPoint: Point,val toPoint: Point)
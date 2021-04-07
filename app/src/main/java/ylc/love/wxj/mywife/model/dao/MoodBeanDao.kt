package ylc.love.wxj.mywife.model.dao

import androidx.room.Dao
import androidx.room.Query
import ylc.love.wxj.mywife.model.MoodBean

/**
 *@author YLC-D
 *@create on 2021/2/23 16
 *说明:
 */
@Dao
interface MoodBeanDao:BaseDao<MoodBean> {
    @Query("select * from MoodBean")
    fun selectAll():MutableList<MoodBean>
}
package ylc.love.wxj.mywife.model.dao

import androidx.room.Dao
import androidx.room.Query
import ylc.love.wxj.mywife.model.DateBean

/**
 * @author YLC-D
 * @create on 2020/10/26 16
 * 说明:
 */
@Dao
interface EventBeanDao : BaseDao<DateBean> {
    /**
     * 根据用户id查找用户信息
     * @param id id
     */
    @Query("select * from DateBean where id = :id")
    fun selectById(id: Long): DateBean?

    @Query("select * from DateBean")
    fun selectAll(): List<DateBean>
}
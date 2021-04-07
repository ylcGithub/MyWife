package ylc.love.wxj.mywife.model.dao

import androidx.room.Dao
import androidx.room.Query
import ylc.love.wxj.mywife.model.BillTypeBean

/**
 *@author YLC-D
 *@create on 2021/1/28 11
 *说明:
 */
@Dao
interface BillTypeBeanDao : BaseDao<BillTypeBean> {
    @Query("select * from BillTypeBean where id = :id")
    fun selectById(id: Long): BillTypeBean?

    @Query("select * from BillTypeBean")
    fun selectAll(): List<BillTypeBean>
}
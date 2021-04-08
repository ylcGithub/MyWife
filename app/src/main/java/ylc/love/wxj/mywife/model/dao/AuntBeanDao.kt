package ylc.love.wxj.mywife.model.dao

import androidx.room.Dao
import androidx.room.Query
import ylc.love.wxj.mywife.model.AuntBean

/**
 *@author YLC-D
 *@create on 2021/4/7 16
 *说明:
 */
@Dao
interface AuntBeanDao : BaseDao<AuntBean> {
    //DESC 是降序查询 ASC 是升序查询。
    @Query("select * from AuntBean order by id DESC")
    fun selectAll(): MutableList<AuntBean>
}
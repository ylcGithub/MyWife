package ylc.love.wxj.mywife.model.dao

import androidx.room.Dao
import androidx.room.Query
import ylc.love.wxj.mywife.model.BillBean
import ylc.love.wxj.mywife.model.DateBean

/**
 *@author YLC-D
 *@create on 2021/4/27 10
 *说明:
 */
@Dao
interface BillBeanDao:BaseDao<BillBean> {
    //查询某个时间段内的数据
    @Query("select * from BillBean where date BETWEEN :startDate and :endDate ORDER BY date DESC")
    fun selectByDate(startDate:Long,endDate:Long): List<BillBean>

    //查询某个时间段内的数据
    @Query("select * from BillBean where typeId = :typeId and date BETWEEN :startDate and :endDate ORDER BY date DESC")
    fun selectByDateAndType(startDate:Long,endDate:Long,typeId:Int): List<BillBean>
}
package ylc.love.wxj.mywife.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 *@author YLC-D
 *@create on 2021/4/7 16
 *说明: 姨妈周期推算数据表
 */
@Entity
data class AuntBean(
    @PrimaryKey
    val id: Long,
    //大姨妈开始时间
    @ColumnInfo
    val startTime: Long,
    //大姨妈结束时间
    @ColumnInfo
    var endTime:Long,
    //大姨妈持续时间
    @ColumnInfo
    var usedTime:Int
)
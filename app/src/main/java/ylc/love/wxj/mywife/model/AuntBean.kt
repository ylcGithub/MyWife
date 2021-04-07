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
    @ColumnInfo
    val startTime: Long,
    @ColumnInfo
    var endTime:Long,
)
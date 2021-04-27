package ylc.love.wxj.mywife.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 *@author YLC-D
 *@create on 2021/4/27 09
 *说明:
 */
@Entity
data class BillBean(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    @ColumnInfo
    var typeId: Int,
    @ColumnInfo
    var type: String,
    @ColumnInfo
    var date: Long,
    @ColumnInfo
    var spend:Float,
    @ColumnInfo
    var des: String?,
)
package ylc.love.wxj.mywife.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 *@author YLC-D
 *@create on 2021/1/28 10
 *说明:
 */
@Entity
data class BillTypeBean(
    @ColumnInfo
    val type: String,
    @ColumnInfo
    var lastUse: Long = 0,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 1,
)
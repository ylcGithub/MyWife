package ylc.love.wxj.mywife.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 *@author YLC-D
 *@create on 2021/2/23 15
 *说明:
 */
@Entity
data class MoodBean(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    @ColumnInfo
    var title: String?,
    @ColumnInfo
    var des: String?,
    @ColumnInfo
    var createDate: Long
)
package ylc.love.wxj.mywife.model.dao

import androidx.room.*

@Dao
interface BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: MutableList<T>):List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(element: T):Long

    @Delete
    fun delete(element: T):Int

    @Delete
    fun deleteList(elements:MutableList<T>):Int

    @Delete
    fun deleteSome(vararg elements:T):Int

    @Update
    fun update(element: T):Int
}

package ylc.love.wxj.mywife.model

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import ylc.love.wxj.mywife.base.LifeApplication
import ylc.love.wxj.mywife.model.dao.*

/**
 * @author Administrator
 * @create on 2020/4/12 0012
 * 说明: 数据声明
 */
@Database(
    entities = [DateBean::class,  BillTypeBean::class, MoodBean::class,AuntBean::class,BillBean::class],
    version = 2,
    exportSchema = false
)
abstract class AppDataBase : RoomDatabase() {

    abstract fun eventBeanDao(): EventBeanDao
    abstract fun auntBeanDao(): AuntBeanDao
    abstract fun moodBeanDao(): MoodBeanDao
    abstract fun billTypeBeanDao(): BillTypeBeanDao
    abstract fun billBeanDao(): BillBeanDao

    companion object {
        val instance = Single.sin
    }

    private object Single {
        val migration_1_to_2 = object :Migration(1,2){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("CREATE TABLE `BillBean` (`id` LONG,`type` TEXT,`des` TEXT,`date` LONG,`typeId` INTEGER,`spend` FLOAT,"
                        + "PRIMARY KEY(`id`))")
            }
        }

        val sin: AppDataBase = Room.databaseBuilder(
            LifeApplication.getAppContext(),
            AppDataBase::class.java, "tools_of_life_db"
        ).allowMainThreadQueries()
            .addMigrations(migration_1_to_2)
            .build()

    }
}
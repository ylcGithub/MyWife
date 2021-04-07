package ylc.love.wxj.mywife.utils

import android.annotation.SuppressLint

import java.text.SimpleDateFormat
import java.util.*

/**
 * 时间工具
 *
 * @author D10NG
 * @date on 2019-10-08 11:28
 */
object DateUtils {

    /**
     * 获取系统时间戳
     * @return
     */
    @JvmStatic
    val curTime: Long
        get() = System.currentTimeMillis()

    @JvmStatic
    val curYear: Int
        get() = Integer.parseInt(getCurDateStr("yyyy"))

    @JvmStatic
    val curMonth: Int
        get() = Integer.parseInt(getCurDateStr("MM"))

    @JvmStatic
    val curDay: Int
        get() = Integer.parseInt(getCurDateStr("dd"))

    @JvmStatic
    val curHour: Int
        get() = Integer.parseInt(getCurDateStr("HH"))

    @JvmStatic
    val curMinute: Int
        get() = Integer.parseInt(getCurDateStr("mm"))

    /**
     * 时间戳转换成字符窜
     * @param milSecond
     * @param pattern
     * @return
     */
    @JvmStatic
    fun getDateStr(milSecond: Long, pattern: String): String {
        val date = Date(milSecond)

        @SuppressLint("SimpleDateFormat")
        val format = SimpleDateFormat(pattern, Locale.CHINESE)
        return format.format(date)
    }

    /**
     * 获取当前时间字符串
     * @param pattern
     * @return
     */
    @JvmStatic
    fun getCurDateStr(pattern: String): String {
        return getDateStr(curTime, pattern)
    }

    /**
     * 将字符串转为时间戳
     * @param dateString
     * @param pattern
     * @return
     */
    @JvmStatic
    fun getDateFromStr(dateString: String, pattern: String): Long {
        @SuppressLint("SimpleDateFormat")
        val dateFormat = SimpleDateFormat(pattern)
        var date: Date? = Date()
        try {
            date = dateFormat.parse(dateString)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return date?.time ?: 0
    }

    /**
     * 获取当月开始的时间戳
     */
    @SuppressLint("SimpleDateFormat")
    @JvmStatic
    fun getCurrMonthStartTime(): Long {
        val dateFormat = SimpleDateFormat("yyyy-MM")
        var date: Date? = Date()
        try {
            val time = "${getCurDateStr("yyyy")}-${getCurDateStr("MM")}"
            date = dateFormat.parse(time)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return date?.time ?: 0
    }

    /**
     * 根据年月日获取时间戳
     * date yyyy-MM-dd
     */
    @JvmStatic
    fun getDateFromYMD(date: String): Long {
        return getDateFromStr(date, "yyyy-MM-dd")
    }

    /**
     * 获取指定月份的天数
     * @param year
     * @param month
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    @JvmStatic
    fun getDaysOfMonth(year: Int, month: Int): Int {
        val calendar = Calendar.getInstance()
        try {
            val sdf = SimpleDateFormat("yyyy-MM")
            calendar.time =
                if (month > 9) sdf.parse("${year}-${month}")!! else sdf.parse("${year}-0${month}")!!
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
    }

    @JvmStatic
    fun getDateYear(time: Long): Int {
        return Integer.parseInt(getDateStr(time, "yyyy"))
    }

    @JvmStatic
    fun getDateMonth(time: Long): Int {
        return Integer.parseInt(getDateStr(time, "MM"))
    }

    @JvmStatic
    fun getDateDay(time: Long): Int {
        return Integer.parseInt(getDateStr(time, "dd"))
    }

    @JvmStatic
    fun getDateWeek(time: Long): String {
        val cd = Calendar.getInstance(Locale.CHINESE)
        cd.time = Date(time)
        return when (cd.get(Calendar.DAY_OF_WEEK)) {
            Calendar.SUNDAY -> "星期日"
            Calendar.MONDAY -> "星期一"
            Calendar.TUESDAY -> "星期二"
            Calendar.WEDNESDAY -> "星期三"
            Calendar.THURSDAY -> "星期四"
            Calendar.FRIDAY -> "星期五"
            else -> "星期六"
        }
    }

    /**
     * 返回 传入时间 距离当前时间已经过去多少天
     */
    fun getGone(oldTime: Long): Int {
        val diff = System.currentTimeMillis() - oldTime
        return (diff / 24 / 60 / 60 / 1000).toInt()
    }

    /**
     * 计算还差多少天才到这个时间
     */
    fun getNeed(oldTime: Long): Int {
        //得到一个Calendar的实例
        val ca: Calendar = Calendar.getInstance()
        //设置时间
        ca.time = Date(oldTime)
        var diffYear = 1
        while (System.currentTimeMillis() > ca.time.time) {
            ca.add(Calendar.YEAR, diffYear)
            diffYear++
        }
        val diff = ca.time.time - getCurrentMill()
        return (diff / (24 * 60 * 60 * 1000)).toInt()
    }

    private fun getCurrentMill(): Long {
        val dateStr = getDateStr(System.currentTimeMillis(), "yyyy-MM-dd")
        return getDateFromStr(dateStr, "yyyy-MM-dd")
    }
}


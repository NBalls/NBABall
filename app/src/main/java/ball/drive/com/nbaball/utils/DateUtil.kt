package ball.drive.com.nbaball.utils

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by aaron on 2018/4/17.
 */
fun getYMD(timestamp: Long): String {
    val sdf = SimpleDateFormat("yyyyMMdd")
    return sdf.format(Date(timestamp))
}

fun getYMD(): String {
    val sdf = SimpleDateFormat("yyyyMMdd")
    return sdf.format(Date(System.currentTimeMillis()))
}

fun getYMDs(): List<String> {
    val strList = ArrayList<String>()
    for (i in 0..20) {
        strList.add(getYMD(System.currentTimeMillis() + (i - 10) * 1000 * 60 * 60 * 24))
    }

    return strList
}
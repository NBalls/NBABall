package ball.drive.com.nbaball.utils

/**
 * Created by aaron on 2018/8/17.
 */
fun changeNumIndex(num: Int): String {
    if (num < 10) {
        return "00" + num
    } else if (num < 100) {
        return "0" + num
    }

    return "" + num
}
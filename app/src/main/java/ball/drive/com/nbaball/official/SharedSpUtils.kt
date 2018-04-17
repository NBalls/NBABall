package ball.drive.com.nbaball.official

import android.content.Context
import android.content.SharedPreferences

/**
 * Created by aaron on 2018/4/16.
 */
class SharedSpUtils(context: Context) {

    private var sp: SharedPreferences = context.getSharedPreferences("zts_suggest", Context.MODE_PRIVATE)

    companion object {
        // 公推，精选，重心
        val PUBLIC_SUGGEST_KEY = "public_suggest"
        val SPECTICAL_SUGGEST_KEY = "spectical_suggest"
        val CENTER_SUGGEST_KEY = "center_suggest"
    }

    fun setValue(key: String, value: String) {
        sp.edit().putString(key, value).commit()
    }

    fun getValue(key: String): String {
        return sp.getString(key, "")
    }
}
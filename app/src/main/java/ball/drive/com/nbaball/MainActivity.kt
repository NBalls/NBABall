package ball.drive.com.nbaball

import android.os.Bundle
import me.yokeyword.fragmentation.SupportActivity

/**
 * 胜率计算器
 */
class MainActivity : SupportActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadRootFragment(R.id.mainLayout, createMainFragment())
    }
}

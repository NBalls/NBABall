package ball.drive.com.nbaball

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ball.drive.com.nbaball.network.APIClient
import com.hwangjr.rxbus.RxBus
import me.yokeyword.fragmentation.SupportFragment

/**
 * Created by aaron on 2018/4/11.
 */
open class BaseFragment: SupportFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        RxBus.get().register(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        RxBus.get().unregister(this)
    }
}
package ball.drive.com.nbaball

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import ball.drive.com.nbaball.entity.TeamGroup
import ball.drive.com.nbaball.entity.common.CommonProfile
import ball.drive.com.nbaball.event.TeamItemClickEvent
import ball.drive.com.nbaball.event.postEvent
import ball.drive.com.nbaball.network.APIClient
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.*

/**
 * Created by aaron on 2018/4/11.
 * 抓取页面
 */
class GrabFragment: BaseFragment() {

    private val apiClient by lazy {
        APIClient()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_grab, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        apiClient.requestTradeData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ jsonObject ->
                    Log.i("#####", jsonObject.toString())
                }, {})
    }
}


fun createGrabFragment(): GrabFragment {
    return GrabFragment()
}
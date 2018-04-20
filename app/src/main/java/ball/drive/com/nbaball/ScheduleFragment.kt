package ball.drive.com.nbaball

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import ball.drive.com.nbaball.entity.TeamGroup
import ball.drive.com.nbaball.entity.common.CommonProfile
import ball.drive.com.nbaball.event.TeamItemClickEvent
import ball.drive.com.nbaball.event.postEvent
import ball.drive.com.nbaball.network.APIClient
import org.jetbrains.anko.onClick
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.text.DecimalFormat
import java.util.*

/**
 * Created by aaron on 2018/4/11.
 */
class ScheduleFragment: BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_schedule, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view?.findViewById<Button>(R.id.btSubmit)?.onClick {
            val etNormalRate = view?.findViewById<EditText>(R.id.etNormalRate).text.toString().toFloat()
            val etR1 = view?.findViewById<EditText>(R.id.etR1).text.toString().toFloat()
            val etR2 = view?.findViewById<EditText>(R.id.etR2).text.toString().toFloat()
            val etR3 = view?.findViewById<EditText>(R.id.etR3).text.toString().toFloat()

            val et1 = etNormalRate / etR1
            val et2 = etNormalRate / etR2
            val et3 = etNormalRate / etR3

            view?.findViewById<EditText>(R.id.etRate1).setText(DecimalFormat("0.000").format(et1))
            view?.findViewById<EditText>(R.id.etRate2).setText(DecimalFormat("0.000").format(et2))
            view?.findViewById<EditText>(R.id.etRate3).setText(DecimalFormat("0.000").format(et3))
        }
    }
}

fun createScheduleFragment(): ScheduleFragment {
    return ScheduleFragment()
}
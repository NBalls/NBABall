package ball.drive.com.nbaball.official

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import ball.drive.com.nbaball.BaseFragment
import ball.drive.com.nbaball.R
import ball.drive.com.nbaball.event.OfficialItemClickEvent
import ball.drive.com.nbaball.event.postEvent
import org.jetbrains.anko.onClick

/**
 * Created by aaron on 2018/4/11.
 */
class OfficialFragment: BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_official, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view?.findViewById<LinearLayout>(R.id.ztsLayout)?.onClick {
            postEvent(OfficialItemClickEvent(""))
        }

        view?.findViewById<LinearLayout>(R.id.lsqtLayout)?.onClick {
            Toast.makeText(context, "猎手球探", Toast.LENGTH_SHORT).show()
        }

        view?.findViewById<LinearLayout>(R.id.acfyLayout)?.onClick {
            Toast.makeText(context, "澳彩风云", Toast.LENGTH_SHORT).show()
        }

        view?.findViewById<LinearLayout>(R.id.lqzqxLayout)?.onClick {
            Toast.makeText(context, "篮球最前线", Toast.LENGTH_SHORT).show()
        }

        view?.findViewById<LinearLayout>(R.id.lqsjLayout)?.onClick {
            Toast.makeText(context, "篮球圣经", Toast.LENGTH_SHORT).show()
        }

        view?.findViewById<LinearLayout>(R.id.qskLayout)?.onClick {
            Toast.makeText(context, "青衫客", Toast.LENGTH_SHORT).show()
        }
    }
}

fun createOfficialFragment(): OfficialFragment {
    return OfficialFragment()
}